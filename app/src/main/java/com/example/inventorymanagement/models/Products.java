package com.example.inventorymanagement.models;

public class Products {
    private String product;
    private String cost;
    private String description;
    private String stock;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }




    public Products(String product, String cost, String description, String stock) {
        this.product = product;
        this.cost = cost;
        this.description = description;
        this.stock = stock;
    }



    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }


}
