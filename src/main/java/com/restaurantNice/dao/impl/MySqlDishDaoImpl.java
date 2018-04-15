package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.DishDao;
import com.restaurantNice.entity.Dish;
import com.restaurantNice.entity.DishCategory;
import com.restaurantNice.enums.DishType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Ник on 10.04.2018.
 */
@Repository
public class MySqlDishDaoImpl implements DishDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(MySqlDishDaoImpl.class.getName());

    @Override
    public List<Dish> findAll() {

        List<Dish> dishes = new ArrayList<>();
        String query = "SELECT * FROM dish";
        List<Map<String,Object>> dishMap = jdbcTemplate.queryForList(query);
        mapToDish(dishes, dishMap);
        return dishes;
    }

    private void mapToDish(List<Dish> dishes, List<Map<String, Object>> dishMap) {
        for(Map<String,Object> row : dishMap){
            Dish dish = new Dish();
            dish.setId((Long)row.get("id"));
            dish.setName((String)row.get("name"));
            dish.setDescription((String)row.get("description"));
            dish.setPrice((Double) row.get("price"));
            dish.setAmount((Integer) row.get("amount"));
            DishType type = Arrays.stream(DishType.values()).filter(p->p.ordinal()== (Long) row.get("dish_type")).findFirst().get();
            dish.setDishType((type) );
            dish.setDishCategory_id((Long)row.get("dish_category_id"));
            dishes.add(dish);
        }
    }

    @Override
    public List<Dish> findAllByType(DishType dishType) {
        String query = "SELECT * FROM dish WHERE dish_type=?";
        Object[] params = new Object[]{dishType.ordinal()};
        List<Dish> dishes = new ArrayList<>();
        List<Map<String,Object>> dishMap = jdbcTemplate.queryForList(query,params);
        mapToDish(dishes, dishMap);
        return dishes;
    }

    @Override
    public Dish findOneById(Long dishId) {

        String query = "SELECT * FROM dish WHERE id=?";
        Object[] params = new Object[]{dishId};
        Dish foundDish = null;
        try{
            foundDish = jdbcTemplate.queryForObject(query,params,new BeanPropertyRowMapper<>(Dish.class));
        }catch(EmptyResultDataAccessException e){
            LOGGER.info("ERROR: "+ e.getMessage() + e.getCause().toString());
            return foundDish;
        }
        return foundDish;
    }

    @Override
    public Long save(Dish dish) {

        String query = "INSERT INTO dish (name,description,price,amount,dish_category_id,dish_type) VALUES(?,?,?,?,?,?)";
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,dish.getName());
                preparedStatement.setString(2,dish.getDescription());
                preparedStatement.setDouble(3,dish.getPrice());
                preparedStatement.setInt(4,dish.getAmount());
                preparedStatement.setLong(5,dish.getDishCategory_id());
                preparedStatement.setInt(6,dish.getDishType().ordinal());
                return preparedStatement;
            }
        },key);
         LOGGER.info("Dish has been saved with id="+key.getKey());
        return key.getKey().longValue();
    }

    @Transactional
    @Override
    public boolean saveRelatedDishes(Long savedOrderId, List<Dish> orderDishes) {

        String query = "INSERT INTO order_dish (order_id,dish_id) VALUES(?,?)";
        for(Dish dish : orderDishes){
            Long savedDishId = saveAsDishForOrder(dish);
            if(savedDishId != null) {
                Object[] params = new Object[]{savedOrderId, savedDishId};
                jdbcTemplate.update(query, params);
                LOGGER.info("The dish with id="+savedDishId+" that related with order(id="+savedOrderId+") has been saved");
            }else{
                LOGGER.info("The dishes that related with order(id="+savedOrderId+") hasn't been saved");
                return false;
            }
        }
        LOGGER.info("The dishes that related with order(id="+savedOrderId+") has been saved");
        return true;
    }

    @Transactional
    @Override
    public boolean deleteRelatedDishes(Long savedOrderId, List<Dish> orderDishes) {
        String query = "DELETE  FROM order_dish WHERE order_id=? AND dish_id=?";
        for (Dish dish : orderDishes) {
            Object[] params = new Object[]{savedOrderId, dish.getId()};
            int deletedRows = jdbcTemplate.update(query, params);
            if (deletedRows > 0 && delete(dish.getId())) {
                LOGGER.info("The dish with id=" + dish.getId() + " that related with order(id=" + savedOrderId + ") has been deleted");
            } else {
                LOGGER.info("The dishes that related with order(id=" + savedOrderId + ") hasn't been deleted");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Long dishId) {

        String query = "DELETE FROM dish WHERE id=?";
        Object[] params = new Object[]{dishId};
        int deletedRows = jdbcTemplate.update(query, params);
        if(deletedRows > 0) return true;
        else return false;
    }

    @Override
    public int update(Dish dish) {

        String query = "UPDATE dish SET name=?,description=?,price=?,amount=?,dish_type=?,dish_category_id=? WHERE id=?";
        Object[] params = new Object[]{dish.getName(),dish.getDescription(),dish.getPrice(), dish.getAmount(),dish.getDishType().ordinal(),dish.getDishCategory_id(),dish.getId()};
        return jdbcTemplate.update(query,params);
    }

    private Long saveAsDishForOrder(Dish dish) {
        dish.setId(null);
        dish.setDishType(DishType.FOR_ORDER);
        return save(dish);
    }

    @Override
    public List<Dish> findAllByOrderId(Long orderId) {

        String query = "SELECT dish.id, dish.name,dish.description,dish.price,dish.amount,dish.dish_type,dish.dish_category_id FROM dish join order_dish on dish.id = order_dish.dish_id WHERE order_id=?";
        Object[] params = new Object[]{orderId};
        List<Dish> dishes = new ArrayList<>();
        List<Map<String,Object>> dishMap = jdbcTemplate.queryForList(query,params);
        mapToDish(dishes, dishMap);
        return dishes;
    }

    @Override
    public List<Dish> findAllByTypeAndDishCategoryId(DishType type, Long dishCategoryId) {

        String query = "SELECT * FROM dish WHERE dish_type=? AND dish_category_id=?";
        Object[] params = new Object[]{type.ordinal(),dishCategoryId};
        List<Dish> dishes = new ArrayList<>();
        List<Map<String,Object>> dishMap = jdbcTemplate.queryForList(query,params);
        mapToDish(dishes, dishMap);
        return dishes;
    }


}
