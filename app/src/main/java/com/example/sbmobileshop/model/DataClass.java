package com.example.sbmobileshop.model;

public class DataClass {
    private String dataTitle;
    private String dataDesc;
    private String dataLang;
    private String dataImage;
    private String key;
    private String quantity;
    private String price, discount;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getDataTitle() {
        return dataTitle;
    }
    public String getDataDesc() {
        return dataDesc;
    }
    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String dataTitle, String dataDesc,String quantity, String price, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataImage = dataImage;
        this.quantity = quantity;
        this.price = price;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public void setDataLang(String dataLang) {
        this.dataLang = dataLang;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public DataClass(){
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
