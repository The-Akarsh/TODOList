# TODO GUI Application

A professional, feature-rich TODO list application built with Java Swing. This application allows users to manage their tasks efficiently with features like priority setting, deadline tracking, and task status management.

## Features

*   **Create Tasks:** Easily add new tasks with a name, description, priority level, and optional deadline.
*   **Edit Tasks:** Modify existing tasks to update their details or mark them as complete.
*   **Delete Tasks:** Remove tasks that are no longer needed.
*   **View Tasks:** View detailed information about a specific task in a read-only mode.
*   **Task List View:** See all your tasks in a table format, displaying key information like name, priority, deadline, and status (Pending/Done).
*   **Priority Management:** Assign priority levels (1-5) to tasks to focus on what matters most.
*   **Deadline Tracking:** Set deadlines with specific dates and times.
*   **Data Persistence:** Tasks are automatically saved to a JSON file (`Tasks.json`), ensuring your data is preserved between sessions.
*   **User-Friendly Interface:** Clean and intuitive GUI built using Java Swing.

## Project Specification

*   **Language:** Java
*   **Java Version:** JDK 25 (Compatible with modern Java versions)
*   **Build Tool:** Maven
*   **GUI Framework:** Java Swing
*   **Data Format:** JSON (using Gson library)
*   **Dependencies:**
    *   `com.google.code.gson:gson:2.10.1` - For JSON serialization and deserialization.
    
## Limitations


* **No alerts:** Currently there is no alerts when setting deadline
* **No sorting options:** Currently task list is sorted according to date created ascendingly 
* **Outdated UI:** The UI is bare-bone and lacks features
* **Repeating names:** Name is assigned acording to number of currently present task, so names may repeat
* **Manual deletion:** Completed tasks has to be deleted manually


## Project Structure

The project follows a Model-View-Controller (MVC) architectural pattern:

```
src/main/java/
├── Controller/
│   ├── HandleDateTime.java    # Utilities for date and time formatting/parsing
│   ├── ManageTask.java        # Logic for creating, editing, and deleting tasks
│   └── TaskStorage.java       # Handles loading and saving tasks to JSON
├── Model/
│   ├── Task.java              # Data model representing a single task
│   └── Tasks.json             # Persistent storage file for tasks
├── View/
│   ├── MainUI.java            # Main application window displaying the task list
│   └── TaskUI.java            # Window for creating/editing/viewing a task
└── Main.java                  # Entry point of the application
```

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 25 or compatible version.
*   Maven installed on your system.

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/The-Akarsh/TODOList.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd TODO_GUI
    ```
3.  **Build the project using Maven:**
    ```bash
    mvn clean install
    ```
4.  **Run the application:**
    You can run the `Main` class from your IDE or use Maven to execute it.

## Usage

1.  **Launch the App:** Run the application to open the main window.
2.  **Add a Task:** Click the "New task(+)" button. Fill in the details and click "Save".
3.  **View/Edit/Delete:** Select a task from the list.
    *   Click "View" (or double-click the row) to see details.
    *   Click "Edit" to modify the task.
    *   Click "Delete" to remove the task.
4.  **Mark as Complete:** Open a task in "Edit" mode and check the "Mark as completed" box.

## Author

**Akarsh A**

---
*Built with ❤️ using Java Swing.*
