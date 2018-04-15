package com.restaurantNice.dao.impl;

import com.mysql.jdbc.Statement;
import com.restaurantNice.dao.DishDao;
import com.restaurantNice.dao.OrderDao;
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
    private DishDao dishDao;
    private static final Logger LOGGER = Logger.getLogger(MySqlOrderDaoImpl.class.getName());


    @Override
    public List<Order> findAllByUser(User user) {

        String query = "SELECT * FROM orders WHERE user_id=?";
        Object[] params = new Object[]{user.getId()};
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
            order.setUser_id((Long) row.get("user_id"));
            OrderState state = Arrays.stream(OrderState.values()).filter(p->p.ordinal()== (Integer) row.get("order_state")).findFirst().get();
            order.setOrderState(state);
            orders.add(order);
        }
    }

    @Override
    public Long save(Order order) {

        String query = "INSERT INTO orders (order_state,user_id,sum) VALUES(?,?,?)";
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1,order.getOrderState().ordinal());
                preparedStatement.setLong(2,order.getUser_id());
                preparedStatement.setDouble(3,order.getSum());
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
        return jdbcTemplate.queryForObject(query,params, new BeanPropertyRowMapper<>(Order.class));
    }

    @Override
    public int update(Order currentOrder) {

        String query = "UPDATE orders SET order_state=?, sum=?, user_id=? WHERE id=?";
        Object[] params = new Object[]{currentOrder.getOrderState().ordinal(),currentOrder.getCurrentSum(),currentOrder.getUser_id(),currentOrder.getId()};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public int deleteOrderById(Long orderId) {

        String query = "DELETE FROM orders WHERE id=?";
        Object[] params = new Object[]{orderId};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public List<Order> findAllByGroupId(Long groupId) {

        String query = "SELECT orders.id,orders.sum,orders.user_id,orders.order_state FROM orders join order_group on orders.id = order_group.order_id WHERE group_id=?";
        Object[] params = new Object[]{groupId};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<Order> orders = new ArrayList<>();
        mapToOrder(orders, orderMap);
        return orders;
    }
}
