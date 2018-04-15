package com.restaurantNice.dao;

import com.restaurantNice.entity.DishCategory;

import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
public interface DishCategoryDao {

    /**
     * Find all dish category from database
     * @return List of found dish category if exists or empty list if not.
     */
    List<DishCategory> FindAllCategory();

    /**
     * Find dish category from database by id
     * @param dishCategoryId
     * @return dish category if has been found or null if not.
     */
    DishCategory findOneById(Long dishCategoryId);

    /**
     * Save dish category
     * @param dishCategory
     * @return number of inserted rows >0 if dish category has been saved or 0 if not
     */
    int save(DishCategory dishCategory);

    /**
     * Update dish category
     * @param dishCategory
     * @return number of inserted rows >0 if dish category has been updated or 0 if not
     */
    int update(DishCategory dishCategory);

    /**
     * Delete dish category by id
     * @param dishCategoryId
     * @return True if dish category has been deleted or false if not.
     */
    boolean delete(Long dishCategoryId);
}
