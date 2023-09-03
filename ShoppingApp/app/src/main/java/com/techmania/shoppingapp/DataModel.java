package com.techmania.shoppingapp;

public class DataModel {
    String userName;
    String password;

    //Default constructor is required for calls to DataSnapshot.getValue(YOUR CLASS)
    public DataModel() {
    }

    public DataModel(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
