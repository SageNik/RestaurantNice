package com.restaurantNice.dao;

import com.restaurantNice.entity.Group;

import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
public interface GroupDao {

    /**
     * Find all groups from database
     * @return List of groups if exist in database or empty list if not.
     */
    List<Group> findAll();

    /**
     * Save group in database
     * @param group
     * @return True if group has been saved or false if not.
     */
    boolean save(Group group);

    /**
     * Update group in database
     * @param group
     * @return True if group has been updated or false if not.
     */
    boolean update(Group group);

    /**
     * Find group by id in database
     * @param groupId
     * @return found group if exist or null if not.
     */
    Group findOneById(Long groupId);

    /**
     * Delete group by id from database
     * @param groupId
     * @return True if group has been deleted or false if not.
     */
    boolean deleteById(Long groupId);

    /**
     * Quit group by current user
     * @param groupId
     * @param userId
     * @return True if group has been left by user or false if not.
     */
    boolean quitGroup(Long groupId,Long userId);

}
