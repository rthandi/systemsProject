

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class StudentPanel extends JPanel{
    public StudentPanel(User student){
        setLayout(new BorderLayout());

        JPanel heading = new JPanel();
            heading.setLayout(new GridLayout(1, 2));
            heading.add(new JLabel(student.getFullName()));
            heading.add(new JLabel(student.getDegreeId()));

        add(heading);

    }

}
