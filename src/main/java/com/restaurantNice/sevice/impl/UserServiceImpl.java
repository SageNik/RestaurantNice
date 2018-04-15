package com.restaurantNice.sevice.impl;

import com.restaurantNice.controller.UserController;
import com.restaurantNice.dao.JoinRequestDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.User;
import com.restaurantNice.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by Ник on 07.04.2018.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private JoinRequestDao joinRequestDao;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public User getUserByNameAndPassword(String username, String password) {
        User authUser = userDao.getOneByNameAndPassword(username, password);
        if(authUser != null){
            authUser.setJoinRequests(joinRequestDao.findAllByUserId(authUser.getId()));
            LOGGER.info("The user with username: "+username+" and password has been found");
        }
        return authUser;
    }

    @Override
    public boolean saveUser(User user) {

        int insertedRows = userDao.save(user);
        if(insertedRows > 0) return true;
        else return false;
    }

    @Override
    public boolean alreadyExist(String username) {
        if(userDao.findOneByUsername(username) != null) return true;
        else return false;
    }
}
