package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.GroupDao;
import com.restaurantNice.dao.GroupOrderDao;
import com.restaurantNice.dao.OrderDao;
import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.GroupOrder;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.OrderState;
import com.restaurantNice.model.GroupOrderViewModel;
import com.restaurantNice.sevice.GroupOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 15.04.2018.
 */
@Service
public class GroupOrderServiceImpl implements GroupOrderService {

    @Autowired
    private GroupOrderDao groupOrderDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public List<GroupOrder> getAllGroupOrders() {
        List<GroupOrder> groupOrders = groupOrderDao.findAll();
        for(GroupOrder order : groupOrders){
            order.setOrders(orderDao.findAllByGroupOrderId(order.getId()));
            order.setSum(order.getCurrentSum());
        }
        return groupOrders;
    }

    @Transactional
    @Override
    public boolean createGroupOrder(Long currentUserId, Long groupId) {
        Group currentGroup = groupDao.findOneById(groupId);
        if(currentGroup != null){
        GroupOrder groupOrder = new GroupOrder(currentUserId, currentGroup.getId());
        return (groupOrderDao.save(groupOrder));
        }else return false;

    }
    @Transactional
    @Override
    public List<GroupOrderViewModel> getAllGroupOrderModels(User currentUser) {

        List<GroupOrderViewModel> models = new ArrayList<>();
        List<GroupOrder> groupOrders = getAllGroupOrders();
        for(GroupOrder groupOrder : groupOrders) {
            getGroupOrderViewModel(currentUser, models, groupOrder);
        }
        return models;
    }

    private void getGroupOrderViewModel(User currentUser, List<GroupOrderViewModel> models, GroupOrder groupOrder) {
        Group currentGroup = groupDao.findOneById(groupOrder.getGroup_id());
        Long myOrderId = getMyOrderId(currentUser, groupOrder);

        if (currentGroup != null) {
            if (currentGroup.getOwner_id().equals(currentUser.getId()) || currentGroup.getUsers().contains(currentUser))
                models.add(GroupOrderViewModel.buildByGroupOrder(groupOrder,currentGroup.getName(), "Member",myOrderId));
            else if (currentUser.getRole().name().equals("ADMIN"))
                models.add(GroupOrderViewModel.buildByGroupOrder(groupOrder, currentGroup.getName(),"ADMIN",myOrderId));
            else models.add(GroupOrderViewModel.buildByGroupOrder(groupOrder,currentGroup.getName(), "",myOrderId));
        }
    }

    private Long getMyOrderId(User currentUser, GroupOrder groupOrder) {
        Long myOrderId = null;
        for(Order order : groupOrder.getOrders()){
            if(order.getUser().getId().equals(currentUser.getId())) myOrderId = order.getId();
        }
        return myOrderId;
    }

    @Transactional
    @Override
    public GroupOrder getOneById(Long groupOrderId) {

        GroupOrder groupOrder = groupOrderDao.findOneById(groupOrderId);
        if(groupOrder != null) {
            groupOrder.setOrders(orderDao.findAllByGroupOrderId(groupOrderId));
            groupOrder.setSum(groupOrder.getCurrentSum());
        }
        return groupOrder;
    }

    @Transactional
    @Override
    public boolean deleteGroupOrderById(Long groupOrderId) {

        GroupOrder groupOrder = getOneById(groupOrderId);
        if(groupOrder == null)return  false;
            for(Order order : groupOrder.getOrders()){
                if(orderDao.deleteOrderById(order.getId()) == 0) return false;
            }
            return groupOrderDao.deleteById(groupOrderId);
    }

    @Transactional
    @Override
    public boolean sendGroupOrder(Long groupOrderId) {

        GroupOrder groupOrder = getOneById(groupOrderId);
        if(groupOrder == null) return false;
            groupOrder.setState(OrderState.SENT);
        return groupOrderDao.update(groupOrder);
    }
}
