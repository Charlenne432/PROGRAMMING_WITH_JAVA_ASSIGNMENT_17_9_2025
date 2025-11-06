package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class tripPanel extends JPanel implements ActionListener{
    private JTextField idTxt, order_numberTxt, dateTxt, statusTxt,total_amountTxt,payment_methodTxt,route_idTxt,driver_idTxt,vehicle_idTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public tripPanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        order_numberTxt = new JTextField();
        dateTxt = new JTextField();
        statusTxt = new JTextField();
        total_amountTxt = new JTextField();
        payment_methodTxt = new JTextField();
        route_idTxt = new JTextField();
        driver_idTxt = new JTextField();
        vehicle_idTxt = new JTextField();
        

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"Trip_id","order_number", "date", "status", "total_amount","payment_method","route_id","driver_id","vehicle_id"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("Trip_id", idTxt, y); y += 30;
        addField("order_number", order_numberTxt, y); y += 30;
        addField("date", dateTxt, y); y += 30;
        addField("status", statusTxt, y); y += 30;
        addField("total_amount", total_amountTxt, y); y += 30;
        addField("payment_method", payment_methodTxt, y); y += 30;
        addField("route_id", route_idTxt, y); y += 30;
        addField("driver_id", driver_idTxt, y); y += 30;
        addField("vehicle_id", vehicle_idTxt, y); y += 30;
        
        

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
                String sql = "INSERT INTO trip(order_number,date,status,total_amount,payment_method,route_id,driver_id,vehicle_id) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(order_numberTxt.getText()));
                ps.setInt(2, Integer.parseInt(statusTxt.getText()));
                ps.setInt(3, Integer.parseInt(total_amountTxt.getText()));
                ps.setString(4, payment_methodTxt.getText());
                ps.setString(5, route_idTxt.getText());
                ps.setString(6, driver_idTxt.getText());
                ps.setString(7, vehicle_idTxt.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Trip added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
                String sql = "UPDATE trip SET order_number=?, date=?, status=?, total_amount=?, payment_method=?, route_id=?, driver_id=?, vehicle_id=? WHERE trip_id=?"
;
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(order_numberTxt.getText()));
                ps.setInt(2, Integer.parseInt(dateTxt.getText()));
                ps.setInt(3, Integer.parseInt(statusTxt.getText()));
                ps.setString(4, total_amountTxt.getText());
                ps.setInt(5, Integer.parseInt(payment_methodTxt.getText()));
                ps.setInt(6, Integer.parseInt(route_idTxt.getText()));
                ps.setInt(7, Integer.parseInt(driver_idTxt.getText()));
                ps.setInt(8, Integer.parseInt(vehicle_idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Trip updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
                String sql = "DELETE FROM trip WHERE Trip_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Trip deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM trip";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("Trip_id"),
                        rs.getString("order_number"),
                        rs.getString("date"),
                        rs.getString("status"),
                        rs.getString("total_amount"),
                        rs.getString("payment_method"),
                        rs.getInt("route_id"),
                        rs.getInt("driver_id"),
                        rs.getInt("vehicle_id")
                    });
        
                }}
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        idTxt.setText("");
        order_numberTxt.setText("");
        dateTxt.setText("");
        statusTxt.setText("");
        total_amountTxt.setText("");
        payment_methodTxt.setText("");
        route_idTxt.setText("");
        driver_idTxt.setText("");
        vehicle_idTxt.setText("");
        
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Trip Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new tripPanel());
        frame.setVisible(true);
    }
}