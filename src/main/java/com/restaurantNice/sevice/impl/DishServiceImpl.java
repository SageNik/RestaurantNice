package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.DishDao;
import com.restaurantNice.dao.impl.MySqlDishDaoImpl;
import com.restaurantNice.entity.Dish;
import com.restaurantNice.enums.DishType;
import com.restaurantNice.sevice.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 10.04.2018.
 */
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dishDao;
    private static final Logger LOGGER = Logger.getLogger(DishServiceImpl.class.getName());


    @Override
    public List<Dish> getAll() {
        return dishDao.findAll();
    }

    @Override
    public List<Dish> getAllByType(DishType dishType) {
        return dishDao.findAllByType(dishType);
    }

    @Override
    public Dish getOneById(Long dishId) {
        return dishDao.findOneById(dishId);
    }

    @Override
    public List<Dish> addDishToOrder (List<Dish> orderDishes,Long dishId, Integer amount) {
        Dish currentDish = getOneById(dishId);

        boolean isExist = false;
        for (Dish dish : orderDishes) {
            if (currentDish.getId().equals(dish.getId())) {
                dish.setAmount(dish.getAmount() + amount);
                isExist = true;
            }
        }
        if (!isExist) {
            currentDish.setAmount(amount);
            orderDishes.add(currentDish);
        }
        return orderDishes;
    }

    @Override
    public List<Dish> delDishFromOrder(List<Dish> orderDishes, Long dishId) {

        Dish deleteDish = null;
       for(Dish dish : orderDishes){
           if(dish.getId().equals(dishId)) deleteDish = dish;
       }
       if(deleteDish != null ) orderDishes.remove(deleteDish);
        return orderDishes;
    }

    @Override
    public boolean saveOrUpdateDish(Dish dish) {

        if(dish.getId() == null){
            if(dishDao.save(dish) != null) return true;
        }else{
            if(dishDao.update(dish) > 0) {
                LOGGER.info("The dish with id="+dish.getId()+" has been updated");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteMenuDish(Long dishId) {
        return dishDao.delete(dishId);
    }
}