package com.example.microservice.repository;

import com.example.microservice.entity.Task;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    static Specification<Task> nameContains(String name) {
        return (task, cq, cb) -> cb.like(task.get("name"), "%" + name + "%");
    }
}
