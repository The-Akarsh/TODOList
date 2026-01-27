package View;

import Controller.ManageTask;
import Model.Task;
import com.github.lgooddatepicker.components.DateTimePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

/** Create a "Create new Task" window for user to create a new task.
 *  Called as result of pressing "new" button in MainUI
 */
public class TaskUI extends JFrame implements ActionListener {

    JLabel createdLable;
    JTextField nameField, createdField;
    JTextArea descriptionField;
    JButton save, cancel;
    JCheckBox enableDeadline,isCompleted;
    DateTimePicker dateTimePicker;
    JComboBox<Integer> priorityBox;

    private Task currentTask = null;

    TaskUI(){
        super("Create Task");
        MainUI.setLogo(this);
        setResizable(false);

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);

        WindowPanels panels = new WindowPanels();
        add(panels.createNamePanel());
        add(panels.createPriorityPanel());
        add(panels.createDescriptionPanel());
        add(panels.createDateTimePickerPanel());

        JPanel buttonPanel = new JPanel();
        save = new JButton("Save");
        save.addActionListener(this);
        buttonPanel.add(save);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);

        add(buttonPanel);
        pack();
        setVisible(true);
    }

    TaskUI(Task task,boolean isEditable){
        this();
        MainUI.setLogo(this);

        this.currentTask = task;
        createdLable.setVisible(true);
        createdField.setVisible(true);
        nameField.setText(task.getName());
        isCompleted.setSelected(task.isComplete());
        descriptionField.setText(task.getDescription());
        priorityBox.setSelectedItem(task.getPriority());
        createdField.setText(task.getCreated_at());
        if(task.getDeadLine() != null) {
            enableDeadline.setSelected(true);
            dateTimePicker.setDateTimePermissive(task.getDeadLine());
        }
        if(isEditable){
            this.setTitle("Edit Task - " + task.getName());
            isCompleted.setEnabled(true);
        }
        if(!isEditable){
            this.setTitle("View Task - " + task.getName());
            save.setVisible(false);
            cancel.setText("Close");
            nameField.setEditable(false);
            isCompleted.setEnabled(false);
            descriptionField.setEditable(false);
            priorityBox.setEditable(false);
            createdLable.setEnabled(true);
            createdField.setEnabled(true);
            enableDeadline.setEnabled(false);
            dateTimePicker.setEnabled(false);
            if(task.getDeadLine() == null) {
                enableDeadline.setVisible(false);
                dateTimePicker.setVisible(false);
            }
        }
    }

    public LocalDateTime getDateTime(){
        if (!enableDeadline.isSelected())
            return null;
        return dateTimePicker.getDateTimePermissive();
    }
    private boolean getStatus(){
        return isCompleted.isSelected();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == cancel){
            this.dispose();
        }
        else if(source ==  save){
            String name = nameField.getText();
            String description = descriptionField.getText();
            Object selected = priorityBox.getSelectedItem();
            int priority = (selected != null) ? (Integer) selected : 1;
            if(currentTask == null){
                ManageTask.create(name,description,priority, getDateTime());
            }
            else{
                ManageTask.edit(currentTask, name,description,priority,getDateTime(), getStatus());
            }
            this.dispose();
        }
    }


    /**
     * This inner class is responsible for creating and organizing the various UI panels
     * used within the {@link TaskUI} frame. It encapsulates the panel creation logic
     * to maintain a clean and organized main class structure.
     */
    class WindowPanels {
        /**
         * Creates and configures the panel for entering the task name and marking it as complete.
         * This panel includes a text field for the name and a checkbox for completion status.
         *
         * @return A {@code JPanel} containing the name input components.
         */
        JPanel createNamePanel(){
            JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            namePanel.add(new JLabel("Name:"));
            nameField = new JTextField(20);
            namePanel.add(nameField);
            namePanel.add(new JLabel("Mark as completed:"));
            isCompleted = new JCheckBox();
            isCompleted.setEnabled(false);
            namePanel.add(isCompleted);
            return namePanel;
        }

        /**
         * Creates and configures the panel for setting the task's priority and displaying its creation date.
         * This panel includes a combo box for priority selection and non-editable fields for the creation timestamp.
         *
         * @return A {@code JPanel} containing the priority and creation date components.
         */
        JPanel createPriorityPanel(){
            JPanel priorityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            priorityPanel.add(new JLabel("Priority:"));
            Integer[] priorities = {1,2,3,4,5};
            priorityBox = new JComboBox<>(priorities);
            priorityBox.setPrototypeDisplayValue(10); // Number of digits inside this is the size of the box. Data type of value = data type of box
            priorityBox.setSelectedItem(1);
            priorityPanel.add(priorityBox);

//            Placeholder for showing created date
            createdLable = new JLabel("Created at:");
            createdLable.setVisible(false);
            priorityPanel.add(createdLable);
            createdField = new JTextField(20);
            createdField.setEditable(false);
            createdField.setVisible(false);
            createdField.setEnabled(false);
            priorityPanel.add(createdField);
            return priorityPanel;
        }

        /**
         * Creates and configures the panel for entering the task description.
         * This panel includes a {@code JTextArea} with line wrapping enabled, enclosed in a {@code JScrollPane}.
         *
         * @return A {@code JPanel} containing the description input component.
         */
        JPanel createDescriptionPanel() {
            JPanel descriptionPanel = new JPanel(new BorderLayout());
            descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);

            descriptionField = new JTextArea(5, 20);
            descriptionField.setLineWrap(true); // Enable line wrapping
            descriptionField.setWrapStyleWord(true); // Wrap at word boundaries
            JScrollPane scrollPane = new JScrollPane(descriptionField);
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scrollbar
            descriptionPanel.add(scrollPane, BorderLayout.CENTER);

            return descriptionPanel;
        }

        /**
         * Creates and configures the panel for setting the task's deadline using a {@code DateTimePicker}.
         * This panel includes a checkbox to enable or disable the deadline and the {@code DateTimePicker} component itself.
         *
         * @return A {@code JPanel} containing the deadline selection components.
         */
        JPanel createDateTimePickerPanel(){
            JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            enableDeadline = new JCheckBox("DeadLine:\n");
           dateTimePicker = new DateTimePicker();
            dateTimePicker.setEnabled(false);

            ActionListener toggleAction = _ -> {
                boolean isEnabled = enableDeadline.isSelected();
                dateTimePicker.setEnabled(isEnabled);
                if (isEnabled && dateTimePicker.getDateTimePermissive() == null) {
                    dateTimePicker.setDateTimePermissive(LocalDateTime.now());
                }
            };
            enableDeadline.addActionListener(toggleAction);
            enableDeadline.setSelected(false);

            datePanel.add(enableDeadline);
            datePanel.add(dateTimePicker);

            return datePanel;
        }
    }
}
