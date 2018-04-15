package com.restaurantNice.dao;

import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;

import java.util.List;

/**
 * Created by Ник on 10.04.2018.
 */
public interface OrderDao {

    /**
     * Find all orders by current user
     * @param user
     * @return list of orders that belong particular user if they exist or an empty list if not.
     */
    List<Order> findAllByUser(User user);

    /**
     * Save order
     * @param order
     * @return Generated key (id) if order has been saved or null if not.
     */
    Long save(Order order);

    /**
     * Find order by id
     * @param orderId
     * @return Found order if exist or null if not.
     */
    Order findOneById(Long orderId);

    /**
     * Update current order
     * @param currentOrder
     * @return number of updated rows >0 if order has been updated or 0 if not
     */
    int update(Order currentOrder);

    /**
     * Delete order by id
     * @param orderId
     * @return number of deleted rows >0 if order has been deleted or 0 if not
     */
    int deleteOrderById(Long orderId);

    /**
     * Find all orders by group id
     * @param groupId
     * @return list of orders that belong particular group if they exist or an empty list if not.
     */
    List<Order> findAllByGroupId(Long groupId);
}
