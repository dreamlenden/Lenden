package com.example.lenden.DataModels;

public class TransactionModel {
    private String trans_id;
    private String cust_mobileNumber;
    private String cust_id;
    private String trans_type;
    private String trans_amt;
    private String Userid;
    private String timestamp;
    private String created_by;
    private String updated_by;
    private String created_timeStamp;
    private String updated_timeStamp;

    public TransactionModel() {
    }

    public TransactionModel(String trans_id, String cust_mobileNumber, String cust_id, String trans_type, String trans_amt, String userid, String timestamp, String created_by, String updated_by, String created_timeStamp, String updated_timeStamp) {
        this.trans_id = trans_id;
        this.cust_mobileNumber = cust_mobileNumber;
        this.cust_id = cust_id;
        this.trans_type = trans_type;
        this.trans_amt = trans_amt;
        Userid = userid;
        this.timestamp = timestamp;
        this.created_by = created_by;
        this.updated_by = updated_by;
        this.created_timeStamp = created_timeStamp;
        this.updated_timeStamp = updated_timeStamp;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public String getCust_mobileNumber() {
        return cust_mobileNumber;
    }

    public String getCust_id() {
        return cust_id;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public String getTrans_amt() {
        return trans_amt;
    }

    public String getUserid() {
        return Userid;
    }

    public String getTimestamp() {
        return timestamp;
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
