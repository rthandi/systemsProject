package gui;

import classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeleteDepartmentPanel extends JPanel{
    public DeleteDepartmentPanel(User user){
        ArrayList<Department> depts = null;
        try {
            depts = SystemsOperations.getDept();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JComboBox deptList = new JComboBox();

        for (Department d : depts) {
            deptList.addItem(d);
        }
        JButton deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("delete dept");
        //Deletes the selected Department
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("delete dept")){
                    Department chosen = (Department) deptList.getSelectedItem();
                    Connection con = null;
                    try{
                        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team029", "team029", "5afef30f");
                        SystemsOperations.deleteDepartment(user, chosen.getDepartmentCode(), con);
                        deptList.remove(deptList.getSelectedIndex()); //ewwwwwww
                        con.close();
                    }catch(SQLException e1) {
                        e1.printStackTrace();
                    }finally{
                        if(con != null){
                            try{
                                con.close();
                            }catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        add(deptList);
        add(deleteButton);
    }
}
