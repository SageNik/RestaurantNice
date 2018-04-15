package com.restaurantNice.controller;

import com.restaurantNice.entity.User;
import com.restaurantNice.enums.Role;
import com.restaurantNice.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

import static com.restaurantNice.Constants.CURRENT_SESSION_USER;

/**
 * Created by Ник on 13.04.2018.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title", "Login page");
        return "login";
    }

    @RequestMapping(value = "authorization", method = RequestMethod.POST)
    public String authorization(@RequestParam("username") String username, @RequestParam("password") String password,
                                HttpSession session, Model model) {
        if (username != null && password != null) {
            User authUser = userService.getUserByNameAndPassword(username, password);
            if (authUser != null) {
                session.setAttribute(CURRENT_SESSION_USER, authUser);
                return "redirect: home";
            } else {
                model.addAttribute("loginError", "Wrong name or password");
                model.addAttribute("title", "Login page");
                return "login";
            }
        } else {
            model.addAttribute("loginError", "Not correct name or password");
            model.addAttribute("title", "Login page");
            return "login";
        }
    }


    @RequestMapping(value = "registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("title", "Registration page");
        return "registration";
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String registrationPost(@RequestParam("username") String username, @RequestParam("password") String password,
                                   @RequestParam("selectRole") String roleVal, Model model) {
        if (username != null && password != null && roleVal != null) {
            Role role = (Integer.parseInt(roleVal) == 0) ? Role.ADMIN : Role.USER;
            if(userService.alreadyExist(username)) {
                model.addAttribute("loginError", "Sorry, user with that name already exist.");
                model.addAttribute("title", "Registration page");
                return "registration";
            }
            if (userService.saveUser(new User(username, password, role))) return "redirect:login";
            else {
                model.addAttribute("loginError", "User hasn't been saved. Check input data.");
                model.addAttribute("title", "Registration page");
                return "registration";
            }
        } else {
            model.addAttribute("loginError", "Not correct name or password");
            model.addAttribute("title", "Registration page");
            return "registration";
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {

        session.removeAttribute(CURRENT_SESSION_USER);
        return "redirect: login";
    }

    @RequestMapping(value = "joinRequests",method = RequestMethod.GET)
    public String joinRequests(HttpSession session, Model model){
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            model.addAttribute("requests", currentUser.getJoinRequests());
            model.addAttribute("title", "JoinRequests Page");
            return "parts/joinRequestsPage";
        }else {
            LOGGER.info("ERROR : The session have not current user");
            return "redirect: logout";
        }
    }

}
