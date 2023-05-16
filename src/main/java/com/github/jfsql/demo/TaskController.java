package com.github.jfsql.demo;


import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task")
    public void createTask(@RequestBody final Task task) {
        taskService.createTask(task);
    }

    @PutMapping("/task/{id}")
    public void updateTask(@RequestBody final Task task, @PathVariable("id") final Long id) {
        System.out.println("task = " + task + ", id = " + id);
        taskService.updateTask(task, id);
    }

    @PutMapping("/tasks")
    public void updateTasks(@RequestBody final List<Task> tasks) throws SQLException {
        taskService.updateTasks(tasks);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTask() {
        return taskService.selectAllTask();
    }

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable("id") final Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaskById(@PathVariable("id") final Long id) {
        System.out.println("id = " + id);
        taskService.deleteTaskById(id);
    }


}
