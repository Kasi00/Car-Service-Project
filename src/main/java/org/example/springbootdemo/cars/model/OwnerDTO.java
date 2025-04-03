package org.example.springbootdemo.cars.model;

public class OwnerDTO {
    private String username;

    public OwnerDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
