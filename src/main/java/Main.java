import View.MainUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex){
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            System.out.println("Could not set the native look and feel.");
        }
        SwingUtilities.invokeLater(MainUI::new);
//         @TODO
    }
}
