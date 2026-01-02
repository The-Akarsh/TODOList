package Controller;

import Model.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static Controller.HandleDateTime.dateTimeFormat;
import static Model.Task.taskList;


class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value != null) {
            out.value(value.format(dateTimeFormat));
        } else {
            out.nullValue();
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString(), dateTimeFormat);
    }
}
/** Uses Gson Library to perform operations on JSON file Located at <code>Model.task(.json)</code>*/
public class TaskStorage {
    private static final String filePath = "src/main/java/Model/Tasks.json";
    public static void saveTask(){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        String json = gson.toJson(Task.taskList);
        try(FileWriter writer = new FileWriter(filePath)){
            writer.write(json);
        } catch (IOException e) {
            System.out.println("Error while saving task list to file (" + filePath + "): " + e.getMessage());
        }
    }

    public static void loadTasks() {
        try {
            // . Read the text from the file (using your existing Json class)
            String json = readJson();

            // 2. Setup Gson (exactly like before)
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            //  Define the specific type we want back: ArrayList<Task>
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();

            //  Convert the JSON string back into Objects
            taskList = gson.fromJson(json, taskListType);
            // Check if list is null (in case file was empty)
            if (taskList == null) {
                taskList = new ArrayList<>();
            }

            // Sync the static counter with the size of the loaded list
            Task.setLastID(taskList.size());

        } catch (JsonSyntaxException e) {
            System.err.println("Error parsing tasks from JSON. The file might be corrupted.");
            e.printStackTrace();
            taskList = new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Failed to load data.");
            e.printStackTrace();
            if (taskList == null) {
                taskList = new ArrayList<>();
            }
        }
    }
    public static String readJson() {
        try{
            Path path = Paths.get(filePath);
            if(Files.exists(path)){
                return Files.readString(path);
            }
        }catch (IOException e){
            System.out.println("Error reading json file ("+ filePath+")");
        }
        return "[]";
    }
}