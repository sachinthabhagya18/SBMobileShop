package com.example.sbmobileshop.model;

import java.text.SimpleDateFormat;

public class MyCartModel {

    String prductname;
    String productPrice;
    String currentDate;
    String currentTime;
    String totalQuntity;
    int totalPrice;
    private String key;

    public MyCartModel(String prductname, String currentDate, String currentTime, String totalQuntity, String totalPrice) {
        this.prductname = prductname;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQuntity = totalQuntity;
        this.totalPrice = Integer.parseInt(totalPrice);
    }

    public MyCartModel(SimpleDateFormat currentDate, SimpleDateFormat currentTime) {
        this.currentDate = String.valueOf(currentDate);
        this.currentTime = String.valueOf(currentTime);
    }

    public MyCartModel(SimpleDateFormat currentDate, SimpleDateFormat currentTime, String totalQuntity, String totalPrice) {
        this.currentDate = String.valueOf(currentDate);
        this.currentTime = String.valueOf(currentTime);
        this.totalQuntity = totalQuntity;
        this.totalPrice = Integer.parseInt(totalPrice);
    }

    public MyCartModel(String productPrice, String currentDate, String currentTime, String totalQuntity) {
        this.productPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQuntity = totalQuntity;
    }

    public MyCartModel(String prductname, String productPrice, String currentDate, String currentTime, String totalQuntity, int totalPrice) {
        this.prductname = prductname;
        this.productPrice = productPrice;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.totalQuntity = totalQuntity;
        this.totalPrice = totalPrice;
    }

    public String getPrductname() {
        return prductname;
    }

    public void setPrductname(String prductname) {
        this.prductname = prductname;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getTotalQuntity() {
        return totalQuntity;
    }

    public void setTotalQuntity(String totalQuntity) {
        this.totalQuntity = totalQuntity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public MyCartModel() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
