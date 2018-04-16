package com.restaurantNice.entity;

import com.restaurantNice.enums.OrderState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 15.04.2018.
 */
public class GroupOrder {

    private Long id;
    private Long owner_id;
    private Long group_id;
    private OrderState state;
    private Double sum;
    private List<Order> orders;

    public GroupOrder(Long owner_id, Long group_id) {
        this.owner_id = owner_id;
        this.group_id = group_id;
        this.state = OrderState.NOT_SENT;
        this.orders = new ArrayList<>();
        this.sum = getCurrentSum();
    }

    public GroupOrder(){}

    public double getCurrentSum(){
        double orderSum = 0;
        for(Order order : orders){
            orderSum += order.getSum();
        }
        return orderSum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupOrder that = (GroupOrder) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (owner_id != null ? !owner_id.equals(that.owner_id) : that.owner_id != null) return false;
        if (group_id != null ? !group_id.equals(that.group_id) : that.group_id != null) return false;
        if (state != that.state) return false;
        if (sum != null ? !sum.equals(that.sum) : that.sum != null) return false;
        return orders != null ? orders.equals(that.orders) : that.orders == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner_id != null ? owner_id.hashCode() : 0);
        result = 31 * result + (group_id != null ? group_id.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (sum != null ? sum.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GroupOrder{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", group_id=" + group_id +
                ", state=" + state +
                ", sum=" + sum +
                ", orders=" + orders +
                '}';
    }
}
