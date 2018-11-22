import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class AppFrame extends JFrame {
    public AppFrame() throws HeadlessException {
        super("University of asd");

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setSize(screenSize.width/2, screenSize.height/2);
        setLocation(screenSize.width/4, screenSize.height/4);

        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());

        contentPane.add(new LoginPanel(this));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void toStudentPanel(User student){
        Container contentPane = getContentPane();
        contentPane.removeAll();

        contentPane.add(new StudentPanel(student));

        contentPane.revalidate();
        contentPane.repaint();

    }


    public static void main(String[] args) {
        new AppFrame();
    }
}