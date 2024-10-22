package com.itschool.base.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity // Annotation to tell Hibernate that this class is an entity and should be persisted in the database
@Table(name = "users") // Annotation to tell Hibernate that this entity should be mapped to the 'users' table in the database
public class User {

    @Id // Annotation to tell Hibernate that this field is the primary key in the table
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Annotation to tell Hibernate to generate new ids for us. GenerationType.IDENTITY will use the database's auto-increment feature (will increment the id by 1)
    private Long id;
    private String name;
    private String email;
    private int age;

    @OneToOne(cascade = CascadeType.ALL) // CascadeType.ALL will propagate all operations (CRUD) to the related entity
    @JoinColumn(name = "address_uuid", referencedColumnName = "uuid") // name will be the column name in the 'users' table, referencedColumnName is the column name in the 'addresses' table
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // mappedBy is used to specify the field in the Order class that owns the relationship and fetch is used to specify how the orders should be fetched (LAZY will fetch the orders only when they are accessed)
    private List<Order> orders;

    @Transient  // this marks the field as transient, meaning it will not be persisted in the database (can be used for business logic, etc.)
    private String test;

    protected User() {
        // hibernate needs a non-arg constructor, otherwise it will fail
    }

    // constructors needed for business logic in the service layer
    public User(String name, String email, int age, Address address, List<Order> orders) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
        this.orders = orders;
    }

    // getters and setters needed for hibernate serialization/deserialization
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
