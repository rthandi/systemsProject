package gui;

import classes.*;
import javax.swing.*;
import java.awt.*;

public class ModulePanel extends JPanel{
    public ModulePanel(StudentModsGrades module){
        setBorder(BorderFactory.createTitledBorder(module.getModuleName()));
        add(new JLabel(module.getModuleID()));
        add(new JLabel("Marks:" + module.getStudentsMarks()));
        add(new JLabel("Credits:" + module.getModuleCredits()));
    }
}
