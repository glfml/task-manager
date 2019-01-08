package com.example.microservice.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max = 100)
    private String name;

    @Length(max = 100)
    private String description;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany(targetEntity = User.class, fetch = FetchType.LAZY, mappedBy = "tasks")
    private List<User> users;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    public Task() {
        createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getOwner() {
        return owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        user.addTask(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

