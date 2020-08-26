package com.example.inventorymanagement.models;

public class Materials {

    private String material;
    private String stock;
    private String _id;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Materials(String material, String stock) {
        this.material = material;
        this.stock = stock;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }



}
