package com.restaurantNice.dao;

import com.restaurantNice.entity.GroupOrder;

import java.util.List;

/**
 * Created by Ник on 15.04.2018.
 */
public interface GroupOrderDao {

    /**
     * Find all group orders from database
     * @return List of group orders if exist in database or empty list if not.
     */
    List<GroupOrder> findAll();

    /**
     * Find all orders by group id
     * @param groupId
     * @return list of orders that belong particular group if they exist or an empty list if not.
     */
    List<GroupOrder> findAllByGroupId(Long groupId);

    /**
     * Save groupOrder in database
     * @param groupOrder
     * @return True if group order has been saved or false if not.
     */
    boolean save(GroupOrder groupOrder);

    /**
     * Find group order by id
     * @param groupOrderId
     * @return Found group order if exist or null if not.
     */
    GroupOrder findOneById(Long groupOrderId);

    /**
     * Delete group order by id
     * @param groupOrderId
     * @return True if group order has been successfully deleted or false if not.
     */
    boolean deleteById(Long groupOrderId);

    /**
     * Update groupOrder in database
     * @param groupOrder
     * @return True if group order has been updated or false if not.
     */
    boolean update(GroupOrder groupOrder);
}
