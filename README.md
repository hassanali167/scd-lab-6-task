# Task Management System

## Overview
This is a simple **Task Management System** built using **Java, Spring Boot, and MongoDB**. It provides a REST API to create, retrieve, update, and delete tasks. The project uses **MongoDB in a Docker container** for data storage.

## Purpose
The main purpose of this project is to provide a **simple yet effective task management solution** that allows users to organize and track their daily tasks. With the ability to create, update, and delete tasks, users can efficiently manage their workload. This system ensures **data persistence** using MongoDB and provides an easy-to-use **REST API** for interaction.

## How It Works
1. **User Interaction:** Users send HTTP requests to the REST API to perform operations such as creating, updating, or deleting tasks.
2. **Data Storage:** All tasks are stored in MongoDB, running inside a Docker container.
3. **Spring Boot Backend:** The backend handles API requests and interacts with the MongoDB database to store and retrieve tasks.
4. **Task Management:** Each task has a title, description, and status (`Pending` or `Completed`). Users can modify the status as needed.

## Technologies Used
- **Java 17**
- **Spring Boot** (Spring Web, Spring Data MongoDB)
- **MongoDB** (Docker Image on port 27017)
- **Docker**

## Prerequisites
Before running this project, ensure you have:
- **Java 17** installed
- **Maven** installed
- **Docker** installed and running

## Setup and Installation
### 1. Clone the Repository
```sh
git clone https://github.com/your-username/task-management-system.git
cd task-management-system
```

### 2. Run MongoDB in Docker
```sh
docker run -d --name mongodb -p 27017:27017 mongo
```

### 3. Configure MongoDB in `application.properties`
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/taskmanager
spring.data.mongodb.database=taskmanager
```

### 4. Build and Run the Application
```sh
mvn spring-boot:run
```
The application will start on **http://localhost:8080**

## API Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST   | /tasks  | Create a new task |
| GET    | /tasks  | Retrieve all tasks |
| GET    | /tasks/{id} | Retrieve a task by ID |
| PUT    | /tasks/{id} | Update task status |
| DELETE | /tasks/{id} | Delete a task |

## Testing the API
### Create a Task
```sh
curl -X POST http://localhost:8080/tasks \
     -H "Content-Type: application/json" \
     -d '{"title": "Buy groceries", "description": "Milk, eggs, and bread"}'
```

### Retrieve All Tasks
```sh
curl -X GET http://localhost:8080/tasks
```

### Retrieve a Task by ID
```sh
curl -X GET http://localhost:8080/tasks/{id}
```

### Update Task Status
```sh
curl -X PUT http://localhost:8080/tasks/{id} \
     -H "Content-Type: application/json" \
     -d '{"status": "Completed"}'
```

### Delete a Task
```sh
curl -X DELETE http://localhost:8080/tasks/{id}
```

## Future Enhancements
- Add **User Authentication**
- Implement **Task Due Dates & Priorities**
- Improve **Error Handling & Logging**
- Create a **Frontend UI**

## License
This project is open-source. Feel free to contribute!

---
📌 **Contributions are welcome!** Fork the repo, create a branch, and submit a pull request. 🚀

