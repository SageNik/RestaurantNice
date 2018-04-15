package com.restaurantNice.dao;

import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.enums.DishType;

import java.util.List;

/**
 * Created by Ник on 10.04.2018.
 */
public interface DishDao {

    /**
     * Find all dishes from database.
     * @return list of found dishes if they exist or empty list
     */
    List<Dish> findAll();

    /**
     * Get all dishes by type
     * @param dishType
     * @return list of dishes that belong particular type if they exist or an empty list if not
     */
    List<Dish> findAllByType(DishType dishType);

    /**
     * Find dish from database by id.
     * @param dishId
     * @return Dish if it exist in database or null if not.
     */
    Dish findOneById(Long dishId);

    /**
     * Find all dishes by order id
     * @param orderId
     * @return List of dishes that related with particular order if exist or empty list if not.
     */
    List<Dish> findAllByOrderId(Long orderId);

    /**
     * Find all dishes by dish type and dish category
     * @param type
     * @param dishCategoryId
     * @return list of dishes that belong particular type and category if they exist or an empty list if not
     */
    List<Dish> findAllByTypeAndDishCategoryId(DishType type, Long dishCategoryId);

    /**
     * Save dish in database
     * @param dish
     * @return Generated key (id) if dish has been saved or null if not.
     */
    Long save(Dish dish);

    /**
     * Save related dishes
     * @param savedOrderId
     * @param orderDishes
     * @return True if related dishes has been saved or false if not.
     */
    boolean saveRelatedDishes(Long savedOrderId, List<Dish> orderDishes);

    /**
     * Delete related dishes
     * @param savedOrderId
     * @param orderDishes
     * @return True if related dishes has been deleted or false if not.
     */
    boolean deleteRelatedDishes(Long savedOrderId, List<Dish> orderDishes);

    /**
     * Delete dish from database
     * @param dishId
     * @return True if dish has been deleted or false if not.
     */
    boolean delete (Long dishId);

    /**
     * Update dish in database
     * @param dish
     * @return number of updated rows that >0 if dish has been updated or 0 if not
     */
    int update(Dish dish);

}
