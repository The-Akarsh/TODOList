package View;

import Controller.HandleDateTime;
import Controller.ManageTask;
import Model.Task;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * Main UI of app. Use <code>MainUI.getINstance()</code> to create instance. MainUI has a new Task button ( + New )
 * and show a list of all created tasks, showing there: name, priority, deadline, status as table
 */
public class MainUI extends JFrame implements ActionListener {

    private static MainUI instance;
    private static Task currentTask;
    JButton newTaskButton, viewButton, editButton, deleteButton;
    JTable taskTable;
    DefaultTableModel tableModel;


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


        pack();
        refreshTable();
        setVisible(true);
    }
    /** This enforces singleton instance of the MainUi to prevent unwanted behavior.
     * Creates new instance if no instances are running, else return the running instance*/
    public static MainUI getInstance(){
        if(instance == null)
            instance = new MainUI();
        return instance;
    }

/** Sets logo for the window. Accept JFrame as input. location for is stored in <code>String logoPath</code> */
    public static void setLogo(JFrame frame){
        String logoPath = "/Icons/Logo.jpg";
        URL logoUrl = MainUI.class.getResource(logoPath);
        if (logoUrl != null) {
            frame.setIconImage(new ImageIcon(logoUrl).getImage());
        } else {
            System.err.println("Resource not found: " + logoPath);
        }
    }

/** Accepts String and JPanel as input. Creates new button with specified string as text, adds ActionListener
  * then adds the button to the specified panel and return the button*/
    public JButton createButton(String text, JPanel panel){
        JButton button = new JButton(text);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }
    /** Refresh contents of the task table by using contents from tasklist*/
    public void refreshTable() {
        // 1. Clear existing rows so we don't add duplicates
        tableModel.setRowCount(0);

        // 2. Loop through the list and add rows
        for (Task task : Task.taskList) {
            String deadlineStr = "--/--/----";
            if (task.getDeadLine() != null) {
                deadlineStr = task.getDeadLine().format(HandleDateTime.dateTimeFormat);
            }

            Object[] rowData = {
                    task.name(),
                    task.priority(),
                    deadlineStr,
                    task.isComplete() ? "Done" : "Pending"
            };

            tableModel.addRow(rowData);
//            Resets lastID to size of taskList at every refresh
            Task.setLastID(Task.taskList.size());
        }
    }



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
            ManageTask.delete(currentTask);
        }
    }
}
