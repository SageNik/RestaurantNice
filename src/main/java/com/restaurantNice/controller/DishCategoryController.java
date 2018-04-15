package com.restaurantNice.controller;

import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.sevice.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

/**
 * Created by Ник on 13.04.2018.
 */
@Controller
public class DishCategoryController {

    @Autowired
    private DishCategoryService dishCategoryService;
    private static final Logger LOGGER = Logger.getLogger(DishCategoryController.class.getName());

    @RequestMapping(value = "addNewDishCategory", method = RequestMethod.GET)
    public String addNewDishCategory(Model model) {

        model.addAttribute("title", "Add new dish category page");
        return "parts/dishCategoryForm";
    }

    @RequestMapping(value = "saveOrUpdateDishCategory", method = RequestMethod.POST)
    public String saveOrUpdateDish (@RequestParam("dishCategoryId")Long dishId, @RequestParam("name")String name){

        if(name != null){
            DishCategory dishCategory = new DishCategory(dishId,name);
            if(dishCategoryService.saveOrUpdateDishCategory(dishCategory)) return "redirect: menu";
            else {
                LOGGER.info("ERROR: The dish category hasn't been saved or updated.");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: name=" + name);
            return null;
        }
    }

    @RequestMapping(value = "editDishCategory", method = RequestMethod.POST)
    public String editDishCategory(@RequestParam("dishCategoryId")Long dishCategoryId, Model model) {
        if (dishCategoryId != null) {
            DishCategory currentDishCategory = dishCategoryService.getOneById(dishCategoryId);

            model.addAttribute("dishCategory", currentDishCategory);
            model.addAttribute("title", "Add new dish category page");
            return "parts/dishCategoryForm";
        } else {
            LOGGER.info("ERROR: not correct params: dishCategoryId=" + dishCategoryId);
            return null;
        }
    }

    @RequestMapping(value = "deleteDishCategory", method = RequestMethod.POST)
    public String deleteDishCategory (@RequestParam("dishCategoryId")Long dishCategoryId){

        if(dishCategoryId != null ){
            if(dishCategoryService.deleteDishCategoryById(dishCategoryId)) return "redirect: menu";
            else {
                LOGGER.info("ERROR: The dish category hasn't been deleted.");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: dishCategoryId=" + dishCategoryId);
            return null;
        }
    }
}
