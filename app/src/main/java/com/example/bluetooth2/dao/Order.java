package com.example.bluetooth2.dao;

import java.io.Serializable;

public class Order  implements Serializable {
    private int productId;
    private int branchId;
    private int product;
    private int count;
    private double price;
    private String company;

    public Order() {
        product = 0;
        count = 0;
        price = 0;
    }

    public Order(Order order) {
        this.product = order.product;
        this.count = order.count;
        this.price = order.price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void increaseCountBy(int step){
        this.count += step;
    }
    public void decreaseCountBy(int step){
        this.count -= step;
    }

    public void calculatePrice(double priceFor100ml){
        this.price = this.count * (priceFor100ml / 100.0);
    }


    public void addToPrice(double value){
        this.price += value;
    }
    public void reduceFromPrice(double value){
        this.price -= value;
    }

}
