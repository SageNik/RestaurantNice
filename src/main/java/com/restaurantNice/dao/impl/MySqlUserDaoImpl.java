package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.JoinRequestDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.JoinRequest;
import com.restaurantNice.entity.User;
import com.restaurantNice.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by Ник on 07.04.2018.
 */
@Repository
public class MySqlUserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JoinRequestDao joinRequestDao;

    @Override
    public User getOneByNameAndPassword(String name, String password) {

        String query = "SELECT * FROM user WHERE username=? AND password=?";
        Object[] params = new Object[]{name,password};
        return findUser(query, params);
    }

    @Override
    public User findOneByUsername(String username) {

        String query = "SELECT * FROM user WHERE username=? ";
        Object[] params = new Object[]{username};
        return findUser(query, params);
    }

    @Override
    public User findOneById(Long userId) {
        String query = "SELECT * FROM user WHERE id=? ";
        Object[] params = new Object[]{userId};
        return findUser(query, params);
    }

    @Override
    public boolean addUserToGroup(Long userId, Long groupId) {
        String query = "INSERT INTO user_group (user_id,group_id) VALUES(?,?)";
        Object[] params =  new Object[]{userId, groupId};
        int insertedRows = jdbcTemplate.update(query,params);
        if(insertedRows > 0) return true;
        else return false;
    }

    private User findUser(String query, Object[] params) {
        User foundUser = null;
        try{
            foundUser = jdbcTemplate.queryForObject(query,params,new BeanPropertyRowMapper<>(User.class));
        }catch(EmptyResultDataAccessException e){
            return foundUser;
        }
        return foundUser;
    }

    @Override
    public int save(User user) {

        String query = "INSERT INTO user (username,password,role) VALUES(?,?,?)";
        Object[] params =  new Object[]{user.getUsername(),user.getPassword(),user.getRole().ordinal()};
        return jdbcTemplate.update(query,params);
    }

    @Override
    public List<User> findAllByGroupId(Long groupId) {
        String query = "SELECT user.id,user.username,user.password,user.role FROM user join user_group on user.id = user_group.user_id WHERE group_id=?";
        Object[] params = new Object[]{groupId};
        List<Map<String,Object>> userMap = jdbcTemplate.queryForList(query,params);
        List<User> users = new ArrayList<>();
        mapToUser(users, userMap);
        return users;
    }
    private void mapToUser(List<User> users, List<Map<String, Object>> userMap) {
        for (Map<String, Object> row : userMap) {
            User user = new User();
            user.setId((Long) row.get("id"));
            user.setUsername((String) row.get("username"));
            user.setPassword((String) row.get("password"));
            Role role = Arrays.stream(Role.values()).filter(p->p.ordinal()== (Integer) row.get("role")).findFirst().get();
            user.setRole(role);
            user.setJoinRequests(joinRequestDao.findAllByUserId(user.getId()));
            users.add(user);
        }
    }

}
