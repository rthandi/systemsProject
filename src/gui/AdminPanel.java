package gui;
import classes.*;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdminPanel extends JTabbedPane{
    public AdminPanel(AppFrame appFrame, User user) throws SQLException {
        setBorder(BorderFactory.createTitledBorder("Welcome Admin"));
        addTab("Add Department", new AddDepartmentPanel(user));
        addTab("Delete Department", new DeleteDepartmentPanel(user));
        addTab("Add Degree", new AddDegreePanel(user));
        addTab("Delete Degree", new DeleteDegreePanel(user));
        addTab("Add new Module", new AddNewModule(user));
        addTab("Add module to Degree", new ModuleDegreePanel(user));
        addTab("Delete Module", new DeleteModulePanel(user));
        addTab("Delete Module from Degree", new RemoveModuleFromDegree(user));
        addTab("Add new user", new AddUserPanel(user));
        //TODO creating a user (should be callable on a list or something so admin doesn't have to manually make 100+ students
    }
}
