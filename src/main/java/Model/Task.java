/**
 * This class is for storing and tracking a single task properties
 */
package Model;

import Controller.HandleDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task {
    private static int lastId = 0;
    private final int  task_number;
    private int priority;
    private String name,description;
    private final LocalDateTime created_at;
    private LocalDateTime deadline;
    private boolean isComplete;

    /** Holds an array of all the tasks. Data type is of Task object
     * Used for writting and reading Tasks in JSON file and other operations */
    public static ArrayList<Task> taskList = new ArrayList<>();

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
    public static int getLastId() {
        return lastId;
    }
    
    /** 
     * Updates the static counter to ensure new tasks get a unique ID.
     * Should be called only once after loading tasks from file.
     */
    public static void initLastId(int maxId) {
        if (maxId > lastId) {
            lastId = maxId;
        }
    }

    public int getTaskNumber() {return task_number;}


    public int getPriority() {return priority;}

    public void setPriority(int priority) {this.priority = priority;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getCreated_at() {return created_at.format(HandleDateTime.dateTimeFormat);}


    public LocalDateTime getDeadLine() {return deadline;}

    public void setDeadline(LocalDateTime deadline) {this.deadline = deadline;}

    public boolean isComplete() {return isComplete;}

    public void setComplete(boolean complete) {isComplete = complete;}
}
