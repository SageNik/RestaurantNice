package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.DishCategoryDao;
import com.restaurantNice.entity.DishCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ник on 13.04.2018.
 */
@Repository
public class MySqlDishCategoryDaoImpl implements DishCategoryDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<DishCategory> FindAllCategory() {

        List<DishCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM dish_category";
        List<Map<String, Object>> dishCategoryMap = jdbcTemplate.queryForList(query);
        mapToDishCategory(categories, dishCategoryMap);
        return categories;
    }

    private void mapToDishCategory(List<DishCategory> categories, List<Map<String, Object>> dishCategoryMap) {
        for (Map<String, Object> row : dishCategoryMap) {
            DishCategory dishCategory = new DishCategory();
            dishCategory.setId((Long) row.get("id"));
            dishCategory.setName((String) row.get("name"));
            dishCategory.setAmountDish((Integer) row.get("amount_dish"));
            categories.add(dishCategory);
        }
    }

    @Override
    public DishCategory findOneById(Long dishCategoryId) {

        String query = "SELECT * FROM dish_category WHERE id=?";
        Object[] params = new Object[]{dishCategoryId};
        return jdbcTemplate.queryForObject(query,params, new BeanPropertyRowMapper<>(DishCategory.class));
    }

    @Override
    public int save(DishCategory dishCategory) {

        String query = "INSERT INTO dish_category (name,amount_dish) VALUES(?,?)";
        Object[] params = new Object[]{dishCategory.getName(),dishCategory.getAmountDish()};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public int update(DishCategory dishCategory) {
        String query = "UPDATE dish_category SET name=?,amount_dish=? WHERE id=?";
        Object[] params = new Object[]{dishCategory.getName(),dishCategory.getAmountDish(),dishCategory.getId()};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public boolean delete(Long dishCategoryId) {
        String query = "DELETE FROM dish_category WHERE id=?";
        Object[] params = new Object[]{dishCategoryId};
        if(jdbcTemplate.update(query,params) > 0) return true;
        else return false;
    }
}
