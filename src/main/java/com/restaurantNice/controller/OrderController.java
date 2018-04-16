package com.restaurantNice.controller;

import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.DishType;
import com.restaurantNice.sevice.DishCategoryService;
import com.restaurantNice.sevice.DishService;
import com.restaurantNice.sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.restaurantNice.Constants.*;

/**
 * Created by Ник on 13.04.2018.
 */
@Controller
public class OrderController {

    @Autowired
    private DishService dishService;
    @Autowired
    private DishCategoryService dishCategoryService;
    @Autowired
    private OrderService orderService;
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

    @RequestMapping(value = "orders", method = RequestMethod.GET)
    public String orders(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            try {
                List<Order> userOrders = orderService.getAllByUser(currentUser);

                model.addAttribute("title", "Order page");
                model.addAttribute("orders", userOrders);
                return "parts/ordersPage";
            } catch (Exception e) {
                LOGGER.info("ERROR : " + e.getMessage());
                return null;
            }
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "newOrder", method = RequestMethod.GET)
    public String newOrder(@RequestParam(value = "groupOrderId", required = false) Long groupOrderId, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            List<Dish> dishes = dishService.getAllByType(DishType.FOR_MENU);
            List<DishCategory> dishCategories = dishCategoryService.getAllCategory();
            session.setAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES, new ArrayList<Dish>());
            session.removeAttribute(CURRENT_ORDER_ID);
if(groupOrderId != null){
             session.setAttribute(CURRENT_GROUP_ORDER_ID,groupOrderId);
            model.addAttribute("groupOrderId", groupOrderId);
}
            model.addAttribute("dishes", dishes);
            model.addAttribute("dishCategories", dishCategories);
            model.addAttribute("orderDishes", session.getAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES));
            model.addAttribute("title", "New order page");
            return "parts/newOrder";
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "toOrder", method = RequestMethod.POST)
    public String toOrder(@RequestParam("dishId") Long dishId, @RequestParam("amount") Integer amount,
                          HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            if (dishId != null && amount != null) {
                List<Dish> orderDishes = (List<Dish>) session.getAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
                List<Dish> currentOrderDishes = dishService.addDishToOrder(orderDishes, dishId, amount);
                session.setAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES, currentOrderDishes);

                addOrderAttributes(model, currentOrderDishes, (Long)session.getAttribute(CURRENT_GROUP_ORDER_ID));
                return "parts/order-content";
            } else {
                LOGGER.info("ERROR: not correct params: dishId=" + dishId + " or amount=" + amount);
                return null;
            }
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    private void addOrderAttributes(Model model, List<Dish> currentOrderDishes, Long groupOrderId) {
        double total = calculateTotal(currentOrderDishes);
        model.addAttribute("total", total);
        model.addAttribute("groupOrderId", groupOrderId);
        model.addAttribute("orderDishes", currentOrderDishes);
    }

    private double calculateTotal(List<Dish> orderDishes) {
        double total = 0.0;
        for (Dish dish : orderDishes) {
            total += ((dish.getPrice() * 100 * dish.getAmount()) / 100);
        }
        return total;
    }

    @RequestMapping(value = "fromOrder", method = RequestMethod.POST)
    public String fromOrder(@RequestParam("dishId") Long dishId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            if (dishId != null) {
                List<Dish> orderDishes = (List<Dish>) session.getAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
                List<Dish> currentOrderDishes = dishService.delDishFromOrder(orderDishes, dishId);
                session.setAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES, currentOrderDishes);

                addOrderAttributes(model, currentOrderDishes, (Long)session.getAttribute(CURRENT_GROUP_ORDER_ID));
                return "parts/order-content";
            } else {
                LOGGER.info("ERROR: not correct params: dishId=" + dishId);
                return null;
            }
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "saveOrder", method = RequestMethod.POST)
    public String saveOrder(@RequestParam(value = "groupOrderId",required = false)Long groupOrderId, Model model, HttpSession session) {

        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            List<Dish> orderDishes = (List<Dish>) session.getAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
            Long orderId = (Long) session.getAttribute(CURRENT_ORDER_ID);

            if(groupOrderId == null) {
                if (orderService.saveOrUpdateOrder(currentUser, orderDishes, orderId)) {
                    session.removeAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
                    return "redirect: orders";
                } else return null;
            }else{
                  orderId = orderService.saveOrUpdateOrderForGroupOrder(currentUser,orderDishes,orderId,groupOrderId);
                if(orderId != null) {
                    return toGroupOrders(groupOrderId, model, session,orderId);
                }else return null;
            }
        }else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }

    }

    private String toGroupOrders(Long groupOrderId, Model model, HttpSession session, Long orderId) {
        session.removeAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
        session.removeAttribute(CURRENT_GROUP_ORDER_ID);
        model.addAttribute("orderId", orderId);
        model.addAttribute("groupOrderId",groupOrderId);
        return "redirect: showGroupOrder";
    }

    @RequestMapping(value = "saveAndSendOrder", method = RequestMethod.POST)
    public String saveAndSendOrder(HttpSession session) {

        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            List<Dish> orderDishes = (List<Dish>) session.getAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
            Long orderId = (Long) session.getAttribute(CURRENT_ORDER_ID);

            if (orderService.saveOrUpdateAndSendOrder(currentUser, orderDishes, orderId)) {
                session.removeAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES);
                session.removeAttribute(CURRENT_ORDER_ID);

                return "redirect: orders";
            } else return null;
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }

    @RequestMapping(value = "showOrder", method = RequestMethod.POST)
    public String newOrder(@RequestParam("orderId") Long orderId, Model model) {
        if (orderId != null) {
            Order currentOrder = orderService.getOrderById(orderId);
            return setAttributesForShowOrderPage(orderId, model, currentOrder);
        } else {
            LOGGER.info("ERROR: not correct params: orderId=" + orderId);
            return null;
        }
    }

    @RequestMapping(value = "sendOrder", method = RequestMethod.POST)
    public String sendOrder(@RequestParam("orderId") Long orderId, Model model) {
        if (orderId != null) {
            Order currentOrder = orderService.sendOrder(orderId);
            return setAttributesForShowOrderPage(orderId, model, currentOrder);
        } else {
            LOGGER.info("ERROR: not correct params: orderId=" + orderId);
            return null;
        }
    }

    private String setAttributesForShowOrderPage(Long orderId, Model model, Order currentOrder) {
        if (currentOrder != null) {

            model.addAttribute("currentOrder", currentOrder);
            model.addAttribute("orderDishes", currentOrder.getDishes());
            model.addAttribute("title", "Show order page");
            return "parts/showOrder";
        } else {
            LOGGER.info("ERROR: order not found by id=" + orderId);
            return null;
        }
    }

    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    public String deleteOrder(@RequestParam("orderId") Long orderId,Model model, HttpSession session,
                              @RequestParam(value = "groupOrderId", required = false)Long groupOrderId) {

        if (orderId != null) {
            if (orderService.deleteOrderById(orderId)) {
                if(groupOrderId == null) return "redirect: orders";
                else {
                    return toGroupOrders(groupOrderId,model,session,null);
                }
            }
            else {
                LOGGER.info("ERROR: order with orderId=" + orderId + " hasn't been deleted");
                return null;
            }
        } else {
            LOGGER.info("ERROR: not correct params: orderId=" + orderId);
            return null;
        }
    }

    @RequestMapping(value = "editOrder", method = RequestMethod.POST)
    public String editOrder(@RequestParam("orderId") Long orderId,
                            @RequestParam(value = "groupOrderId", required = false)Long groupOrderId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute(CURRENT_SESSION_USER);
        if (currentUser != null) {
            Order currentOrder = orderService.getOrderById(orderId);
            if (currentOrder != null) {
                List<Dish> dishes = dishService.getAllByType(DishType.FOR_MENU);
                List<DishCategory> dishCategories = dishCategoryService.getAllCategory();
                session.setAttribute(CURRENT_TEMP_NEW_ORDER_ENTRIES, currentOrder.getDishes());
                session.setAttribute(CURRENT_ORDER_ID, orderId);
                session.setAttribute(CURRENT_GROUP_ORDER_ID, groupOrderId);

                model.addAttribute("groupOrderId", groupOrderId);
                model.addAttribute("dishes", dishes);
                model.addAttribute("orderId", orderId);
                model.addAttribute("dishCategories", dishCategories);
                model.addAttribute("orderDishes", currentOrder.getDishes());
                model.addAttribute("total", currentOrder.getSum());
                model.addAttribute("title", "Edit order page");
                return "parts/editOrder";
            } else return null;
        } else {
            LOGGER.info("ERROR : The session have not current user");
            return null;
        }
    }
}
