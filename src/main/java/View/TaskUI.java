package View;

import Controller.HandleDateTime;
import Controller.CreateTask;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/** Create a "Create new Task" window for user to create a new task.
 *  Called as result of pressing "new" button in MainUI
 */
public class TaskUI extends JFrame implements ActionListener {

    JTextField nameField, priorityField;
    JTextArea descriptionField;
    JButton Save, cancel;
    JCheckBox enableDeadline;
    JComboBox<String> hourBox, minuteBox, amOrPmBox;
    JSpinner dateSpinner;

    TaskUI(){
        super("Create Task");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.setLocationRelativeTo(null);

        WindowPanels panels = new WindowPanels();
        add(panels.createNamePanel());
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

    public String getName(){return nameField.getText();}
    public String getDescription(){return descriptionField.getText();}
    public int getPriority(){return Integer.parseInt(priorityField.getText());}
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
            String name = getName();
            String description = getDescription();
            int priority = getPriority();
            String dateTime = getDateTime();
            CreateTask.create(name, description, priority, dateTime);

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
            namePanel.add(new JLabel("Priority:"));
            priorityField = new JTextField(1);
            namePanel.add(nameField);
            namePanel.add(priorityField);
            return namePanel;
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
            if(nowHour == 0) nowHour = 12;
            hourBox.setSelectedItem(nowHour);
            int nowMinutes = now.get(java.util.Calendar.MINUTE);
            minuteBox.setSelectedItem(String.format("%02d", nowMinutes));
            amOrPmBox.setSelectedIndex(now.get(java.util.Calendar.AM_PM));

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
