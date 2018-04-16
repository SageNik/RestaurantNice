package com.restaurantNice.sevice;

import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;

import java.util.List;

/**
 * Created by Ник on 10.04.2018.
 */
public interface OrderService {

    /**
     * Get all orders by current user
     * @param user
     * @return list of orders that belong particular user if they exist or an empty list if not
     */
    List<Order> getAllByUser(User user);

    /**
     * Save new order
     * @param currentUser
     * @param orderDishes
     * @param orderId
     * @return True if order has been saved or updated or false if not.
     */
    boolean saveOrUpdateOrder(User currentUser, List<Dish> orderDishes, Long orderId);

    /**
     * Save new order and send
     * @param currentUser
     * @param orderDishes
     * @param orderId
     * @return True if order has been saved and sent or updated and sent or false if not.
     */
    boolean saveOrUpdateAndSendOrder(User currentUser, List<Dish> orderDishes, Long orderId);

    /**
     * Get order by id from database
     * @param orderId
     * @return Found order if exist or null if not.
     */
    Order getOrderById(Long orderId);

    /**
     * Send order by order id
     * @param orderId
     * @return Order if it has been sent or null if not.
     */
    Order sendOrder(Long orderId);

    /**
     * Delete order by id
     * @param orderId
     * @return True if order has been deleted or false if not.
     */
    boolean deleteOrderById(Long orderId);

    /**
     * Save or update new order for group order
     * @param currentUser
     * @param orderDishes
     * @param orderId
     * @return Id of order if order has been saved or updated or null if not.
     */
    Long saveOrUpdateOrderForGroupOrder(User currentUser,List<Dish> orderDishes,Long orderId,Long groupOrderId);
}
