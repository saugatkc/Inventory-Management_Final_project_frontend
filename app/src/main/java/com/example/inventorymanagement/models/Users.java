package com.example.inventorymanagement.models;

public class Users {
    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String username;
    private String contact;
    private String email;
    private String password;

    public Users(String fullname, String username, String contact, String email, String password) {
        this.fullname = fullname;
        this.username = username;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }


}
