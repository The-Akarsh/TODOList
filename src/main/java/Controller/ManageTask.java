package Controller;

import Model.Task;
import View.MainUI;

import javax.swing.*;
import java.time.LocalDateTime;

import static Model.Task.taskList;

/** Used for taking input from fields of  <code>View.TaskUI</code>. Create and add object of type Task to adds them to array list of
     * type Task in <code>Model.TaskList */
public class ManageTask {


    private static String checkName(String name){
        if(name.isBlank()){
            return "Task " + (Task.getLastId() + 1);
        }
        return name;
    }
    private static String checkDescription(String description){
        if(description.isBlank()){
            return "Created at " + HandleDateTime.LocalDateTimeToString(LocalDateTime.now());
        }
        return description;
    }

    /** Runs <code>Controller.TaskStorage.saveTask</code> and refreshes main window
     *  using <code>View.MainUI.getInstance().refreshTable()</code>*/
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
/** Create a new task from the input of TaskUI*/
    public static void create(String name,String description,int priority,LocalDateTime deadline ){
        name = checkName(name);
        description = checkDescription(description);
        Task newTask = new Task(name, description, priority,deadline);
        taskList.add(newTask);
        finalSave();
    }
/** Edit existing task by using the inputs from taskUI*/
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
/** Removes task from <code>Model.Task.taskList</code> and updates the UI*/
    public static void delete(Task task){
        taskList.remove(task);
        finalSave();
    }
}
