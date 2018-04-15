package com.restaurantNice.entity;

import com.restaurantNice.enums.DishType;

/**
 * Created by Ник on 05.04.2018.
 */
public class Dish {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer amount;
    private Long dishCategory_id;
    private DishType dishType;

    public Dish(Long id, String name, String description, Double price, Long dishCategory_id, DishType dishType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.dishCategory_id = dishCategory_id;
        this.amount = 0;
        this.dishType = dishType;
    }

    public Dish(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getDishCategory_id() {
        return dishCategory_id;
    }

    public void setDishCategory_id(Long dishCategory_id) {
        this.dishCategory_id = dishCategory_id;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (id != null ? !id.equals(dish.id) : dish.id != null) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        if (amount != null ? !amount.equals(dish.amount) : dish.amount != null) return false;
        if (dishCategory_id != null ? !dishCategory_id.equals(dish.dishCategory_id) : dish.dishCategory_id != null)
            return false;
        return dishType == dish.dishType;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (dishCategory_id != null ? dishCategory_id.hashCode() : 0);
        result = 31 * result + (dishType != null ? dishType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", dishCategory_id=" + dishCategory_id +
                ", dishType=" + dishType +
                '}';
    }
}
