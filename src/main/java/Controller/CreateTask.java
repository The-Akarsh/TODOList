package Controller;

import Model.Task;
import Model.TaskList;

import java.time.LocalDateTime;

public class CreateTask {
    public static void create(String name, String description, int priority, String dateTime){

        LocalDateTime deadline = HandleDateTime.stringTOLocalDateTime(dateTime);
        Task newTask = new Task(name, description, priority, deadline);
        TaskList.taskList.add(newTask);
    }
}
