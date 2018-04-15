package com.restaurantNice.controller;

import com.restaurantNice.Constants;
import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.DishType;
import com.restaurantNice.model.UserViewModel;
import com.restaurantNice.sevice.DishCategoryService;
import com.restaurantNice.sevice.DishService;
import com.restaurantNice.sevice.JoinRequestService;
import com.restaurantNice.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;


@Controller
public class ViewController implements Constants {

    @Autowired
    private DishService dishService;
    @Autowired
    UserService userService;
    @Autowired
    private DishCategoryService dishCategoryService;
    @Autowired
    private JoinRequestService joinRequestService;
    private static final Logger LOGGER = Logger.getLogger(ViewController.class.getName());

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home(HttpSession session, Model model) {
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null) {
            UserViewModel userViewModel = UserViewModel.buildByUser(currentUser);
            model.addAttribute("user", userViewModel);
            model.addAttribute("title", "Home page");
            return "home";
        }else{
            LOGGER.info("ERROR: The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "menu", method = RequestMethod.GET)
    public String menu(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            List<Dish> dishes = dishService.getAllByType(DishType.FOR_MENU);
            List<DishCategory> dishCategories = dishCategoryService.getAllCategory();

            model.addAttribute("user",currentUser);
            model.addAttribute("dishes", dishes);
            model.addAttribute("dishCategories", dishCategories);
            model.addAttribute("title", "Menu page");
            return"parts/menuPage";
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return "redirect: logout";
        }
    }

    @RequestMapping(value = "acceptJoinRequest",method = RequestMethod.POST)
    public String acceptJoinRequest(@RequestParam("joinRequestId")Long joinRequestId, HttpSession session){

            if(joinRequestId != null){
                if(joinRequestService.acceptRequest(joinRequestId)){
                    updateCurrentSessionUser(session);
                    LOGGER.info("The join request with id="+joinRequestId+" has been accept ");
                    return "redirect: joinRequests";
                }
                else return null;
            }else {
                LOGGER.info("ERROR: not correct params: joinRequestId=" + joinRequestId);
                return null;
            }
    }

    private void updateCurrentSessionUser(HttpSession session) {
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null){
            currentUser = userService.getUserByNameAndPassword(currentUser.getUsername(), currentUser.getPassword());
            if(currentUser!= null) session.setAttribute(CURRENT_SESSION_USER,currentUser);
        }
    }

    @RequestMapping(value = "rejectJoinRequest",method = RequestMethod.POST)
    public String rejectJoinRequest(@RequestParam("joinRequestId")Long joinRequestId, HttpSession session){

        if(joinRequestId != null){
            if(joinRequestService.rejectRequest(joinRequestId)){
                updateCurrentSessionUser(session);
                LOGGER.info("The join request with id="+joinRequestId+" has been rejected ");
                return "redirect: joinRequests";
            }
            else return null;
        }else {
            LOGGER.info("ERROR: not correct params: joinRequestId=" + joinRequestId);
            return null;
        }
    }

}
