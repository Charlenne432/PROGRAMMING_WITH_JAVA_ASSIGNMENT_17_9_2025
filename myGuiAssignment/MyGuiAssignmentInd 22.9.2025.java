package com.myGuiAssignment;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MyGuiAssignmentInd implements ActionListener {

    // Frame and components
    JFrame frame;
    JLabel amountRequested = new JLabel("Amount requested:");
    JLabel Duration = new JLabel("Duration (Years):");
    JLabel totalToReturn = new JLabel("Total to Return:");

    JTextField Artxt = new JTextField();
    JTextField Dur = new JTextField();
    JTextField Ttl = new JTextField();

    JButton Calculate = new JButton("Calculate");
    JButton Clear = new JButton("Clear");

    public MyGuiAssignmentInd() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        addActionEvent();
    }

    // Create window
    private void createWindow() {
        frame = new JFrame();
        frame.setTitle("===== Interest Rate Calculator =====");
        frame.setBounds(100, 100, 800, 600);
        frame.getContentPane().setBackground(Color.lightGray);
        frame.getContentPane().setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Add components to frame
    private void addComponentsToFrame() {
        frame.add(amountRequested);
        frame.add(Duration);
        frame.add(totalToReturn);

        frame.add(Artxt);
        frame.add(Dur);
        frame.add(Ttl);

        frame.add(Calculate);
        frame.add(Clear);

        // Make total field read-only
        Ttl.setEditable(false);
    }

    // Set bounds of components
    private void setLocationAndSize() {
        amountRequested.setBounds(30, 30, 150, 30);
        Artxt.setBounds(180, 30, 150, 30);

        Duration.setBounds(30, 70, 150, 30);
        Dur.setBounds(180, 70, 150, 30);

        totalToReturn.setBounds(30, 110, 150, 30);
        Ttl.setBounds(180, 110, 150, 30);

        Calculate.setBounds(50, 170, 120, 40);
        Clear.setBounds(200, 170, 120, 40);
    }

    // Add action listeners
    private void addActionEvent() {
        Calculate.addActionListener(this);
        Clear.addActionListener(this);
    }

    // Handle button actions
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == Calculate) {
            try {
                int amount = Integer.parseInt(Artxt.getText());
                int dura = Integer.parseInt(Dur.getText());
                double interestRate = 0.006; // 0.6% per year

                // Use duration in calculation: Simple Interest
                double total = amount * (1 + (interestRate * dura));

                // Show result with 2 decimal places
                Ttl.setText(String.format("%.2f", total));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter valid numbers!", 
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == Clear) {
            Artxt.setText("");
            Dur.setText("");
            Ttl.setText("");
        }
    }

    // Main method
    public static void main(String[] args) {
        new MyGuiAssignmentInd();
    }
}


