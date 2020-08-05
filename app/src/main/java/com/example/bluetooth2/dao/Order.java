package com.example.bluetooth2.dao;

public class Order {
    public int product;
    public int count;
    public double price;

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
}
