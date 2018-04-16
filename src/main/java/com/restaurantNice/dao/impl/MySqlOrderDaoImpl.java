package com.restaurantNice.dao.impl;

import com.mysql.jdbc.Statement;
import com.restaurantNice.dao.DishDao;
import com.restaurantNice.dao.OrderDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.Group;
import com.restaurantNice.entity.Order;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Ник on 10.04.2018.
 */
@Repository
public class MySqlOrderDaoImpl implements OrderDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DishDao dishDao;
    private static final Logger LOGGER = Logger.getLogger(MySqlOrderDaoImpl.class.getName());

    @Transactional
    @Override
    public List<Order> findAllByUser(User user) {

        String query = "SELECT * FROM orders WHERE user_id=? AND order_state IN(?,?)";
        Object[] params = new Object[]{user.getId(),OrderState.NOT_SENT.ordinal(),OrderState.SENT.ordinal()};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<Order> orders = new ArrayList<>();
        mapToOrder(orders, orderMap);
        return orders;
    }

    private void mapToOrder(List<Order> orders, List<Map<String, Object>> orderMap) {
        for (Map<String, Object> row : orderMap) {
            Order order = new Order();
            order.setId((Long) row.get("id"));
            order.setSum((Double) row.get("sum"));
            Long userId = (Long) row.get("user_id");
            order.setUser(userDao.findOneById(userId));
            order.setGroupOrder_id((Long) row.get("group_order_id"));
            OrderState state = Arrays.stream(OrderState.values()).filter(p->p.ordinal()== (Integer) row.get("order_state")).findFirst().get();
            order.setDishes(dishDao.findAllByOrderId(order.getId()));
            order.setOrderState(state);
            orders.add(order);
        }
    }

    @Override
    public Long save(Order order) {

        String query = "INSERT INTO orders (order_state,user_id,sum) VALUES(?,?,?)";
        if(order.getGroupOrder_id() != null) query = "INSERT INTO orders (order_state,user_id,sum,group_order_id) VALUES(?,?,?,?)";
        KeyHolder key = new GeneratedKeyHolder();
        String finalQuery = query;
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement preparedStatement = connection.prepareStatement(finalQuery, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,order.getOrderState().ordinal());
                preparedStatement.setLong(2,order.getUser().getId());
                preparedStatement.setDouble(3,order.getSum());
                if(order.getGroupOrder_id() != null) preparedStatement.setLong(4,order.getGroupOrder_id());
                return preparedStatement;
            }
        },key);
            LOGGER.info("Order has been saved with id="+key.getKey());
        return key.getKey().longValue();
    }

    @Override
    public Order findOneById(Long orderId) {

        String query = "SELECT * FROM orders WHERE id=?";
        Object[] params = new Object[]{orderId};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<Order> orders = new ArrayList<>();
        mapToOrder(orders, orderMap);
        if(orderMap.isEmpty())return null;
        else return orders.get(0);
    }

    @Override
    public int update(Order currentOrder) {

        String query = "UPDATE orders SET order_state=?, sum=?, user_id=? WHERE id=?";
        Object[] params = new Object[]{currentOrder.getOrderState().ordinal(),currentOrder.getCurrentSum(),currentOrder.getUser().getId(),currentOrder.getId()};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public int deleteOrderById(Long orderId) {

        String query = "DELETE FROM orders WHERE id=?";
        Object[] params = new Object[]{orderId};
        return jdbcTemplate.update(query,params);
    }

    @Transactional
    @Override
    public List<Order> findAllByGroupOrderId(Long groupOrderId) {
        String query = "SELECT * FROM orders WHERE order_state=? AND group_order_id=?";
        Object[] params = new Object[]{OrderState.FOR_GROUP_ORDER.ordinal(),groupOrderId};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<Order> orders = new ArrayList<>();
        mapToOrder(orders, orderMap);
        return orders;
    }
}
