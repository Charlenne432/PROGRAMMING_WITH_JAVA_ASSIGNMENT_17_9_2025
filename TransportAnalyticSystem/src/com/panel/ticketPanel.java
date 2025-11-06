package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.util.DB;

public class ticketPanel extends JPanel implements ActionListener{
    private JTextField idTxt, statusTxt, francsTxt, PWTxt, createdAtTxt;
    private JButton addBtn, updateBtn, deleteBtn, loadBtn;
    private JTable table;
    private DefaultTableModel model;

    public ticketPanel(){
        setLayout(null);

        // Fields
        idTxt = new JTextField();
        statusTxt = new JTextField();
        francsTxt = new JTextField();
        PWTxt = new JTextField();
        createdAtTxt = new JTextField();

        // Buttons
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        loadBtn = new JButton("Load");

        // Table
        String[] labels = {"ticket_id", "status", "francs", "PW", " created_at"};
        model = new DefaultTableModel(labels, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 300, 750, 200);
        add(sp);

        // Layout fields
        int y = 20;
        addField("ticket_id", idTxt, y); y += 30;
        addField("status", statusTxt, y); y += 30;
        addField("francs", francsTxt, y); y += 30;
        addField("PW", PWTxt, y); y += 30;
        addField("created_at", createdAtTxt, y); y += 30;

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
               
                if (statusTxt.getText().trim().isEmpty() || francsTxt.getText().trim().isEmpty() || 
                    PWTxt.getText().trim().isEmpty() || createdAtTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                    return;
                }
                
                String sql = "INSERT INTO ticket(status, francs, PW,created_at) VALUES(?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, statusTxt.getText().trim());
                ps.setString(2, francsTxt.getText().trim());
                ps.setString(3, PWTxt.getText().trim());
                ps.setString(4, createdAtTxt.getText().trim());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Ticket added successfully!");
                clearFields();

            } else if (e.getSource() == updateBtn) {
                
                if (idTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ticket ID is required for update!");
                    return;
                }
                
                String sql = "UPDATE ticket SET status=?, francs=?, PW=?,created_at=? WHERE ticket_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, statusTxt.getText().trim());
                ps.setString(2, francsTxt.getText().trim());
                ps.setString(3, PWTxt.getText().trim());
                ps.setString(4, createdAtTxt.getText().trim());
                ps.setInt(5, Integer.parseInt(idTxt.getText().trim())); // Fixed index from 6 to 5
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Ticket updated successfully!");
                clearFields();

            } else if (e.getSource() == deleteBtn) {
                // Validate ticket_id
                if (idTxt.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ticket ID is required for deletion!");
                    return;
                }
                
                String sql = "DELETE FROM ticket WHERE ticket_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idTxt.getText().trim()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Ticket deleted successfully!");
                clearFields();

            } else if (e.getSource() == loadBtn) {
                model.setRowCount(0);
                String sql = "SELECT * FROM ticket";
                ResultSet rs = con.createStatement().executeQuery(sql);
                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("ticket_id"), 
                            rs.getString("status"), 
                            rs.getString("francs"), 
                            rs.getString("PW"),     
                            rs.getString("created_at"),
                    });
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric Ticket ID!");
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
        statusTxt.setText("");
        francsTxt.setText("");
        PWTxt.setText("");
        createdAtTxt.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ticket Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new ticketPanel());
        frame.setVisible(true);
    }
}