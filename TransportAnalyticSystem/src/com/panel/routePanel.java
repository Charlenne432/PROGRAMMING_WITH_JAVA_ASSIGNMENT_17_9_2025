package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class routePanel extends JPanel implements ActionListener{
    private JTextField idTxt, locationTxt,distanceTxt, timeTxt, created_atTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public routePanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        locationTxt = new JTextField();
        distanceTxt = new JTextField();
        timeTxt = new JTextField();
        created_atTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Route_id", "location", "distance", "time", "created_at"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Route_id", idTxt, y); y += 30;
        addField("location", locationTxt, y); y += 30;
        addField("distance", distanceTxt, y); y += 30;
        addField("time", timeTxt, y); y += 30;
        addField("created_at",created_atTxt, y); y += 30;

        // Buttons
        addButtons();
    }

    private void addField(String lbl, JComponent txt, int y) {
        JLabel l = new JLabel(lbl);
        l.setBounds(20, y, 120, 25);
        txt.setBounds(150, y, 150, 25);
        add(l);
        add(txt);
    }

    private void addButtons() {
        addBtn.setBounds(320, 20, 100, 30);
        updateBtn.setBounds(320, 60, 100, 30);
        deleteBtn.setBounds(320, 100, 100, 30);
        loadBtn.setBounds(320, 140, 100, 30);

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
                String sql = "INSERT INTO route(location,distance,time,created_at) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, locationTxt.getText());
                ps.setString(2, distanceTxt.getText());
                ps.setString(3, timeTxt.getText());
                ps.setTimestamp(4, Timestamp.valueOf(created_atTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Route added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
                String sql = "UPDATE route SET location=?, distance=?, time=?, created_at=? WHERE Route_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, locationTxt.getText());
                ps.setString(2, distanceTxt.getText());
                ps.setString(3, timeTxt.getText());
                ps.setTimestamp(4, Timestamp.valueOf(created_atTxt.getText()));
                ps.setInt(5, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Route updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM route WHERE Route_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Route deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM route";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("Route_id"),
                            rs.getString("location"),
                            rs.getString("distance"),
                            rs.getString("time"),
                            rs.getTimestamp("created_at")

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
        locationTxt.setText("");
        distanceTxt.setText("");
        timeTxt.setText("");
        created_atTxt.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Route Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new routePanel());
        frame.setVisible(true);
    }
}