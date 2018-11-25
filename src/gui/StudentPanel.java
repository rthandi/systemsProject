package gui;

import javax.swing.*;
import java.awt.*;
import classes.*;
import java.awt.event.ActionListener;

public class StudentPanel extends JPanel{
    public StudentPanel(User student){
        System.out.println("hi");
        setLayout(new BorderLayout());

        JPanel heading = new JPanel();
            heading.setLayout(new GridLayout(1, 2));
            heading.add(new JLabel(student.getFullName()));
            heading.add(new JLabel(student.getDegreeId()));

        add(heading, BorderLayout.NORTH);

        JPanel body = new JPanel();
            body.setLayout(new GridLayout(1,2));

            JPanel modules = new JPanel();
                modules.setLayout(new BoxLayout(modules, BoxLayout.Y_AXIS));
                //TODO get a list of the person's modules

            JPanel misc = new JPanel();
                misc.setLayout(new BoxLayout(misc, BoxLayout.Y_AXIS));
                //TODO get the level of study, and a way of registering?

        body.add(modules);
        body.add(misc);



    }

}
