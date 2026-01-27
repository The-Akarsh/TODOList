package Controller;

import Model.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static Model.Task.DATE_FORMAT;
import static Model.Task.taskList;


/**
 * A custom Gson TypeAdapter for serializing and deserializing {@link LocalDateTime} objects.
 * It converts {@link LocalDateTime} to a formatted string for JSON storage and parses it back.
 */
class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value != null) {
            out.value(value.format(DATE_FORMAT));
        } else {
            out.nullValue();
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString(), DATE_FORMAT);
    }
}

/**
 * Handles the persistence of tasks to a JSON file.
 * This class provides methods to save the current list of tasks to disk and load them back upon application startup.
 * It uses the Gson library for JSON serialization and deserialization.
 */
public class TaskStorage {
    private static final String folderPath = System.getenv("LOCALAPPDATA") + "\\TODO_GUI";
    private static final String filePath = folderPath + "\\Tasks.json";

    /**
     * Saves the current list of tasks ({@link Model.Task#taskList}) to a JSON file.
     * The file is stored in the user's local application data directory.
     * If the directory does not exist, it is created.
     */
    public static void saveTask(){
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

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

    /**
     * Loads tasks from the JSON file into {@link Model.Task#taskList}.
     * If the file does not exist or is corrupted, it initializes an empty task list.
     * After loading, it updates the task ID counter to ensure new tasks get unique IDs.
     */
    public static void loadTasks() {
        try {
            String json = readJson();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();

            taskList = gson.fromJson(json, taskListType);
            if (taskList == null) {
                taskList = new ArrayList<>();
            }

            // Find the highest ID in the loaded list and initialize the counter
            int maxId = 0;
            for (Task task : taskList) {
                if (task.getTaskNumber() > maxId) {
                    maxId = task.getTaskNumber();
                }
            }
            Task.initLastId(maxId);

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

    /**
     * Reads the content of the JSON file as a string.
     *
     * @return The content of the file as a String, or "[]" if the file does not exist or an error occurs.
     */
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
