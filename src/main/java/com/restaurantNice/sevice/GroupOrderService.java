package com.restaurantNice.sevice;

import com.restaurantNice.entity.GroupOrder;
import com.restaurantNice.entity.User;
import com.restaurantNice.model.GroupOrderViewModel;

import java.util.List;

/**
 * Created by Ник on 15.04.2018.
 */
public interface GroupOrderService {

    /**
     * Get all group orders from database
     * @return List of group orders if exist in database or empty list if not.
     */
    List<GroupOrder> getAllGroupOrders();

    /**
     * Create new group order
     * @param currentUserId
     * @param groupId
     * @return True if new group order has been successfully created or false if not.
     */
    boolean createGroupOrder(Long currentUserId, Long groupId);

    /**
     * Get all group order view models
     * @param currentUser
     * @return List of group order view models if exist in database or empty list if not.
     */
    List<GroupOrderViewModel>getAllGroupOrderModels(User currentUser);

    /**
     * Get group order by id
     * @param groupOrderId
     * @return Found group order if exist or null if not.
     */
    GroupOrder getOneById(Long groupOrderId);

    /**
     * Delete group order by id with related order from database
     * @param groupOrderId
     * @return True if new group order has been successfully deleted or false if not.
     */
    boolean deleteGroupOrderById(Long groupOrderId);

    /**
     * Send group order
     * @param groupOrderId
     * @return True if group order has been successfully sent or false if not.
     */
    boolean sendGroupOrder(Long groupOrderId);
}
