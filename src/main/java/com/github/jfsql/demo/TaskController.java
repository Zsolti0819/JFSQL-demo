package com.github.jfsql.demo;


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
        System.out.println("TaskController.createTask");
        System.out.println("task = " + task);
        taskService.createTask(task);
    }

    @PutMapping("/task/{id}")
    public void updateTask(@RequestBody final Task task, @PathVariable("id") final Long id) {
        System.out.println("TaskController.updateTask");
        System.out.println("task = " + task + ", id = " + id);
        taskService.updateTask(task, id);
    }

    @GetMapping("/tasks")
    public List<Task> getAllTask() {
        System.out.println("TaskController.getAllTask");
        return taskService.selectAllTask();
    }

    @GetMapping("/update/{id}")
    public Task getTaskById(@PathVariable("id") final Long id) {
        System.out.println("TaskController.getTaskById");
        System.out.println("id = " + id);
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTaskById(@PathVariable("id") final Long id) {
        System.out.println("TaskController.deleteTaskById");
        System.out.println("id = " + id);
        taskService.deleteTaskById(id);
    }

    @GetMapping("/tasks?completed={filterValue}")
    public List<Task> filterTaskByCompletion(final String status) {
        System.out.println("TaskController.filterTaskByCompletion");
        System.out.println("status = " + status);
        return taskService.selectTasksByCompletion(status);
    }

}
