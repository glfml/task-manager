package com.example.microservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(targetEntity = Task.class, fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonIgnore
    private List<Task> ownerTasks;

    @ManyToMany(targetEntity = Task.class, fetch = FetchType.LAZY)
    @JoinTable(name = "tasks_users", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "task_id")})
    @JsonIgnore
    private List<Task> tasks;

    public Long getId() {
        return id;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getOwnerTasks() {
        return ownerTasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
