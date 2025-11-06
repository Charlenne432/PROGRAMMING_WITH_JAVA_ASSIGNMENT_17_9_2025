

package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.util.DB;

public class maintenancePanel extends JPanel implements ActionListener{
    private JTextField idTxt, vehicleIdTxt, maintenanceDateTxt, descriptionTxt, costTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public maintenancePanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        vehicleIdTxt = new JTextField();
        maintenanceDateTxt = new JTextField();
        descriptionTxt = new JTextField();
        costTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"maintenance_id", "vehicle_id", "date", "description", "cost"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("maintenance_id", idTxt, y); y += 30;
        addField("vehicle_id", vehicleIdTxt, y); y += 30;
        addField("date", maintenanceDateTxt, y); y += 30;
        addField("description", descriptionTxt, y); y += 30;
        addField("cost", costTxt, y); y += 30;

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
          
                if (vehicleIdTxt.getText().trim().isEmpty() || maintenanceDateTxt.getText().trim().isEmpty() || 
                    descriptionTxt.getText().trim().isEmpty() || costTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                    return;
                }
                
                String sql = "INSERT INTO maintenance(vehicle_id, date, description, cost) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(vehicleIdTxt.getText().trim()));
                ps.setString(2, maintenanceDateTxt.getText().trim());
                ps.setString(3, descriptionTxt.getText().trim());
                ps.setDouble(4, Double.parseDouble(costTxt.getText().trim()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Maintenance record added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
              
                if (idTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Maintenance ID is required for update!");
                    return;
                }
                
                String sql = "UPDATE maintenance SET vehicle_id=?, date=?, description=?, cost=? WHERE maintenance_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(vehicleIdTxt.getText().trim()));
                ps.setString(2, maintenanceDateTxt.getText().trim());
                ps.setString(3, descriptionTxt.getText().trim());
                ps.setDouble(4, Double.parseDouble(costTxt.getText().trim()));
                ps.setInt(5, Integer.parseInt(idTxt.getText().trim()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Maintenance record updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
             
                if (idTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Maintenance ID is required for deletion!");
                    return;
                }
                
                String sql = "DELETE FROM maintenance WHERE maintenance_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText().trim()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Maintenance record deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM maintenance";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("maintenance_id"),
                            rs.getInt("vehicle_id"),
                            rs.getString("date"),
                            rs.getString("description"),
                            rs.getDouble("cost")
                    });
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for Vehicle ID, Maintenance ID and Cost!");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idTxt.setText("");
        vehicleIdTxt.setText("");
        maintenanceDateTxt.setText("");
        descriptionTxt.setText("");
        costTxt.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maintenance Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new maintenancePanel());
        frame.setVisible(true);
    }
}