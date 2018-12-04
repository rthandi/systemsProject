package gui;

import javax.swing.*;

import classes.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteModulePanel extends JPanel {
    //protected String moduleToDelete = "COM1006";

    public DeleteModulePanel(User user){
        Connection con = null;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
            ArrayList<String> modIds = SystemsOperations.getModIds(user, con);


            //Panel for the search
            JPanel searchPanel = new JPanel();
            searchPanel.setLayout(new FlowLayout());
            searchPanel.setBorder(BorderFactory.createTitledBorder("Module Search:"));

            JTextField modSearch = new JTextField(10);
            searchPanel.add(modSearch);

            JButton searchButton = new JButton("Search");
            Connection finalCon = con;
            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getActionCommand().equals("Search")){
                        String chosenModule = modSearch.getText();
                        if (modIds.contains(chosenModule)) {
                            try {
                                SystemsOperations.deleteModule(user, chosenModule, finalCon);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            } finally {
                                try {if (finalCon != null) finalCon.close(); } catch (Exception es) {es.printStackTrace(System.err);}
                            }
                        }else{
                            System.out.println("Module Not found");
                        }
                    }
                }
            });
            searchPanel.add(searchButton);

            add(searchPanel);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {if (con != null) con.close(); } catch (Exception e) {e.printStackTrace(System.err);}
        }
    }
}

