import Controller.TaskStorage;
import View.MainUI;

import javax.swing.*;

/**
 * The entry point of the application.
 * This class initializes the application by loading tasks, setting the look and feel,
 * and launching the main user interface.
 */
public final class Main {
    /**
     * The main method which serves as the entry point for the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try{
            TaskStorage.loadTasks();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Could not set the native look and feel.");
        }
        SwingUtilities.invokeLater(MainUI::new);
    }
}
