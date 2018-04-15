package com.restaurantNice.sevice;

import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.DishType;

import java.util.List;

/**
 * Created by Ник on 10.04.2018.
 */
public interface DishService {

    /**
     * Get all dishes from database
     * @return list of found dishes if they exist or empty list.
     */
    List<Dish> getAll();

    /**
     * Get all dishes by type
     * @param dishType
     * @return list of dishes that belong particular type if they exist or an empty list if not
     */
    List<Dish> getAllByType(DishType dishType);

    /**
     * Get dish from database by id.
     * @param dishId
     * @return Dish if it exist in database or null if not.
     */
    Dish getOneById(Long dishId);

    /**
     * Add dish to the order
     * @param orderDishes
     * @param dishId
     * @param amount
     * @return List of current ordered dishes.
     */
    List<Dish> addDishToOrder(List<Dish> orderDishes, Long dishId, Integer amount);

    /**
     * Dellete dish from order
     * @param orderDishes
     * @param dishId
     * @return List of current ordered dishes.
     */
    List<Dish> delDishFromOrder(List<Dish> orderDishes, Long dishId);

    /**
     * Save or update dish
     * @param dish
     * @return True if dish has been saved or updated or false if not.
     */
    boolean saveOrUpdateDish(Dish dish);

    /**
     * Delete dish from database by dish id
     * @param dishId
     * @return True if dish has been deleted or false if not.
     */
    boolean deleteMenuDish(Long dishId);
}
