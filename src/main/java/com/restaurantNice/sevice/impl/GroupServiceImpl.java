package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.GroupDao;
import com.restaurantNice.dao.OrderDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.User;
import com.restaurantNice.model.GroupViewModel;
import com.restaurantNice.sevice.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupDao groupDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public List<Group> getAllGroups() {
        List<Group> groups = groupDao.findAll();
        for(Group group : groups){
            group.setOrders(orderDao.findAllByGroupId(group.getId()));
            group.setUsers(userDao.findAllByGroupId(group.getId()));
        }
        return groups;
    }

    @Override
    public List<GroupViewModel> getAllGroupsModel(User authorizedUser) {

        List<GroupViewModel> models = new ArrayList<>();
        List<Group> groups = getAllGroups();
        for(Group group : groups){
            if(group.getOwner_id().equals(authorizedUser.getId()))
                models.add(GroupViewModel.buildByGroup(group,"Owner"));
            else if(group.getUsers().contains(authorizedUser))
                models.add(GroupViewModel.buildByGroup(group,"Member"));
            else models.add(GroupViewModel.buildByGroup(group,""));
        }
        return models;
    }

    @Transactional
    @Override
    public boolean saveOrUpdateGroup(Group group) {

        if(group.getId() == null) return save(group);
        return update(group);
    }

    @Override
    public Group getLightOneById(Long groupId) {
        return groupDao.findOneById(groupId);
    }

    @Override
    public boolean deleteGroupById(Long groupId) {
        return groupDao.deleteById(groupId);
    }

    @Override
    public boolean quitGroup(Long groupId, Long userId) {
        return groupDao.quitGroup(groupId, userId);
    }

    private boolean update(Group group) {
        return (groupDao.update(group));
    }

    private boolean save(Group group){
        return (groupDao.save(group));
    }
}
