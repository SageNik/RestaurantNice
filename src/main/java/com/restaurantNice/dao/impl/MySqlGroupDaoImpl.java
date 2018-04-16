package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.GroupDao;
import com.restaurantNice.dao.GroupOrderDao;
import com.restaurantNice.dao.OrderDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Ник on 13.04.2018.
 */
@Service
public class MySqlGroupDaoImpl implements GroupDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupOrderDao groupOrderDao;
    private static final Logger LOGGER = Logger.getLogger(MySqlGroupDaoImpl.class.getName());

    @Override
    public List<Group> findAll() {

        List<Group> groups = new ArrayList<>();
        String query = "SELECT *FROM `group`";
        List<Map<String,Object>> groupMap = jdbcTemplate.queryForList(query);
        mapToGroup(groups, groupMap);
        return groups;
    }

    @Override
    public boolean save(Group group) {

        String query = "INSERT INTO `group` (name,description,owner_id) VALUES(?,?,?)";
        Object[] params = new Object[]{group.getName(),group.getDescription(),group.getOwner_id()};
        int insertedRows = jdbcTemplate.update(query,params);
        if(insertedRows > 0) {
            LOGGER.info("The new group has been successfully saved");
            return true;
        }
        else return false;
    }

    @Override
    public boolean update(Group group) {

        String query = "UPDATE `group` SET name=?,description=?,owner_id=? WHERE id=?";
        Object[] params = new Object[]{group.getName(),group.getDescription(),group.getOwner_id(), group.getId()};
        int updatedRows = jdbcTemplate.update(query,params);
        if(updatedRows > 0) {
            LOGGER.info("The group with id="+group.getId()+" has been successfully updated");
            return true;
        }
        else return false;
    }

    @Override
    public Group findOneById(Long groupId) {
        String query = "SELECT * FROM `group` WHERE id=?";
        Object[] params = new Object[]{groupId};
        List<Group> groups = new ArrayList<>();
        List<Map<String,Object>> groupMap = jdbcTemplate.queryForList(query,params);
        mapToGroup(groups, groupMap);
        if(groups.isEmpty())return  null;
        else return groups.get(0);
    }

    @Override
    public boolean deleteById(Long groupId) {
        String query = "DELETE  FROM `group` WHERE id=?";
        Object[] params = new Object[]{groupId};
        int deletedRows = jdbcTemplate.update(query,params);
        if(deletedRows > 0) {
            LOGGER.info("The group with id="+groupId+" has been successfully deleted");
            return true;
        }
        else return false;
    }

    @Override
    public boolean quitGroup(Long groupId, Long userId) {
        String query = "DELETE  FROM `user_group` WHERE group_id=? AND user_id=?";
        Object[] params = new Object[]{groupId, userId};
        int deletedRows = jdbcTemplate.update(query,params);
        if(deletedRows > 0) return true;
        else return false;
    }

    private void mapToGroup(List<Group> groups, List<Map<String, Object>> groupMap) {
        for (Map<String, Object> row : groupMap) {
            Group group = new Group();
            group.setId((Long) row.get("id"));
            group.setName((String) row.get("name"));
            group.setDescription((String) row.get("description"));
            group.setOwner_id((Long) row.get("owner_id"));
            group.setUsers(userDao.findAllByGroupId(group.getId()));
            group.setGroupOrders(groupOrderDao.findAllByGroupId(group.getId()));
            groups.add(group);
        }
    }
}
