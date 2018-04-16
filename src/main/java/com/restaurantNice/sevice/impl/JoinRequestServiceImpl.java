package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.JoinRequestDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.JoinRequest;
import com.restaurantNice.entity.User;
import com.restaurantNice.sevice.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Created by Ник on 14.04.2018.
 */
@Service
public class JoinRequestServiceImpl implements JoinRequestService{

    @Autowired
    private JoinRequestDao joinRequestDao;
    @Autowired
    private UserDao userDao;
    private static final Logger LOGGER = Logger.getLogger(JoinRequestServiceImpl.class.getName());

    @Override
    public boolean sendJoinRequest(Group group, User currentUser) {
        JoinRequest joinRequest = joinRequestDao.findOneByParams(currentUser.getId(),group.getOwner_id(), group.getId());
        if(joinRequest == null) {
            joinRequest = new JoinRequest(currentUser, group);
            return joinRequestDao.send(joinRequest);
        }else return true;
    }

    @Transactional
    @Override
    public boolean acceptRequest(Long joinRequestId) {

        JoinRequest joinRequest = joinRequestDao.findOneById(joinRequestId);
        if(joinRequest != null){
            if(userDao.addUserToGroup(joinRequest.getJoinUser().getId(), joinRequest.getGroup().getId())) {
                return joinRequestDao.deleteById(joinRequestId);
            }else {
                LOGGER.info("The join request with id="+joinRequestId+" hasn't been accept correctly");
                return false;
            }
        }else{
            LOGGER.info("Error: join request with id="+joinRequestId+" hasn't been found");
            return false;
        }
    }

    @Override
    public boolean rejectRequest(Long joinRequestId) {
                return joinRequestDao.deleteById(joinRequestId);
    }

    @Override
    public JoinRequest getOneById(Long joinRequestId) {
        return joinRequestDao.findOneById(joinRequestId);
    }
}
