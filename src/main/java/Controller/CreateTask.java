package Controller;

import Model.Task;
import View.TaskUI;

import java.time.LocalDateTime;

public class CreateTask {
    public Task create(TaskUI view){
        String name = view.getName();
        String description = view.getDescription();
        String priority = view.getPriority();
        String dateTime = view.getDateTime();
        LocalDateTime deadline = LocalDateTime.parse(dateTime);

        return new Task(name,description,Integer.parseInt(priority),deadline);
    }
}
