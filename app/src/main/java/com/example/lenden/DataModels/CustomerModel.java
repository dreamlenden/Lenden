package com.example.lenden.DataModels;

public class CustomerModel {
    private String customer_id;
    private String phoneNumber;
    private String name;
    private String address;
    private String state;
    private String city;

    public CustomerModel() {
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public CustomerModel(String customer_id, String phoneNumber, String name, String address, String state, String city) {
        this.customer_id = customer_id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.address = address;
        this.state = state;
        this.city = city;
    }
}
