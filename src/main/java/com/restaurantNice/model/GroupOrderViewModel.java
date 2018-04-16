package com.restaurantNice.model;

import com.restaurantNice.entity.GroupOrder;

/**
 * Created by Ник on 16.04.2018.
 */
public class GroupOrderViewModel {

    private Long number;
    private String groupName;
    private Long myOrderId;
    private double sum;
    private String myState;

    public static GroupOrderViewModel buildByGroupOrder(GroupOrder groupOrder,String groupName, String myState, Long myOrderId){
        GroupOrderViewModel model = new GroupOrderViewModel();
        model.number = groupOrder.getId();
        model.groupName = groupName;
        model.sum = groupOrder.getSum();
        model.myState = myState;
        model.myOrderId = myOrderId;
        return model;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getMyState() {
        return myState;
    }

    public void setMyState(String myState) {
        this.myState = myState;
    }

    public Long getMyOrderId() {
        return myOrderId;
    }

    public void setMyOrderId(Long myOrderId) {
        this.myOrderId = myOrderId;
    }
}
