package com.example.microservice.controller;

import com.example.microservice.dto.TaskFilter;
import com.example.microservice.dto.ValidationError;
import com.example.microservice.entity.Task;
import com.example.microservice.entity.User;
import com.example.microservice.exception.UnauthorizedException;
import com.example.microservice.services.TaskManager;
import com.example.microservice.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskManager taskManager;
    @Autowired
    private UserManager userManager;

    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody @Valid Task task, BindingResult result) {
        ResponseEntity<Object> error = checkValidation(result);
        if (error != null) return error;

        taskManager.save(task);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    private ResponseEntity<Object> checkValidation(BindingResult result) {
        if (result.hasErrors()) {

            StringBuilder builder = new StringBuilder();
            result.getFieldErrors().stream().forEach(f -> {
                builder.append(f.getField());
                builder.append(": ");
                builder.append(f.getDefaultMessage());
                builder.append("\n");
            });

            ValidationError error = new ValidationError(builder.toString());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    @RequestMapping(value = "/update/{task}", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> update(@PathVariable(value = "task") Long id, @RequestBody @Valid Task task, BindingResult result) {
        ResponseEntity<Object> error = checkValidation(result);
        if (error != null) return error;

        Task aTask = taskManager.find(id);
        task.setId(aTask.getId());

        taskManager.save(task);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{task}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "task") Long id) {
        Task task = taskManager.find(id);
        taskManager.delete(task);
    }

    @RequestMapping(value = "/show/{task}", method = RequestMethod.GET)
    public Task show(@PathVariable(value = "task") Long id) {
        Task task = taskManager.find(id);

        return task;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> index(
            TaskFilter filter,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestHeader("X-Caller-Id") String callerId) {

        //todo el chequeo de seguridad debe refactorizarse
        List<Task> tasks = null;
        try {
            tasks = taskManager.findAllfilterBy(filter, page);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/share/{task}/{user}", method = RequestMethod.POST)
    public void share(@PathVariable(value = "task") Long taskId, @PathVariable(value = "user") Long userId) {
        Task task = taskManager.find(taskId);
        User user = userManager.find(userId);
        //todo solo se puede compartir una tarea propia // unauthorized

        taskManager.share(task, user);
    }

    @RequestMapping(value = "/users/{task}", method = RequestMethod.GET)
    public List<User> users(@PathVariable(value = "task") Long taskId) {
        Task task = taskManager.find(taskId);

        //todo solo se puede compartir una tarea propia // unauthorized

        return task.getUsers();
    }
}
