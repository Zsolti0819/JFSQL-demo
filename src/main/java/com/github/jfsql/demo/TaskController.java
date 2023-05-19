package com.github.jfsql.demo;


import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public void createTask(@RequestBody final Task task) {
        log.debug("task = {}", task);
        taskService.createTask(task);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTask() {
        return taskService.selectAllTask();
    }

    @PutMapping("/update/{id}")
    public void updateTask(@RequestBody final Task task, @PathVariable("id") final Long id) {
        log.debug("task = {}, id = {}", task, id);
        taskService.updateTask(task, id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaskById(@PathVariable("id") final Long id) {
        log.debug("id = {}", id);
        taskService.deleteTaskById(id);
    }

}
