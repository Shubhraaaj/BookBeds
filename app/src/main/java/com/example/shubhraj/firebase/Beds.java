package com.example.shubhraj.firebase;

/**
 * Created by Shubhraj on 09-09-2017.
 */

public class Beds {
    private String status,id;
    private int price;

    public Beds() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Beds(String id, int price, String status) {
        this.id = id;
        this.status = status;
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }
}