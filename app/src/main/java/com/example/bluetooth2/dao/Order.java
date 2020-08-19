package com.example.bluetooth2.dao;

import java.io.Serializable;
import java.util.Date;

public class Order  implements Serializable {
    private Long orderId;
    private int branchId;
    private int product;
    private int amount;
    private double price;
    private String paidByCompany;
    private Date date;

    public Order() {
        this.date = new Date();
        this.orderId = date.getTime();
        this.product = 0;
        this.amount = 0;
        this.price = 0;
        this.branchId = 1; //TODO correct branch #
    }

    public Order(Order order) {
        this.product = order.product;
        this.amount = order.amount;
        this.price = order.price;
        this.date = order.date;

    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
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


    public void addToPrice(double value){
        this.price += value;
    }
    public void reduceFromPrice(double value){
        this.price -= value;
    }

}
