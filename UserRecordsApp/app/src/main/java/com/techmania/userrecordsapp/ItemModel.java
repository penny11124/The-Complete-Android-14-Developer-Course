package com.techmania.userrecordsapp;

public class ItemModel {
    String userName;
    String phoneNum;
    String group;

    public ItemModel() {
    }

    public ItemModel(String userName, String phoneNum, String group) {
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.group = group;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
