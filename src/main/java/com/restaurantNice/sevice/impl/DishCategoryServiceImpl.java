package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.DishCategoryDao;
import com.restaurantNice.dao.DishDao;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.enums.DishType;
import com.restaurantNice.sevice.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 13.04.2018.
 */
@Service
public class DishCategoryServiceImpl implements DishCategoryService{

    @Autowired
    private DishCategoryDao dishCategoryDao;
    @Autowired
    private DishDao dishDao;
    private static final Logger LOGGER = Logger.getLogger(DishCategoryDao.class.getName());

    @Transactional
    @Override
    public List<DishCategory> getAllCategory() {

        List<DishCategory> categories = dishCategoryDao.FindAllCategory();
        for (DishCategory category : categories){
            category.setDishes(dishDao.findAllByTypeAndDishCategoryId(DishType.FOR_MENU,category.getId()));
            category.setAmountDish(category.getCurrentAmountDish());
        }
        return categories;
    }

    @Transactional
    @Override
    public DishCategory getOneById(Long dishCategoryId) {

        DishCategory dishCategory = dishCategoryDao.findOneById(dishCategoryId);
        if(dishCategory != null){
            dishCategory.setDishes(dishDao.findAllByTypeAndDishCategoryId(DishType.FOR_MENU,dishCategoryId));
            dishCategory.setAmountDish(dishCategory.getCurrentAmountDish());
            return dishCategory;
        }
        return null;
    }

    @Override
    public boolean saveOrUpdateDishCategory(DishCategory dishCategory) {

        if(dishCategory.getId() == null){
            if(dishCategoryDao.save(dishCategory) > 0) return true;
        }else{
            dishCategory.setDishes(dishDao.findAllByTypeAndDishCategoryId(DishType.FOR_MENU,dishCategory.getId()));
            dishCategory.setAmountDish(dishCategory.getCurrentAmountDish());
            if(dishCategoryDao.update(dishCategory) > 0) {
                LOGGER.info("The dish category with id="+dishCategory.getId()+" has been updated");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteDishCategoryById(Long dishCategoryId) {
        return dishCategoryDao.delete(dishCategoryId);
    }
}
