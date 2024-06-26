package com.itschool.base.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.UUID) // generate a UUID for us (UUID is of type String in Java and VARCHAR in the database)
    private String uuid;
    private String city;
    private String street;
    private Integer number;
    private Long zipCode;

    @OneToOne(mappedBy = "address") // mappedBy is the field name in the User class that owns the relationship
    private User user;

    protected Address() {
        // hibernate needed
    }

    public Address(String city, String street, Integer number, Long zipCode) {
        this.city = city;
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getZipCode() {
        return zipCode;
    }

    public void setZipCode(Long zipCode) {
        this.zipCode = zipCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
