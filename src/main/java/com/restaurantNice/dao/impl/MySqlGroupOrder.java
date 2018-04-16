package com.restaurantNice.dao.impl;

import com.restaurantNice.dao.GroupDao;
import com.restaurantNice.dao.GroupOrderDao;
import com.restaurantNice.entity.GroupOrder;
import com.restaurantNice.enums.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Ник on 15.04.2018.
 */
@Repository
public class MySqlGroupOrder  implements GroupOrderDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private GroupDao groupDao;

    @Override
    public List<GroupOrder> findAll() {
        String query = "SELECT * FROM group_order";
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query);
        List<GroupOrder> orders = new ArrayList<>();
        mapToGroupOrder(orders, orderMap);
        return orders;
    }

    @Override
    public List<GroupOrder> findAllByGroupId(Long groupId) {
        String query = "SELECT * FROM group_order WHERE group_id=?";
        Object[] params = new Object[]{groupId};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<GroupOrder> orders = new ArrayList<>();
        mapToGroupOrder(orders, orderMap);
        return orders;
    }

    @Override
    public boolean save(GroupOrder groupOrder) {
        String query = "INSERT INTO group_order (order_state,sum,owner_id,group_id) VALUES(?,?,?,?)";
        Object[] params = new Object[]{groupOrder.getState().ordinal(),groupOrder.getSum(),groupOrder.getOwner_id(),groupOrder.getGroup_id()};
        int insertedRows = jdbcTemplate.update(query,params);
        if(insertedRows > 0) return true;
        else return false;
    }

    @Override
    public GroupOrder findOneById(Long groupOrderId) {
        String query = "SELECT * FROM group_order WHERE id=?";
        Object[] params = new Object[]{groupOrderId};
        List<Map<String,Object>> orderMap = jdbcTemplate.queryForList(query,params);
        List<GroupOrder> orders = new ArrayList<>();
        mapToGroupOrder(orders, orderMap);
        if(orders.isEmpty()) return null;
        else return orders.get(0);
    }

    @Override
    public boolean deleteById(Long groupOrderId) {
        String query = "DELETE FROM group_order WHERE id=?";
        Object[] params = new Object[]{groupOrderId};
        int deletedRows = jdbcTemplate.update(query,params);
        if(deletedRows > 0)return true;
        else return false;
    }

    @Override
    public boolean update(GroupOrder groupOrder) {
        String query = "UPDATE group_order SET order_state=? WHERE id=?";
        Object[] params = new Object[]{groupOrder.getState().ordinal(), groupOrder.getId()};
        int deletedRows = jdbcTemplate.update(query,params);
        if(deletedRows > 0)return true;
        else return false;
    }

    private void mapToGroupOrder(List<GroupOrder> orders, List<Map<String, Object>> orderMap) {
        for (Map<String, Object> row : orderMap) {
            GroupOrder groupOrder = new GroupOrder();
            groupOrder.setId((Long) row.get("id"));
            groupOrder.setSum((Double) row.get("sum"));
            groupOrder.setOwner_id((Long) row.get("owner_id"));
            OrderState state = Arrays.stream(OrderState.values()).filter(p->p.ordinal()== (Integer) row.get("order_state")).findFirst().get();
            groupOrder.setState(state);
            groupOrder.setGroup_id((Long)row.get("group_id"));
            orders.add(groupOrder);
        }
    }
}
