package Controller;

import Model.Task;
import View.MainUI;
import View.TaskUI;

import java.time.LocalDateTime;

import static Model.Task.taskList;

/** Used for taking input from fields of  <code>View.TaskUI</code>. Create and add object of type Task to adds them to array list of
     * type Task in <code>Model.TaskList */
public class ManageTask {
/** Return string from nameBox in TaskUI if field is not null, else returns string "Task " + lastIndex*/
    private static String getNameFromUI(TaskUI taskUI) {
        if (!taskUI.getName().isBlank()) {
            return taskUI.getName();
        }
        return "Task " + (Task.getLastId() + 1);
    }

/** Returns LocalDateTime if present else return null*/
    private static LocalDateTime getDeadlineFromUI(TaskUI taskUI) {
        String dateTime = taskUI.getDateTime();
        if (dateTime != null) {
            return HandleDateTime.stringTOLocalDateTime(dateTime);
        }
        return null;
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
    public static void create(TaskUI taskUI){
        String name = getNameFromUI(taskUI);
        LocalDateTime deadline = getDeadlineFromUI(taskUI);
        Task newTask = new Task(name, taskUI.getDescription(), taskUI.getPriority(), deadline);
        taskList.add(newTask);
        finalSave();
    }
/** Edit existing task by using the inputs from taskUI*/
    public static void edit(TaskUI taskUI, Task task){
        task.setName(getNameFromUI(taskUI));
        task.setDescription(taskUI.getDescription());
        task.setPriority(taskUI.getPriority());
        String dateTime = taskUI.getDateTime();
        task.setDeadline(getDeadlineFromUI(taskUI));
        taskList.set(task.task_number() -1, task);
        finalSave();
    }
}
