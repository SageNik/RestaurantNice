package com.restaurantNice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 13.04.2018.
 */
public class DishCategory {

    private Long id;
    private String name;
    private Integer amountDish;
    private List<Dish> dishes;

    public DishCategory(Long id, String name) {
        this.id = id;
        this.name = name;
        this.amountDish = 0;
        this.dishes = new ArrayList<>();
    }

    public DishCategory(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmountDish() {
        return amountDish;
    }

    public void setAmountDish(Integer amountDish) {
        this.amountDish = amountDish;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Integer getCurrentAmountDish(){
        return dishes.size();
    }

    @Override
    public String toString() {
        return "DishCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountDish=" + amountDish +
                ", dishes=" + dishes +
                '}';
    }
}
