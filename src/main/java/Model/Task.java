package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Represents a single task in the application.
 * This class stores all properties related to a task, including its unique ID, name, description,
 * priority, creation timestamp, deadline, and completion status.
 */
public class Task {
    /** Defines the date time format used througout the Application
     * Refer {@link DateTimeFormatter}*/
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("h:mm a dd/MM/yyyy");

    private static int lastId = 0;
    private final int  task_number;
    private int priority;
    private String name,description;
    private final LocalDateTime created_at;
    private LocalDateTime deadline;
    private boolean isComplete;

    /**
     * A static list holding all task instances.
     * This list is used for global access to tasks, including saving to and loading from storage.
     */
    public static ArrayList<Task> taskList = new ArrayList<>();

    /**
     * Constructs a new Task with the specified details.
     * Automatically assigns a unique task number and sets the creation timestamp to the current time.
     *
     * @param name        The name of the task.
     * @param description The description of the task.
     * @param priority    The priority level of the task.
     * @param deadline    The deadline for the task (can be null).
     */
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

    /**
     * Gets the ID of the last created task.
     *
     * @return The last assigned task ID.
     */
    public static int getLastId() {
        return lastId;
    }
    
    /** 
     * Updates the static counter to ensure new tasks get a unique ID.
     * This should be called only once after loading tasks from persistent storage to synchronize the ID counter.
     *
     * @param maxId The highest task ID currently in storage.
     */
    public static void initLastId(int maxId) {
        if (maxId > lastId) {
            lastId = maxId;
        }
    }

    /**
     * Gets the unique number assigned to this task.
     *
     * @return The task number.
     */
    public int getTaskNumber() {return task_number;}


    /**
     * Gets the priority level of the task.
     *
     * @return The priority level.
     */
    public int getPriority() {return priority;}

    /**
     * Sets the priority level of the task.
     *
     * @param priority The new priority level.
     */
    public void setPriority(int priority) {this.priority = priority;}

    /**
     * Gets the name of the task.
     *
     * @return The task name.
     */
    public String getName() {return name;}

    /**
     * Sets the name of the task.
     *
     * @param name The new task name.
     */
    public void setName(String name) {this.name = name;}

    /**
     * Gets the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {return description;}

    /**
     * Sets the description of the task.
     *
     * @param description The new task description.
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Gets the formatted creation timestamp of the task.
     *
     * @return The creation date and time as a formatted string.
     */
    public String getCreated_at() {return created_at.format(DATE_FORMAT);}


    /**
     * Gets the deadline of the task.
     *
     * @return The deadline as a {@link LocalDateTime}, or null if not set.
     */
    public LocalDateTime getDeadLine() {return deadline;}

    /**
     * Sets the deadline for the task.
     *
     * @param deadline The new deadline.
     */
    public void setDeadline(LocalDateTime deadline) {this.deadline = deadline;}

    /**
     * Checks if the task is marked as complete.
     *
     * @return True if the task is complete, false otherwise.
     */
    public boolean isComplete() {return isComplete;}

    /**
     * Sets the completion status of the task.
     *
     * @param complete The new completion status.
     */
    public void setComplete(boolean complete) {isComplete = complete;}
}
