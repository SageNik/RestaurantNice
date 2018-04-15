package com.restaurantNice.entity;

/**
 * Created by Ник on 05.04.2018.
 */
public class JoinRequest {

    private Long id;
    private User joinUser;
    private Long ownerGroup_id;
    private Group group;

    public JoinRequest(User joinUser, Group group) {
        this.joinUser = joinUser;
        this.ownerGroup_id = group.getOwner_id();
        this.group = group;
    }
    public JoinRequest(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getJoinUser() {
        return joinUser;
    }

    public void setJoinUser(User joinUser) {
        this.joinUser = joinUser;
    }

    public Long getOwnerGroup_id() {
        return ownerGroup_id;
    }

    public void setOwnerGroup_id(Long ownerGroup_id) {
        this.ownerGroup_id = ownerGroup_id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
