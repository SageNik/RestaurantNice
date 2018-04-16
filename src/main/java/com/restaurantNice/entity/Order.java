package com.restaurantNice.entity;

import com.restaurantNice.enums.OrderState;

import java.util.List;

/**
 * Created by Ник on 05.04.2018.
 */
public class Order {

    private Long id;
    private OrderState orderState;
    private User user;
    private List<Dish> dishes;
    private double sum;
    private Long groupOrder_id;

    public Order(OrderState orderState, User user, List<Dish> dishes, Long groupOrder_id) {
        this.orderState = orderState;
        this.user = user;
        this.dishes = dishes;
        this.sum = getCurrentSum();
        this.groupOrder_id = groupOrder_id;
    }

    public Order() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public Long getGroupOrder_id() {
        return groupOrder_id;
    }

    public void setGroupOrder_id(Long groupOrder_id) {
        this.groupOrder_id = groupOrder_id;
    }

    public double getCurrentSum(){
        int sum = 0;
        for(Dish dish : dishes){
            sum += dish.getPrice()*100*dish.getAmount();
        }
        return sum/100;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderState=" + orderState +
                ", user=" + user +
                ", dishes=" + dishes +
                ", sum=" + sum +
                ", groupOrder_id=" + groupOrder_id +
                '}';
    }
}
