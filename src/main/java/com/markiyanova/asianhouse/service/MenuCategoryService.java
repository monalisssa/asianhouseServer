package com.markiyanova.asianhouse.service;

import com.markiyanova.asianhouse.entity.menu.MenuCategoryEntity;
import com.markiyanova.asianhouse.entity.user.UserEntity;
import com.markiyanova.asianhouse.exception.MenuCategoryNotFoundException;
import com.markiyanova.asianhouse.exception.MenuCategoryWithThisNameAlreadyExistException;
import com.markiyanova.asianhouse.exception.UserNotFoundException;
import com.markiyanova.asianhouse.model.MenuCategory;
import com.markiyanova.asianhouse.model.MenuItem;
import com.markiyanova.asianhouse.model.User;
import com.markiyanova.asianhouse.repository.MenuCategoryRepository;
import com.markiyanova.asianhouse.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuCategory createMenuCategory(String name, MultipartFile image) throws MenuCategoryWithThisNameAlreadyExistException, IOException {
       if(menuCategoryRepository.findMenuCategoryEntityByName(name) != null)
       {
           throw new MenuCategoryWithThisNameAlreadyExistException("Категория блюд с таким именем уже существует!");
       }
       else
       {
           MenuCategoryEntity menuCategory = new MenuCategoryEntity();
           menuCategory.setName(name);
           menuCategory.setImage(image.getBytes());
           return MenuCategory.toModel(menuCategoryRepository.save(menuCategory));
       }
    }

    public List<MenuCategory> getAll() {

        List<MenuCategory> menuCategories = new ArrayList<>();
        menuCategoryRepository.findAll().forEach(menuCategory -> menuCategories.add(MenuCategory.toModel(menuCategory)));
        return menuCategories;
    }

    public MenuCategoryEntity getOneByName(String name) throws MenuCategoryNotFoundException {

        MenuCategoryEntity findMenuCategoryEntity = menuCategoryRepository.findMenuCategoryEntityByName(name);
        if(findMenuCategoryEntity == null)
        {
            throw new MenuCategoryNotFoundException("Категория меню с таким id не была найдена!");
        }
        return findMenuCategoryEntity;
    }


    public MenuCategoryEntity getOneById(long id) throws MenuCategoryNotFoundException {

        Optional<MenuCategoryEntity> findMenuCategoryEntity = menuCategoryRepository.findById(id);
        if(findMenuCategoryEntity.isEmpty())
        {
            throw new MenuCategoryNotFoundException("Категория меню с таким id не была найдена!");
        }
        return findMenuCategoryEntity.get();
    }

    public Long delete(long id){
        menuCategoryRepository.deleteById(id);
        return id;
    }



}
