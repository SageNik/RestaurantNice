package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.GroupDao;
import com.restaurantNice.dao.JoinRequestDao;
import com.restaurantNice.dao.UserDao;
import com.restaurantNice.entity.JoinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ник on 14.04.2018.
 */
@Repository
public class MySqlJoinRequestDaoImpl implements JoinRequestDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    public boolean send(JoinRequest joinRequest) {

        String query = "INSERT INTO join_request (join_user_id,group_id,owner_group_id) VALUES(?,?,?)";
        Object[] params = new Object[]{joinRequest.getJoinUser().getId(),joinRequest.getGroup().getId(),joinRequest.getOwnerGroup_id()};
        int insertedRows = jdbcTemplate.update(query,params);
        if(insertedRows > 0)return true;
        else return false;
    }

    @Transactional
    @Override
    public List<JoinRequest> findAllByUserId(Long userId) {

        String query = "SELECT * FROM `join_request` WHERE owner_group_id=?";
        Object[] params = new Object[]{userId};
        List<JoinRequest> requests = new ArrayList<>();
        List<Map<String,Object>> reqMapRows = jdbcTemplate.queryForList(query,params);
        mapToJoinRequest(requests, reqMapRows);
        return requests;
    }

    @Override
    public JoinRequest findOneById(Long joinRequestId) {

        String query = "SELECT * FROM join_request WHERE id=?";
        Object[] params = new Object[]{joinRequestId};
        List<JoinRequest> requests = new ArrayList<>();
        List<Map<String,Object>> reqMapRows = jdbcTemplate.queryForList(query,params);
        mapToJoinRequest(requests, reqMapRows);
        if(requests.isEmpty())return null;
        else return requests.get(0);
    }

    @Override
    public boolean deleteById(Long joinRequestId) {
        String query = "DELETE FROM join_request WHERE id=?";
        Object[] params = new Object[]{joinRequestId};
        int insertedRows = jdbcTemplate.update(query,params);
        if(insertedRows > 0)return true;
        else return false;
    }

    @Override
    public JoinRequest findOneByParams(Long joinUserId, Long groupOwnerId, Long groupId) {
        String query = "SELECT * FROM join_request WHERE join_user_id=? AND owner_group_id=? AND group_id=?";
        Object[] params = new Object[]{joinUserId,groupOwnerId,groupId};
        List<JoinRequest> requests = new ArrayList<>();
        List<Map<String,Object>> reqMapRows = jdbcTemplate.queryForList(query,params);
        mapToJoinRequest(requests, reqMapRows);
        if(requests.isEmpty())return null;
        else return requests.get(0);
    }

    private void mapToJoinRequest(List<JoinRequest> requests, List<Map<String, Object>> reqMapRows) {
        for (Map row : reqMapRows){
            JoinRequest joinRequest = getJoinRequest(row);
            requests.add(joinRequest);
        }
    }

    private JoinRequest getJoinRequest(Map row) {
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setId((Long)row.get("id"));
        joinRequest.setOwnerGroup_id((Long)row.get("owner_group_id"));
        Long joinUserId = (Long)row.get("join_user_id");
        Long groupId = (Long)row.get("group_id");
        joinRequest.setJoinUser(userDao.findOneById(joinUserId));
        joinRequest.setGroup(groupDao.findOneById(groupId));
        return joinRequest;
    }
}
