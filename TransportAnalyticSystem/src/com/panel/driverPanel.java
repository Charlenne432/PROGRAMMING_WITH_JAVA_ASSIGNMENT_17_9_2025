package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.util.DB;

public class driverPanel extends JPanel implements ActionListener{
    private JTextField idTxt, nameTxt, licenseTxt, experienceTxt, created_atTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public driverPanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        nameTxt = new JTextField();
        licenseTxt = new JTextField();
        experienceTxt = new JTextField();
        created_atTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Driver_id", "names", "driving_licence", "experience", "created_at"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Driver_id", idTxt, y); y += 30;
        addField("names", nameTxt, y); y += 30;
        addField("driving_licence", licenseTxt, y); y += 30;
        addField("experience", experienceTxt, y); y += 30;
        addField("created_at", created_atTxt, y); y += 30;

        // Buttons
        addButtons();
    }

    private void addField(String lbl, JComponent txt, int y) {
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 80, 25);
        txt.setBounds(120, y, 150, 25);
        add(l);
        add(txt);
    }

    private void addButtons() {
        addBtn.setBounds(300, 20, 100, 30);
        updateBtn.setBounds(300, 60, 100, 30);
        deleteBtn.setBounds(300, 100, 100, 30);
        loadBtn.setBounds(300, 140, 100, 30);

        add(addBtn);
        add(updateBtn);
        add(deleteBtn);
        add(loadBtn);

        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        loadBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try (Connection con = DB.getConnection()) {

            if (e.getSource() == addBtn) {
                String sql = "INSERT INTO driver(names, driving_licence, experience, created_at) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameTxt.getText());
                ps.setString(2, licenseTxt.getText());
                ps.setString(3, experienceTxt.getText());
                ps.setString(4, created_atTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Driver added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
                String sql = "UPDATE driver SET names=?, driving_licence=?, experience=?, created_at=? WHERE driver_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameTxt.getText());
                ps.setString(2, licenseTxt.getText());
                ps.setString(3, experienceTxt.getText());
                ps.setString(4,created_atTxt.getText());
                ps.setInt(5, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Driver updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM driver WHERE Driver_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Driver deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM driver";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("Driver_id"),
                            rs.getString("names"),
                            rs.getString("driving_licence"),
                            rs.getString("experience"),
                            rs.getString("created_at")
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idTxt.setText("");
        nameTxt.setText("");
        licenseTxt.setText("");
        experienceTxt.setText("");
        created_atTxt.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Driver Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new driverPanel());
        frame.setVisible(true);
    }
}