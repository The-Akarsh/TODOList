package Controller;

import Model.Task;
import View.MainUI;

import javax.swing.*;
import java.time.LocalDateTime;

import static Model.Task.taskList;

/**
 * Controller class responsible for managing task operations.
 * It handles the creation, modification, and deletion of tasks, acting as a bridge
 * between the UI ({@link View.TaskUI}) and the data model ({@link Model.Task}).
 */
public class ManageTask {

    /**
     * Validates and processes the task name.
     * If the name is blank, it assigns a default name based on the next available task ID.
     *
     * @param name The input name for the task.
     * @return The processed name.
     */
    private static String checkName(String name){
        if(name.isBlank()){
            return "Task " + (Task.getLastId() + 1);
        }
        return name;
    }

    /**
     * Validates and processes the task description.
     * If the description is blank, it assigns a default description with the current timestamp.
     *
     * @param description The input description for the task.
     * @return The processed description.
     */
    private static String checkDescription(String description){
        if(description.isBlank()){
            return "Created at " + LocalDateTime.now().format(Task.DATE_FORMAT);
        }
        return description;
    }

    /**
     * Saves the current state of tasks to storage and refreshes the main UI table.
     * The save operation is performed on a separate thread to avoid blocking the UI,
     * while the UI update is scheduled on the Event Dispatch Thread.
     */
    private static void finalSave() {
        // Run I/O in a separate thread
        new Thread(() -> {
            TaskStorage.saveTask();

            // Update UI back on the main thread
            SwingUtilities.invokeLater(() -> {
                if (MainUI.getInstance() != null) {
                    MainUI.getInstance().refreshTable();
                }
            });
        }).start();
    }

    /**
     * Creates a new task based on the provided parameters and adds it to the task list.
     * Triggers a save and UI refresh after creation.
     *
     * @param name        The name of the task.
     * @param description The description of the task.
     * @param priority    The priority level of the task.
     * @param deadline    The deadline for the task (can be null).
     */
    public static void create(String name,String description,int priority,LocalDateTime deadline ){
        name = checkName(name);
        description = checkDescription(description);
        Task newTask = new Task(name, description, priority,deadline);
        taskList.add(newTask);
        finalSave();
    }

    /**
     * Edits an existing task with new values.
     * Updates the task in the list and triggers a save and UI refresh.
     *
     * @param originalTask The original task object to be modified.
     * @param name         The new name for the task.
     * @param description  The new description for the task.
     * @param priority     The new priority level.
     * @param deadline     The new deadline (can be null).
     * @param isComplete   The new completion status.
     */
    public static void edit(Task originalTask, String name,String description,int priority,LocalDateTime deadline,boolean isComplete){
        int index = taskList.indexOf(originalTask);
        name  = checkName(name);
        originalTask.setName(name);
        originalTask.setDescription(description);
        originalTask.setPriority(priority);
        originalTask.setDeadline(deadline);
        originalTask.setComplete(isComplete);
        taskList.set(index, originalTask);
        finalSave();
    }

    /**
     * Deletes a specified task from the task list.
     * Triggers a save and UI refresh after deletion.
     *
     * @param task The task to be removed.
     */
    public static void delete(Task task){
        taskList.remove(task);
        finalSave();
    }
}
