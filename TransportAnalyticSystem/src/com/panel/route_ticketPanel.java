package com.panel;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.util.DB;

public class route_ticketPanel extends JPanel implements ActionListener{
	private JTextField  routeIdTxt, ticketIdTxt;
	private JButton addBtn, updateBtn, deleteBtn, loadBtn;
	private JTable table;
	private DefaultTableModel model;

	public route_ticketPanel(){
		setLayout(null);
		
		routeIdTxt = new JTextField();
		ticketIdTxt = new JTextField();
		
		addBtn = new JButton("Add");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		loadBtn = new JButton("Load");

		String[] labels = {"Ticket_id", "Route_id"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 300, 750, 200);
		add(sp);

		// Layout fields
		int y = 20;
		addField("Route_id", routeIdTxt, y); y += 30;
		addField("Ticket_id", ticketIdTxt, y); y += 30;

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
				String sql = "INSERT INTO route_ticket(Route_id, Ticket_id) VALUES(?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(routeIdTxt.getText()));
				ps.setInt(2, Integer.parseInt(ticketIdTxt.getText()));
				JOptionPane.showMessageDialog(this, "Route-Ticket added successfully!");
				clearFields();

			} else if (e.getSource() == updateBtn) {
				String sql = "UPDATE route_ticket SET Route_id=?, Ticket_id=? WHERE Ticket_id=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(routeIdTxt.getText()));
				ps.setInt(2, Integer.parseInt(ticketIdTxt.getText()));
				JOptionPane.showMessageDialog(this, "Route-Ticket updated successfully!");
				clearFields();

			} else if (e.getSource() == deleteBtn) {
				String sql = "DELETE FROM route_ticket WHERE RouteTicketID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Route-Ticket deleted successfully!");
				clearFields();

			} else if (e.getSource() == loadBtn) {
				model.setRowCount(0);
				String sql = "SELECT * FROM route_ticket";
				ResultSet rs = con.createStatement().executeQuery(sql);
				while (rs.next()) {
					model.addRow(new Object[]{
							rs.getInt("Route_id"),
							rs.getInt("Ticket_id"),
					});
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void clearFields() {
		routeIdTxt.setText("");
		ticketIdTxt.setText("");
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Route-Ticket Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.add(new route_ticketPanel());
		frame.setVisible(true);
	}
}