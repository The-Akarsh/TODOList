package View;

import Controller.HandleDateTime;
import Controller.CreateTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TaskUI extends JFrame implements ActionListener {
    /** Create a "Create new Task" window for user to create a new task.
     *  Called as result of pressing "new" button in MainUI
     */

    JPanel panel;
    JTextField nameField, priorityField;
    JTextArea descriptionField;
    JButton Save, cancel;
    JComboBox<String> hourBox, minuteBox, amOrPmBox;
    JSpinner dateSpinner;
    String dateTime;

    // This is to hold other panels to create suitable layout
    JFrame mainFrame;

    TaskUI(){
        super("Create Task");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));
        WindowPanels panels = new WindowPanels();
        add(panels.createNamePanel());
        add(panels.createDescriptionPanel());
        add(panels.createDateTimePanel());

        Save = new JButton("Save");
        Save.addActionListener(this);
        add(Save);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        add(cancel);

        setVisible(true);
    }

    public String getName(){return nameField.getText();}
    public String getDescription(){return descriptionField.getText();}
    public String getPriority(){return priorityField.getText();}
    public String getDateTime(){
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
            CreateTask newTask = new CreateTask();
            newTask.create(this);
            this.dispose();
        }

    }


    class WindowPanels {
        /** This inner class of TaskUI is to create and customize the different panels for name,description & dateTime
         *  This is to fine tune the appearence of thses fields */
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

//            Create a model that has current date and make that spinner
            SpinnerDateModel dateModel = new SpinnerDateModel();
            dateSpinner = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner);
            dateSpinner.setEditor(dateEditor);
//            Setting Time values combo box
            String[] hour = stringArrayRange(1, 12);
            String[] minute = stringArrayRange(0, 59);
            String[] amOrPm = {"AM","PM"};
//            Creating combo box
            hourBox = new JComboBox<>(hour);
            minuteBox = new JComboBox<>(minute);
            amOrPmBox = new JComboBox<>(amOrPm);

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

