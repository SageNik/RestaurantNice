package com.restaurantNice.controller;

import com.restaurantNice.Constants;
import com.restaurantNice.entity.GroupOrder;
import com.restaurantNice.entity.User;
import com.restaurantNice.model.GroupOrderViewModel;
import com.restaurantNice.sevice.GroupOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 15.04.2018.
 */
@Controller
public class GroupOrderController implements Constants{

    @Autowired
    private GroupOrderService groupOrderService;
    private static final Logger LOGGER = Logger.getLogger(GroupController.class.getName());

    @RequestMapping(value = "groupOrders", method = RequestMethod.GET)
    public String groupOrders(HttpSession session, Model model){
        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null){

        List<GroupOrderViewModel> groupOrderViewModels = groupOrderService.getAllGroupOrderModels(currentUser);

        model.addAttribute("orders", groupOrderViewModels);
        return "parts/groupOrdersPage";
        }else{LOGGER.info("ERROR : The session have not current user");
            return null;}
    }

    @RequestMapping(value = "createGroupOrder", method = RequestMethod.POST)
    public String createGroupOrder(@RequestParam("groupId")Long groupId, HttpSession session){

        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null){
            if(groupId != null) {
                if(groupOrderService.createGroupOrder(currentUser.getId(), groupId)) return "redirect: groupOrders";
                else return null;
            }else{
                LOGGER.info("ERROR: not correct params: groupId=" + groupId);
                return null;
            }
        }else{LOGGER.info("ERROR : The session have not current user");
        return null;}
    }

    @RequestMapping(value = "showGroupOrder", method = RequestMethod.GET)
    public String showGroupOrder(@RequestParam("groupOrderId")Long groupOrderId,
                                 @RequestParam(value = "orderId",required = false)Long orderId,
                                 HttpSession session, Model model){

        User currentUser = (User)session.getAttribute(CURRENT_SESSION_USER);
        if(currentUser != null){
            if(groupOrderId != null) {
                GroupOrder groupOrder = groupOrderService.getOneById(groupOrderId);
                model.addAttribute("userId",currentUser.getId());
                model.addAttribute("orderId", orderId);
                model.addAttribute("groupOrder", groupOrder);
                return "parts/groupOrderShowPage";
            }else{
                LOGGER.info("ERROR: not correct params: groupId=" + groupOrderId);
                return null;
            }
        }else{LOGGER.info("ERROR : The session have not current user");
            return null;}
    }

    @RequestMapping(value = "deleteGroupOrder", method = RequestMethod.POST)
    public String deleteGroupOrder(@RequestParam("groupOrderId")Long groupOrderId){

            if(groupOrderId != null) {
                if(groupOrderService.deleteGroupOrderById(groupOrderId)) return "redirect: groupOrders";
                else return null;
            }else{
                LOGGER.info("ERROR: not correct params: groupId=" + groupOrderId);
                return null;
            }
    }

    @RequestMapping(value = "sendGroupOrder", method = RequestMethod.POST)
    public String sendGroupOrder(@RequestParam("groupOrderId")Long groupOrderId){

        if(groupOrderId != null) {
            if(groupOrderService.sendGroupOrder(groupOrderId)) return "redirect: groupOrders";
            else return null;
        }else{
            LOGGER.info("ERROR: not correct params: groupId=" + groupOrderId);
            return null;
        }
    }
}
