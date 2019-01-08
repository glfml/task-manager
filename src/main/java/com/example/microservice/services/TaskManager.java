package com.example.microservice.services;

import com.example.microservice.dto.AuthResponse;
import com.example.microservice.dto.TaskFilter;
import com.example.microservice.entity.Task;
import com.example.microservice.entity.User;
import com.example.microservice.exception.UnauthorizedException;
import com.example.microservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskManager {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("apiClient1")
    private RestApiClient apiClient;

    public void save(Task task)
    {
        taskRepository.save(task);
    }

    public Task find(Long id)
    {
        return taskRepository.getOne(id);
    }

    public void delete(Task task)
    {
        taskRepository.delete(task);
    }

    public List<Task> findAllfilterBy(TaskFilter filter, int page) throws UnauthorizedException {
        AuthResponse response = apiClient.authenticate();
        if (null == response) {
            throw new UnauthorizedException();
        }

        //todo falta aplicar filtro y paginado
        return taskRepository.findAll(Specification.where(TaskRepository.nameContains(filter.getName())));
    }

    public void share(Task task, User user) {
        task.addUser(user);
        save(task);
    }
}
