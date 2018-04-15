package com.restaurantNice.sevice;


import com.restaurantNice.entity.User;

/**
 * Created by Ник on 07.04.2018.
 */
public interface UserService {

    /**
     * Get user by username and password
     * @param username
     * @param password
     * @return found user or null.
     */
    User getUserByNameAndPassword(String username, String password);

    /**
     * Save user
     * @param user
     * @return true if user has been saved success or false if not.
     */
    boolean saveUser (User user);

    /**
     * Looking for current username in database
     * @param username
     * @return true if found the user with same username or false if not.
     */
    boolean alreadyExist(String username);
}
