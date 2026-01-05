# TODO_GUI

A robust and user-friendly Task Management application built with Java Swing. This application allows users to efficiently manage their daily tasks with features like priority setting, deadlines, and persistent storage.

## 🚀 Features

- **Task Management**: Create, edit, and view tasks easily.
- **Detailed Task Properties**:
  - Set task priorities.
  - Add descriptions.
  - Track creation, modification, and deadline dates.
- **Persistent Storage**: Tasks are saved automatically using JSON, ensuring your data is never lost.
- **Clean UI**: A native look and feel interface using Java Swing.

## 🛠️ Tech Stack

- **Language**: Java 25
- **GUI Framework**: Java Swing
- **Build Tool**: Maven
- **Data Serialization**: Google Gson

## 📂 Project Structure

The project follows the MVC (Model-View-Controller) architectural pattern:

- **Model**: Represents the data (Task objects).
- **View**: Handles the UI components (`MainUI`, `TaskUI`).
- **Controller**: Manages logic and data flow (`TaskStorage`, `ManageTask`, `HandleDateTime`).

## ⚙️ Prerequisites

- Java Development Kit (JDK) 25 or higher.
- Maven.

## 📦 Installation & Usage

1.  **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/TODO_GUI.git
    cd TODO_GUI
    ```

2.  **Build the project**
    ```bash
    mvn clean install
    ```

3.  **Run the application**
    You can run the `Main` class located in `src/main/java/Main.java`.

## 🤝 Contributing

This is among my first java swing project. This is an experimental project to learn java swing. So all form of contributions are welcome

## 📝 License

This project is open source.
