package com.restaurantNice.dao;

import com.restaurantNice.entity.User;

import java.util.List;

/**
 * Created by Ник on 07.04.2018.
 */
public interface UserDao {

    /**
     * Get user by parameters if exist in database.
     * @param name
     * @param password
     * @return found user or null
     */
    User getOneByNameAndPassword(String name, String password);

    /**
     * Save user
     * @param user
     * @return number of inserted rows >0 if user has been saved or 0 if not
     */
    int save (User user);


    /**
     * Find all users by group id
     * @param groupId
     * @return list of users that belong particular group if they exist or an empty list if not.
     */
    List<User> findAllByGroupId(Long groupId);

    /**
     * Find user by username in database
     * @param username
     * @return Found user if exist or null if not.
     */
    User findOneByUsername(String username);

    /**
     * Find user by id in database
     * @param userId
     * @return Found user if exist or null if not.
     */
    User findOneById(Long userId);

    /**
     * Add user to group by id
     * @param userId
     * @param groupId
     * @return True if user has been added or false if not.
     */
    boolean addUserToGroup(Long userId, Long groupId);
}
