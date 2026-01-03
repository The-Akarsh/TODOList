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

    JTextField nameField;
    JTextArea descriptionField;
    JButton Save, cancel;
    JCheckBox enableDeadline;
    JComboBox<String> hourBox, minuteBox, amOrPmBox;
    JComboBox<Integer> priorityBox;
    JSpinner dateSpinner;

    TaskUI(){
        super("Create Task");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);

        WindowPanels panels = new WindowPanels();
        add(panels.createNamePanel());
        add(panels.createPriorityPanel());
        add(panels.createDescriptionPanel());
        add(panels.createDateTimePanel());

        JPanel buttonPanel = new JPanel();
        Save = new JButton("Save");
        Save.addActionListener(this);
        buttonPanel.add(Save);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        buttonPanel.add(cancel);

        add(buttonPanel);
        pack();
        setVisible(true);
    }
    TaskUI(Task task){
        this();
        nameField.setText(task.name());
        descriptionField.setText(task.description());
        priorityBox.setSelectedItem(task.priority());
        if(task.deadline() != null){
            enableDeadline.setSelected(true);
            Date dateForSpinner = HandleDateTime.LocalTOLegacyDate(task.deadline());
            dateSpinner.setValue(dateForSpinner);
            int hour = task.deadline().getHour();
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
            int minute = task.deadline().getMinute();
            hourBox.setSelectedItem(String.format("%02d", hour));
            minuteBox.setSelectedItem(String.format("%02d", minute));
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
    @Override
    public void actionPerformed(ActionEvent e) {
        String command  = e.getActionCommand();

        if(command.equals("Cancel")){
            this.dispose();
        }
        else if(command.equals("Save")){
            ManageTask.create(this);
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
            return priorityPanel;
        }
        JPanel createDescriptionPanel() {
            JPanel descriptionPanel = new JPanel(new BorderLayout());
            descriptionPanel.add(new JLabel("Description:"), BorderLayout.NORTH);

            descriptionField = new JTextArea(5, 20);
            JScrollPane scrollPane = new JScrollPane(descriptionField);
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

            ActionListener toggleAction = e -> {
                boolean isEnabled = enableDeadline.isSelected();
                dateSpinner.setEnabled(isEnabled);
                hourBox.setEnabled(isEnabled);
                minuteBox.setEnabled(isEnabled);
                amOrPmBox.setEnabled(isEnabled);
            };
            enableDeadline.addActionListener(toggleAction);
            enableDeadline.setSelected(true);

            datePanel.add(enableDeadline);
            datePanel.add(Box.createHorizontalStrut(10));
            datePanel.add(new JLabel("Time:"));
            datePanel.add(hourBox);
            datePanel.add(new JLabel(":"));
            datePanel.add(minuteBox);
            datePanel.add(amOrPmBox);
            datePanel.add(Box.createHorizontalStrut(10)); // Add a 10px gap for spacing
            datePanel.add(new JLabel("Date:"));
            datePanel.add(dateSpinner);

            return datePanel;
        }
    }
}
