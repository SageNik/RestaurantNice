package com.restaurantNice.sevice;

import com.restaurantNice.entity.DishCategory;

import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
public interface DishCategoryService {

    /**
     * Get all dish category from database
     * @return List of found dish category if exists or empty list if not.
     */
    List<DishCategory> getAllCategory();

    /**
     * Get dish category by id
     * @param dishCategoryId
     * @return dish category if has been found or null if not.
     */
    DishCategory getOneById(Long dishCategoryId);

    /**
     * Save or update current dish category
     * @param dishCategory
     * @return true if dish category has been saved or updated or false if not.
     */
    boolean saveOrUpdateDishCategory(DishCategory dishCategory);

    /**
     * Delete dish category by id
     * @param dishCategoryId
     * @return true if dish category has been deleted or false if not.
     */
    boolean deleteDishCategoryById(Long dishCategoryId);
}
