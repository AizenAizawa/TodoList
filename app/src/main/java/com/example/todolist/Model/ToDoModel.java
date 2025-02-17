package com.example.todolist.Model;

public class ToDoModel {

    private String task;
    private int id, status;

    // ✅ Default constructor (needed for database operations)
    public ToDoModel() {}

    // ✅ Constructor with parameters
    public ToDoModel(int id, String task, int status) {
        this.id = id;
        this.task = task;
        this.status = status;
    }

    // ✅ Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
