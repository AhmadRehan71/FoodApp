package com.mabnets.foodapp.model;

public class Order {

    int id;
    String customer;
    String  item;

    public Order(int id, String customer, String item) {
        this.id = id;
        this.customer = customer;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
