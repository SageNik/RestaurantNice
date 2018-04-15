package com.restaurantNice.entity;

import com.restaurantNice.enums.OrderState;

import java.util.List;

/**
 * Created by Ник on 05.04.2018.
 */
public class Order {

    private Long id;
    private OrderState orderState;
    private Long user_id;
    private List<Dish> dishes;
    private double sum;

    public Order(OrderState orderState, Long user_id, List<Dish> dishes) {
        this.orderState = orderState;
        this.user_id = user_id;
        this.dishes = dishes;
        this.sum = getCurrentSum();
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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
                ", user_id=" + user_id +
                ", dishes=" + dishes +
                ", sum=" + sum +
                '}';
    }
}
