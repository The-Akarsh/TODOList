package Controller;

import Model.Task;
import View.MainUI;
import View.TaskUI;

import java.time.LocalDateTime;

    /** Used for taking input from fields of  <code>View.TaskUI</code>. Create and add object of type Task to adds them to array list of
     * type Task in <code>Model.TaskList */
public class ManageTask {
    public static void create(TaskUI taskUI){
        String name;
        if(!taskUI.getName().isBlank()) {
            name = taskUI.getName();
        }
        else{
            name = "Task " +  (Task.getLastId() + 1);
        }
        String description = taskUI.getDescription();
        int priority = taskUI.getPriority();
        String dateTime = taskUI.getDateTime();
        LocalDateTime deadline = null;
        if(dateTime != null)
            deadline = HandleDateTime.stringTOLocalDateTime(dateTime);
        Task newTask = new Task(name, description, priority, deadline);
        Task.taskList.add(newTask);
        TaskStorage.saveTask();
        MainUI.getInstance().refreshTable();
    }
}
