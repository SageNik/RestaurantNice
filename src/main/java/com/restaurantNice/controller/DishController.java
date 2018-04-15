package com.restaurantNice.controller;

import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.enums.DishType;
import com.restaurantNice.sevice.DishCategoryService;
import com.restaurantNice.sevice.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 13.04.2018.
 */
@Controller
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishCategoryService dishCategoryService;
    private static final Logger LOGGER = Logger.getLogger(DishController.class.getName());

    @RequestMapping(value = "addNewDish", method = RequestMethod.GET)
    public String addNewDish(Model model) {
        List<DishCategory> dishCategories = dishCategoryService.getAllCategory();

        model.addAttribute("dishCategories", dishCategories);
        model.addAttribute("title", "Add new dish page");
        return "parts/dishForm";
    }

    @RequestMapping(value = "saveOrUpdateDish", method = RequestMethod.POST)
    public String saveOrUpdateDish (@RequestParam("dishId")Long dishId, @RequestParam("name")String name,
                                    @RequestParam("description")String description, @RequestParam("price")Double price,
                                    @RequestParam("dishCategory_id")Long dishCategory_id){

        if(name != null && description != null && price != null && dishCategory_id != null){
            Dish dish = new Dish(dishId,name,description,price,dishCategory_id, DishType.FOR_MENU);
            if(dishService.saveOrUpdateDish(dish)) return "redirect: menu";
            else {
                LOGGER.info("ERROR: The dish hasn't been saved or updated.");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: name=" + name+", description="+description+", price="+price+", dishCategory_id="+dishCategory_id);
            return null;
        }
    }

    @RequestMapping(value = "editDish", method = RequestMethod.POST)
    public String editDish(@RequestParam("dishId")Long dishId, Model model) {
        if (dishId != null) {
            List<DishCategory> dishCategories = dishCategoryService.getAllCategory();
            Dish currentDish = dishService.getOneById(dishId);

            model.addAttribute("dish", currentDish);
            model.addAttribute("dishCategories", dishCategories);
            model.addAttribute("title", "Add new dish page");
            return "parts/dishForm";
        } else {
            LOGGER.info("ERROR: not correct params: dishId=" + dishId);
            return null;
        }
    }

    @RequestMapping(value = "deleteMenuDish", method = RequestMethod.POST)
    public String deleteMenuDish (@RequestParam("dishId")Long dishId){

        if(dishId != null ){
            if(dishService.deleteMenuDish(dishId)) return "redirect: menu";
            else {
                LOGGER.info("ERROR: The dish hasn't been deleted.");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: dishId=" + dishId);
            return null;
        }
    }
}
