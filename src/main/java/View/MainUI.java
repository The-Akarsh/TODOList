package View;

import Controller.HandleDateTime;
import Model.Task;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Main UI of app. Use <code>MainUI.getINstance()</code> to create instance. MainUI has a new Task button ( + New )
 * and show a list of all created tasks, showing there: name, priority, deadline, status as table
 */
public class MainUI extends JFrame implements ActionListener {

    private static MainUI instance;
    JButton newTaskButton;
    JTable taskTable;
    DefaultTableModel tableModel;


    public MainUI(){
        super("TODO LIST App");
        instance = this;
        setSize(600,700);
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Setting up buttons
        newTaskButton = new JButton("New task(+)");
        newTaskButton.addActionListener(this);
        buttonPanel.add(newTaskButton);
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
                if(evt.getClickCount() == 2){
                    int selectedRow = taskTable.getSelectedRow();
                    Task selectedTask = Task.taskList.get(selectedRow);
                    new TaskUI(selectedTask);
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

    /** Refresh contents of the task table by using contents from tasklist*/
    public void refreshTable() {
        // 1. Clear existing rows so we don't add duplicates
        tableModel.setRowCount(0);

        // 2. Loop through the list and add rows
        for (Task task : Task.taskList) {
            String deadlineStr = "--/--/----";
            if (task.deadline() != null) {
                deadlineStr = task.deadline().format(HandleDateTime.dateTimeFormat);
            }

            Object[] rowData = {
                    task.name(),
                    task.priority(),
                    deadlineStr,
                    task.isComplete() ? "Done" : "Pending"
            };

            tableModel.addRow(rowData);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();

        if(source == newTaskButton){
            TaskUI task = new TaskUI();
        }
    }
}
