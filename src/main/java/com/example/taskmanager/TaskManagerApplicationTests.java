package com.example.taskmanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.TestRestTemplate;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)  // Random port for integration testing
public class TaskManagerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void contextLoads() {
        assertNotNull(taskRepository);  // Ensure repository is injected
    }

    @Test
    public void testCreateTask() {
        Task task = new Task("Test Task", "This is a test task", "Pending");
        
        // Send POST request to create a task
        Task createdTask = restTemplate.postForObject("/tasks", task, Task.class);
        
        // Ensure that the task is created
        assertNotNull(createdTask);
        assertNotNull(createdTask.getId());
    }

    @Test
    public void testGetAllTasks() {
        // Send GET request to retrieve all tasks
        Task[] tasks = restTemplate.getForObject("/tasks", Task[].class);
        
        // Ensure there are tasks available
        assertNotNull(tasks);
        assert(tasks.length > 0);
    }
}
