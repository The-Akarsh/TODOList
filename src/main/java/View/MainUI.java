package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is for main UI of app, it must have a new Task button ( + New ), edit button ( * edit)
 * And show a list of all created tasks, showing there: name, priority, deadline, status as table
 */
public class MainUI extends JFrame implements ActionListener {

    JButton newBotton, editButton;


    public MainUI(){
        super("TODO LIST App");
        setSize(600,700);
        setLocationRelativeTo(null); // To always open it at center
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        String tittleText = "<html><font size = 8>TODO LIST</font></html>";
        JLabel tittle = new JLabel(tittleText,SwingConstants.CENTER);
        tittle.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(tittle);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Setting up buttons
        newBotton = new JButton("New Botton");
        newBotton.addActionListener(this);
        buttonPanel.add(newBotton);
        editButton = new JButton("Edit Botton");
        editButton.addActionListener(this);
        buttonPanel.add(editButton);

        add(buttonPanel);
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
