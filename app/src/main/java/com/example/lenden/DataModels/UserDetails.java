package com.example.lenden.DataModels;

public class UserDetails {
    private String firebase_id;
    private String phoneNumber;
    private String name;
    private String fathers_name;
    private String email_id;
    private String pincode;
    private String city;
    private String state;
    private String address;
    private String id_type;
    private String id_no;
    private String user_id;
    private String establishment_catg;
    private String imei;
    private String hs_make;
    private String hs_model;
    private String registration_timestamp;
    private double location_lat;
    private double location_long;
    private String created_by;
    private String updated_by;
    private String created_timeStamp;
    private String updated_timeStamp;

    public UserDetails() {
    }

    public UserDetails(String firebase_id, String phoneNumber, String name, String fathers_name, String email_id, String pincode, String city, String state, String address, String id_type, String id_no, String user_id, String establishment_catg, String imei, String hs_make, String hs_model, String registration_timestamp, double location_lat, double location_long, String created_by, String updated_by, String created_timeStamp, String updated_timeStamp) {
        this.firebase_id = firebase_id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.fathers_name = fathers_name;
        this.email_id = email_id;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.address = address;
        this.id_type = id_type;
        this.id_no = id_no;
        this.user_id = user_id;
        this.establishment_catg = establishment_catg;
        this.imei = imei;
        this.hs_make = hs_make;
        this.hs_model = hs_model;
        this.registration_timestamp = registration_timestamp;
        this.location_lat = location_lat;
        this.location_long = location_long;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.created_timeStamp = created_timeStamp;
        this.updated_timeStamp = updated_timeStamp;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getAddress() {
        return address;
    }

    public String getId_type() {
        return id_type;
    }

    public String getId_no() {
        return id_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEstablishment_catg() {
        return establishment_catg;
    }

    public String getImei() {
        return imei;
    }

    public String getHs_make() {
        return hs_make;
    }

    public String getHs_model() {
        return hs_model;
    }

    public String getRegistration_timestamp() {
        return registration_timestamp;
    }

    public double getLocation_lat() {
        return location_lat;
    }

    public double getLocation_long() {
        return location_long;
    }

    public String getCreated_by() {
        return created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public String getCreated_timeStamp() {
        return created_timeStamp;
    }

    public String getUpdated_timeStamp() {
        return updated_timeStamp;
    }
}
