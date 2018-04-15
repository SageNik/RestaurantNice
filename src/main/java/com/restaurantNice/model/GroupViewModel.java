package com.restaurantNice.model;

import com.restaurantNice.entity.Group;

/**
 * Created by Ник on 13.04.2018.
 */
public class GroupViewModel {

    private Long groupId;
    private String groupName;
    private String groupDescription;
    private String myStatus;

    public static  GroupViewModel buildByGroup(Group group, String status){
        GroupViewModel model = new GroupViewModel();
        model.groupName = group.getName();
        model.groupDescription = group.getDescription();
        model.groupId = group.getId();
        model.myStatus = status;
        return model;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(String myStatus) {
        this.myStatus = myStatus;
    }
}
