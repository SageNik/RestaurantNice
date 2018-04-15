package com.restaurantNice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 05.04.2018.
 */
public class Group {

    private Long id;
    private Long owner_id;
    private String name;
    private String description;
    private List<User> users;
    private List<Order> orders;

    public Group(Long id, Long owner_id, String name, String description) {
        this.id = id;
        this.owner_id = owner_id;
        this.name = name;
        this.description = description;
        this.users = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public Group(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != null ? !id.equals(group.id) : group.id != null) return false;
        if (owner_id != null ? !owner_id.equals(group.owner_id) : group.owner_id != null) return false;
        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        if (description != null ? !description.equals(group.description) : group.description != null) return false;
        if (users != null ? !users.equals(group.users) : group.users != null) return false;
        return orders != null ? orders.equals(group.orders) : group.orders == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (owner_id != null ? owner_id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (orders != null ? orders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", users=" + users +
                ", orders=" + orders +
                '}';
    }
}
