package com.example.inventorymanagement.models;

public class DailyProducts {
    private String createdAt;
    public Products product;
    private String quantity;
    private String damaged;
    private String remaining;
    private String _id;




    public DailyProducts(Products product, String quantity, String damaged, String remaining) {
        this.product = product;
        this.quantity = quantity;
        this.damaged = damaged;
        this.remaining = remaining;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDamaged() {
        return damaged;
    }

    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }

    public String getRemaining() {
        return remaining;
    }

    public void setRemaining(String remaining) {
        this.remaining = remaining;
    }



}
