// package com.example.taskmanager;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

// @SpringBootApplication
// @EnableMongoRepositories(basePackageClasses = TaskRepository.class)  // Ensures Mongo repository is enabled
// public class TaskManagerApplication {

//     public static void main(String[] args) {
//         SpringApplication.run(TaskManagerApplication.class, args);
//     }
// }






package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}

@Document(collection = "tasks")
class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;

    public Task() {}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = "Pending";
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }

    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(String status) { this.status = status; }
}

interface TaskRepository extends MongoRepository<Task, String> {}

@RestController
@RequestMapping("/tasks")
class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody Task task) {
        if (task.getTitle() == null || task.getTitle().isEmpty() || task.getDescription() == null || task.getDescription().isEmpty()) {
            return ResponseEntity.badRequest().body("Title and Description are required!");
        }
        task.setStatus("Pending");
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(task));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        Optional<Task> task = repository.findById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskStatus(@PathVariable String id, @RequestBody Task updatedTask) {
        if (updatedTask.getStatus() == null || (!updatedTask.getStatus().equalsIgnoreCase("Pending") && !updatedTask.getStatus().equalsIgnoreCase("Completed"))) {
            return ResponseEntity.badRequest().body("Invalid status! Use 'Pending' or 'Completed'.");
        }

        return repository.findById(id).map(task -> {
            task.setStatus(updatedTask.getStatus());
            repository.save(task);
            return ResponseEntity.ok(task);
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Task deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
    }
}
