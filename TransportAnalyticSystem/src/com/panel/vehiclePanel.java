package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class vehiclePanel extends JPanel implements ActionListener{
    private JTextField idTxt, nameTxt, identifierTxt, statusTxt, locationTxt,contactTxt,assigned_sinceTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public vehiclePanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        nameTxt = new JTextField();
        identifierTxt = new JTextField();
        statusTxt = new JTextField();
        locationTxt = new JTextField();
        contactTxt = new JTextField();
        assigned_sinceTxt = new JTextField();
       

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Vehicle_id", "name", "identifier", "status", "location","contact","assigned_since"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Vehicle_id", idTxt, y); y += 30;
        addField("name", nameTxt, y); y += 30;
        addField("identifier", identifierTxt, y); y += 30;
        addField("status", statusTxt, y); y += 30;
        addField("location", locationTxt, y); y += 30;
        addField("contact", contactTxt, y); y += 30;
        addField("assigned_since", assigned_sinceTxt, y); y += 30;
        

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
                String sql = "INSERT INTO vehicle(name,identifier, status,location,contact, assigned_since) VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameTxt.getText());
                ps.setString(2, identifierTxt.getText());
                ps.setInt(3, Integer.parseInt(statusTxt.getText()));
                ps.setString(4, locationTxt.getText());
                ps.setString(5, contactTxt.getText());
                ps.setString(6, assigned_sinceTxt.getText());
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Vehicle added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
                String sql = "UPDATE vehicle SET name=?, identifier=?, status=?, location=?,contact=?,assigned_since=? WHERE Vehicle_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nameTxt.getText());
                ps.setString(2, identifierTxt.getText());
                ps.setString(3, statusTxt.getText());
                ps.setString(4, locationTxt.getText());
                ps.setInt(5, Integer.parseInt(contactTxt.getText()));
                ps.setInt(6, Integer.parseInt(assigned_sinceTxt.getText()));
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Vehicle updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM vehicle WHERE Vehicle_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Vehicle deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM vehicle";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("Vehicle_id"),
                            rs.getString("name"),
                            rs.getString("identifier"),
                            rs.getString("status"),
                            rs.getString("location"),
                            rs.getString("contact"),
                            rs.getString("assigned_since")
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
        identifierTxt.setText("");
        statusTxt.setText("");
        locationTxt.setText("");
        contactTxt.setText("");
        assigned_sinceTxt.setText("");
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vehicle Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new vehiclePanel());
        frame.setVisible(true);
    }
}