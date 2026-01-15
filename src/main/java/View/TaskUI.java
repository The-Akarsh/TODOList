package View;

import Controller.HandleDateTime;
import Controller.ManageTask;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/** Create a "Create new Task" window for user to create a new task.
 *  Called as result of pressing "new" button in MainUI
 */
public class TaskUI extends JFrame implements ActionListener {

    JLabel createdLable,dateLable,timeLabel;
    JTextField nameField, createdField;
    JTextArea descriptionField;
    JButton save, cancel;
    JCheckBox enableDeadline,isCompleted;
    JComboBox<String> hourBox, minuteBox, amOrPmBox;
    JComboBox<Integer> priorityBox;
    JSpinner dateSpinner;

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
        add(panels.createDateTimePanel());

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
        nameField.setText(task.name());
        isCompleted.setSelected(task.isComplete());
        descriptionField.setText(task.description());
        priorityBox.setSelectedItem(task.priority());
        createdField.setText(task.getCreated_at());
        if(task.getDeadLine() != null){
            enableDeadline.setSelected(true);
            Date dateForSpinner = HandleDateTime.LocalTOLegacyDate(task.getDeadLine());
            dateSpinner.setValue(dateForSpinner);
            int hour = task.getDeadLine().getHour();
            if( hour > 11){
                amOrPmBox.setSelectedIndex(1);
                if (hour != 12)
                    hour -= 12;
            }
            else{
                amOrPmBox.setSelectedIndex(0);
                if (hour == 0)
                    hour = 12;
            }
            int minute = task.getDeadLine().getMinute();
            hourBox.setSelectedItem(String.format("%02d", hour));
            minuteBox.setSelectedItem(String.format("%02d", minute));

        }
        if(isEditable){
            this.setTitle("Edit Task - " + task.name());
            isCompleted.setEnabled(true);
        }
        if(!isEditable){
            this.setTitle("View Task - " + task.name());
            save.setVisible(false);
            cancel.setText("Close");
            nameField.setEditable(false);
            isCompleted.setEnabled(false);
            descriptionField.setEditable(false);
            priorityBox.setEditable(false);
            createdLable.setEnabled(true);
            createdField.setEnabled(true);
            if(task.getDeadLine() != null) {
                dateLable.setVisible(false);
                enableDeadline.setEnabled(false);
                hourBox.setEnabled(false);
                minuteBox.setEnabled(false);
                amOrPmBox.setEnabled(false);
                dateSpinner.setEnabled(false);
                save.setVisible(false);
                cancel.setText("Close");
            }else{
                enableDeadline.setVisible(false);
                hourBox.setVisible(false);
                minuteBox.setVisible(false);
                amOrPmBox.setVisible(false);
                dateSpinner.setVisible(false);
            }
        }
    }

    public String getName(){return nameField.getText();}
    public String getDescription(){return descriptionField.getText();}
    public int getPriority(){return Integer.parseInt(priorityBox.getSelectedItem().toString());}
    public String getDateTime(){
        if (!enableDeadline.isSelected())
            return null;
        String time =  hourBox.getSelectedItem() + ":" + minuteBox.getSelectedItem()
                + " " + amOrPmBox.getSelectedItem();
        Date rawDate = (Date) dateSpinner.getValue();
        return HandleDateTime.rawTimeTOSring(rawDate, time);
    }
    public boolean getIsCompleted(){return isCompleted.isSelected();}
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if(source == cancel){
            this.dispose();
        }
        else if(source ==  save){
            if(currentTask == null){
                ManageTask.create(this);
            }
            else{
                ManageTask.edit(this,currentTask);
            }
            this.dispose();
        }
    }


    /** This inner class of TaskUI is to create and customize the different panels for name,description & dateTime
     *  This is to fine tune the appearence of thses fields */
    class WindowPanels {
        String[] stringArrayRange(int min, int max){
            String[] stringArray = new String[max - min + 1];
            for(int i = min; i <= max; i++){
                stringArray[i - min] = String.format("%02d", i);
            }
            return stringArray;
        }
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
        JPanel createDateTimePanel(){
            JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

            enableDeadline = new JCheckBox("DeadLine:\n");
//            Create a model that has current date and make that spinner
            SpinnerDateModel dateModel = new SpinnerDateModel();
            dateSpinner = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner,"dd/MM/yyyy");
            dateSpinner.setEditor(dateEditor);
//            Setting Time values combo box
            String[] hour = stringArrayRange(1, 12);
            String[] minute = stringArrayRange(0, 59);
            String[] amOrPm = {"AM","PM"};
//            Creating combo box
            hourBox = new JComboBox<>(hour);
            minuteBox = new JComboBox<>(minute);
            amOrPmBox = new JComboBox<>(amOrPm);

//            Setting current time as default values
            java.util.Calendar now = java.util.Calendar.getInstance();
            int nowHour = now.get(java.util.Calendar.HOUR);
            hourBox.setPrototypeDisplayValue("000");
            if(nowHour == 0) nowHour = 12;
            hourBox.setSelectedItem(String.format("%02d", nowHour));
            int nowMinutes = now.get(java.util.Calendar.MINUTE);
            minuteBox.setPrototypeDisplayValue("000");
            minuteBox.setSelectedItem(String.format("%02d", nowMinutes));
            amOrPmBox.setSelectedIndex(now.get(java.util.Calendar.AM_PM));
            amOrPmBox.setPrototypeDisplayValue("000");

            dateSpinner.setEnabled(false);
            hourBox.setEnabled(false);
            minuteBox.setEnabled(false);
            amOrPmBox.setEnabled(false);

            ActionListener toggleAction = e -> {
                boolean isEnabled = enableDeadline.isSelected();
                dateSpinner.setEnabled(isEnabled);
                hourBox.setEnabled(isEnabled);
                minuteBox.setEnabled(isEnabled);
                amOrPmBox.setEnabled(isEnabled);
            };
            enableDeadline.addActionListener(toggleAction);
            enableDeadline.setSelected(false);

            datePanel.add(enableDeadline);
            datePanel.add(Box.createHorizontalStrut(10));
            dateLable = new JLabel("Date:");
            timeLabel = new JLabel("Time:");
            datePanel.add(timeLabel);
            datePanel.add(hourBox);
            datePanel.add(dateLable);
            datePanel.add(minuteBox);
            datePanel.add(amOrPmBox);
            datePanel.add(Box.createHorizontalStrut(10)); // Add a 10px gap for spacing
            datePanel.add(new JLabel("Date:"));
            datePanel.add(dateSpinner);

            return datePanel;
        }
    }
}
