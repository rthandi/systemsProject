package gui;

import javax.swing.*;
import java.awt.*;
import classes.*;
import java.awt.event.ActionListener;

public class StudentPanel extends JPanel{
    public StudentPanel(User student){

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        JPanel heading = new JPanel();
            heading.setLayout(new GridLayout(1,2));

            JButton name = new JButton(student.getFullName());
            JButton degree = new JButton(student.getDegreeId());

            //name.setBorder(BorderFactory.createLineBorder(Color.black));
            //degree.setBorder(BorderFactory.createLineBorder(Color.black));

            heading.add(name);
            heading.add(new JSeparator((SwingConstants.VERTICAL)));
            heading.add(degree);

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
