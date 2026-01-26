package Controller;

import Model.Task;
import View.MainUI;

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
        TaskStorage.saveTask();
        // Check for null to prevent crash if MainUI isn't fully ready (safety check)
        if (MainUI.getInstance() != null) {
            MainUI.getInstance().refreshTable();
        }
    }
/** Create a new task from the input of TaskUI*/
    public static void create(String name,String description,int priority,LocalDateTime deadline ){
        name = checkName(name);
        description = checkDescription(description);
        Task newTask = new Task(name, description, priority,deadline );
        taskList.add(newTask);
        finalSave();
    }
/** Edit existing task by using the inputs from taskUI*/
    public static void edit(Task task,String name,String description,int priority,LocalDateTime deadline,boolean isComplete){
        task.setName(checkName(name));
        task.setDescription(checkDescription(description));
        task.setPriority(priority);
        task.setDeadline(deadline);
        task.setComplete(isComplete);
        taskList.set(task.getTaskNumber() -1, task);
        finalSave();
    }
/** Removes task from <code>Model.Task.taskList</code> and updates the UI*/
    public static void delete(Task task){
        taskList.remove(task);
        finalSave();
    }
}
