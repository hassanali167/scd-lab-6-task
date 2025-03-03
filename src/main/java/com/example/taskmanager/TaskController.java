package com.example.taskmanager;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        task.setStatus("Pending");
        return repository.save(task);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Task updateTaskStatus(@PathVariable String id, @RequestBody Task updatedTask) {
        return repository.findById(id).map(task -> {
            task.setStatus(updatedTask.getStatus());
            return repository.save(task);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        repository.deleteById(id);
    }
}
