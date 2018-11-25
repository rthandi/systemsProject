package gui;
import classes.*;
import javax.swing.*;

public class AdminPanel extends JPanel{
    public AdminPanel(AppFrame appFrame, User user){
        JTabbedPane tabPane = new JTabbedPane();

        JPanel deleteDeptPanel = new JPanel();
            deleteDeptPanel.add(new JLabel("Hello there"));

        JPanel deleteDegreePanel = new JPanel();
            deleteDegreePanel.add(new JLabel("uuggghh"));

        tabPane.addTab("Delete Department", deleteDeptPanel);

    }
}
