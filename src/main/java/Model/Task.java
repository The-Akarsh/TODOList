/**
 * This class is for storing and tracking a single task properties
 */
package Model;

import java.time.LocalDateTime;

public class Task {
    private static int lastId;
    private int  task_number, priority;
    private String name,description;
    private LocalDateTime created_at, deadline;
    private boolean isComplete;



    public Task(String name, String description, int priority,LocalDateTime deadline ){
        ++lastId;
        this.task_number = lastId;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.created_at = LocalDateTime.now();
        this.deadline = deadline;
        this.isComplete = false;
    }

    public int task_number() {
        return task_number;
    }

    public Task setTask_number(int task_number) {
        this.task_number = task_number;
        return this;
    }

    public int priority() {
        return priority;
    }

    public Task setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public String name() {
        return name;
    }

    public Task setName(String name) {
        this.name = name;
        return this;
    }

    public String description() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }


    public LocalDateTime deadline() {
        return deadline;
    }

    public Task setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
        return this;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public Task setComplete(boolean complete) {
        isComplete = complete;
        return this;
    }
}
