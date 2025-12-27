package View;
/**
 * This is for main UI of app, it must have a new Task button ( + New ), edit button ( * edit)
 * And show a list of all created tasks, showing there: name, priority, deadline, status as table
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame implements ActionListener {

    JButton newBotton, editButton;


    public MainUI(){
        super("TODO LIST App");
        setSize(400,400);
        setLocationRelativeTo(null); // To always open it at center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setting up buttons
        newBotton = new JButton("New Botton");
        editButton = new JButton("Edit Botton");
        newBotton.addActionListener(this);
        editButton.addActionListener(this);
        add(newBotton);
        add(editButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();

        if(command.equals("New Botton")){
            TaskUI task = new TaskUI();
        }
        else if(command.equals("Edit Botton")){
            // TODO
        }
    }
}
