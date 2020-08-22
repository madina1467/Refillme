package com.example.bluetooth2.dao;

import com.example.bluetooth2.App;
import com.example.bluetooth2.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order  implements Serializable {
    private Long orderId;
    private int branchId;
    private int productId;
    private String productName;
    private int amount;
    private double price;
    private String paidByCompany;
    private Date date;
    private String city;
    private int paymentStatus;

    public Order() {
        this.date = new Date();
        this.orderId = date.getTime();
        this.productId = 0;
        this.amount = 0;
        this.price = 0;
        this.branchId = 3474; //TODO correct branch #
        this.paymentStatus = 1;
        this.city = "Nur-Sultan";
    }

    public String getOrderDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(this.date);
    }

    public String getOrderTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(this.date);
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;

        switch (productId){
            case 1:
                this.productName = App.getAppResources().getString(R.string.name_product1);
                break;
            case 2:
                this.productName = App.getAppResources().getString(R.string.name_product2);
                break;
            case 3:
                this.productName = App.getAppResources().getString(R.string.name_product4);
                break;
            case 4:
                this.productName = App.getAppResources().getString(R.string.name_product3);
                break;
        }

    }

    public void addToPrice(double value){
        this.price += value;
    }
    public void reduceFromPrice(double value){
        this.price -= value;
    }

    public String getCity() {
        return city;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaidByCompany() {
        return paidByCompany;
    }

    public void setPaidByCompany(String paidByCompany) {
        this.paidByCompany = paidByCompany;
    }

    public void increaseCountBy(int step){
        this.amount += step;
    }
    public void decreaseCountBy(int step){
        this.amount -= step;
    }

    public void calculatePrice(double priceFor100ml){
        this.price = this.amount * (priceFor100ml / 100.0);
    }

    public Long getOrderId() {
        return orderId;
    }

    public int getBranchId() {
        return branchId;
    }

    public Date getDate() {
        return date;
    }

    public String getProductName() {
        return productName;
    }

    public int getPaymentStatus() {
        return paymentStatus;
    }
}
