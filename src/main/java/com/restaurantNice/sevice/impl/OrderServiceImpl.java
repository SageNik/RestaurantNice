package com.restaurantNice.sevice.impl;

import com.restaurantNice.dao.DishDao;
import com.restaurantNice.dao.OrderDao;
import com.restaurantNice.dao.impl.MySqlOrderDaoImpl;
import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.OrderState;
import com.restaurantNice.sevice.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Ник on 10.04.2018.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    DishDao dishDao;
    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class.getName());


    @Override
    public List<Order> getAllByUser(User user) throws IllegalArgumentException {

        if (user != null && user.getId() != null) return orderDao.findAllByUser(user);
        else throw new IllegalArgumentException("Error. Invalid parameter user in current session");
    }

    @Transactional
    @Override
    public boolean saveOrUpdateOrder(User currentUser, List<Dish> orderDishes, Long orderId) {

        if(orderId == null) {
            Order order = new Order(OrderState.NOT_SENT, currentUser, orderDishes, null);
            if(save(orderDishes, order) != null) return true;
        }else{
            if(update(orderDishes, orderId, OrderState.NOT_SENT)!= null) return true;
        }
        return false;
    }

    private Long update(List<Dish> orderDishes, Long orderId,OrderState orderState) {
        Order currentOrder = getOrderById(orderId);

        if(dishDao.deleteRelatedDishes(orderId,currentOrder.getDishes()) && dishDao.saveRelatedDishes(orderId,orderDishes) ) {
            currentOrder = getOrderById(orderId);
            currentOrder.setSum(currentOrder.getCurrentSum());
            currentOrder.setOrderState(orderState);
            if (orderDao.update(currentOrder) > 0) return orderId;
        }
        return null;
    }

    private Long save(List<Dish> orderDishes, Order order) {
        try {
            Long savedOrderId = orderDao.save(order);
            if (savedOrderId != null) {
                if(dishDao.saveRelatedDishes(savedOrderId, orderDishes));
                return savedOrderId;
            }
                LOGGER.info("ERROR: order hasn't been saved correctly");
                return null;
        } catch (Exception e) {
            LOGGER.info("ERROR: order hasn't been saved");
            return null;
        }
    }

    @Transactional
    @Override
    public boolean saveOrUpdateAndSendOrder(User currentUser, List<Dish> orderDishes, Long orderId) {

        if(orderId == null) {
            Order order = new Order(OrderState.SENT, currentUser, orderDishes, null);
            if(save(orderDishes, order) != null) return true;
        }else{
            if(update(orderDishes, orderId, OrderState.SENT)!= null) return true;
        }
        return false;
    }

    @Override
    public Order getOrderById(Long orderId) {
        Order currentOrder = orderDao.findOneById(orderId);
        if (currentOrder != null) {
            currentOrder.setDishes(dishDao.findAllByOrderId(orderId));
        }
        return currentOrder;
    }

    @Override
    public Order sendOrder(Long orderId) {

        Order currentOrder = getOrderById(orderId);
        int updatedRows;
        if (currentOrder != null) {
            currentOrder.setOrderState(OrderState.SENT);
            updatedRows = orderDao.update(currentOrder);
            if (updatedRows > 0) {
                LOGGER.info("The order with id=" + orderId + " has been successfully sent");
                return currentOrder;
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrderById(Long orderId) {
        if(orderDao.deleteOrderById(orderId) > 0) return true;
        else return false;
    }

    @Transactional
    @Override
    public Long saveOrUpdateOrderForGroupOrder(User currentUser, List<Dish> orderDishes, Long orderId, Long groupOrderId) {
        if(orderId == null) {
            Order order = new Order(OrderState.FOR_GROUP_ORDER, currentUser, orderDishes, groupOrderId);
            return save(orderDishes, order);
        }else{
            return update(orderDishes, orderId, OrderState.FOR_GROUP_ORDER);
        }
    }

}
