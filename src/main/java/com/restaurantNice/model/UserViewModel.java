package com.restaurantNice.model;

import com.restaurantNice.entity.User;

/**
 * Created by Ник on 14.04.2018.
 */
public class UserViewModel {

    private String username;
    private String role;
    private int requestsSize;

    public static UserViewModel buildByUser(User user){
        UserViewModel model = new UserViewModel();
        model.username = user.getUsername();
        model.role = user.getRole().name();
        model.requestsSize = user.getJoinRequests().size();
        return model;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRequestsSize() {
        return requestsSize;
    }

    public void setRequestsSize(int requestsSize) {
        this.requestsSize = requestsSize;
    }
}
