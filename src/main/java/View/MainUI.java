package View;

import Controller.ManageTask;
import Model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * The main user interface of the application.
 * This class represents the primary window where users can view the list of tasks,
 * add new tasks, and perform actions like viewing, editing, or deleting existing tasks.
 * It implements the Singleton pattern to ensure only one instance of the main window exists.
 */
public class MainUI extends JFrame implements ActionListener {

    private static MainUI instance;
    private static Task currentTask;
    JButton newTaskButton, viewButton, editButton, deleteButton;
    JTable taskTable;
    DefaultTableModel tableModel;
    JLabel taskCountLabel;


    /**
     * Constructs the MainUI window.
     * Initializes the frame, sets up the layout, creates buttons, the task table,
     * and configures event listeners.
     */
    public MainUI(){
        super("TODO LIST App");
        instance = this;

        setLogo(this);
        setSize(400,600);
        setLocationRelativeTo(null); // To always open it at center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        String titleText = "<html><font size = 8>TODO LIST</font></html>";
        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(10)); // Spacer
        add(topPanel, BorderLayout.NORTH);

        // Setting up buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        newTaskButton = createButton("New task(+)", buttonPanel);
        viewButton = createButton("View", buttonPanel);
        editButton = createButton("Edit", buttonPanel);
        deleteButton = createButton("Delete", buttonPanel);

        viewButton.setEnabled(false);
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        topPanel.add(buttonPanel);

        // Task Count text
        taskCountLabel = new JLabel("Total tasks: 0");
        taskCountLabel.setForeground(Color.GRAY);
        taskCountLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        taskCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(taskCountLabel);

        add(topPanel, BorderLayout.NORTH);

        // Table

        String[] tableColumns = {"Name","Priority","Deadline","Status"};
        tableModel = new DefaultTableModel(tableColumns,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        taskTable = new JTable(tableModel);
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Name
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(50); // Priority
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Deadline
        taskTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Status completed ? Pending
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        taskTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int selectedRow = taskTable.getSelectedRow();
                currentTask = Task.taskList.get(selectedRow);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                viewButton.setEnabled(true);
                if(evt.getClickCount() == 2){
                    new TaskUI(currentTask,false);
                }
            }
        });

        setKeyBinding();
        pack();
        refreshTable();
        setVisible(true);
    }

    /**
     * Retrieves the singleton instance of the MainUI.
     * If the instance does not exist, it creates a new one.
     *
     * @return The singleton instance of MainUI.
     */
    public static MainUI getInstance(){
        if(instance == null)
            instance = new MainUI();
        return instance;
    }

    /**
     * Sets the application logo for the specified frame.
     * The logo is loaded from the resources folder.
     *
     * @param frame The JFrame to set the icon for.
     */
    public static void setLogo(JFrame frame){
        String logoPath = "/Icons/Logo.jpg";
        URL logoUrl = MainUI.class.getResource(logoPath);
        if (logoUrl != null) {
            frame.setIconImage(new ImageIcon(logoUrl).getImage());
        } else {
            System.err.println("Resource not found: " + logoPath);
        }
    }

    /**
     * Creates a new button with the specified text and adds it to the given panel.
     * Also registers the current class as the ActionListener for the button.
     *
     * @param text  The text to display on the button.
     * @param panel The JPanel to add the button to.
     * @return The created JButton.
     */
    public JButton createButton(String text, JPanel panel){
        JButton button = new JButton(text);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    /**
     * Refreshes the task table with the current list of tasks.
     * Clears the existing table data and repopulates it from {@link Model.Task#taskList}.
     * Also updates the task count label.
     */
    public void refreshTable() {
        // 1. Clear existing rows so we don't add duplicates
        tableModel.setRowCount(0);
        int completedTasks = 0;

        // 2. Loop through the list and add rows
        for (Task task : Task.taskList) {
            String deadlineStr = "--/--/----";
            if (task.getDeadLine() != null) {
                deadlineStr = task.getDeadLine().format(Task.DATE_FORMAT);
            }
            if(task.isComplete())
                completedTasks++;
            Object[] rowData = {
                    task.getName(),
                    task.getPriority(),
                    deadlineStr,
                    task.isComplete() ? "Done" : "Pending"
            };

            tableModel.addRow(rowData);
        }
        
        // Update task count label
        if (taskCountLabel != null) {
            taskCountLabel.setText("Total tasks: " + Task.taskList.size() + ", total tasks completed: " + completedTasks
            + ", total tasks in progress: " + (Task.taskList.size()-completedTasks));
        }
    }


    /**
     * Handles button click events.
     *
     * @param e The action event.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == newTaskButton){
            new TaskUI();
        }
        else if(source == viewButton){
            new TaskUI(currentTask,false);
        }
        else if(source == editButton){
            new TaskUI(currentTask,true);
        }
        else if(source == deleteButton){
            if (confirmDeletion()) {
                ManageTask.delete(currentTask);
            }
        }
    }

    /**
     * Sets up keyboard shortcuts for common actions.
     * Ctrl+N: New Task
     * Delete: Delete Task
     * Space: View Task
     * Ctrl+E: Edit Task
     */
    private void setKeyBinding(){
        KeyStroke ctrlN,delete,space, ctrlE;
        JRootPane rootPane = this.getRootPane();
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = rootPane.getActionMap();

        ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        delete = KeyStroke.getKeyStroke( KeyEvent.VK_DELETE, 0);
        space = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);

        inputMap.put(ctrlN, "New Task");
        inputMap.put(delete, "Delete");
        inputMap.put(space, "View");
        inputMap.put(ctrlE, "Edit");

        actionMap.put("New Task", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTaskButton.doClick();
            }
        });
        actionMap.put("Delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteButton.doClick();
            }
        });
        actionMap.put("View", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewButton.doClick();
            }
        });
        actionMap.put("Edit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editButton.doClick();
            }
        });
    }

    /**
     * Displays a confirmation dialog for task deletion.
     *
     * @return True if the user confirms deletion, false otherwise.
     */
    private boolean confirmDeletion() {
        String title = "Delete Task: " + currentTask.getName();
        String message = "Are you sure you want to delete this task?";
        
        int response = JOptionPane.showConfirmDialog(
            this, 
            message, 
            title, 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
        );

        return response == JOptionPane.YES_OPTION;
    }
}
