package ui.swing.panels;

import javax.swing.*;
import service.PerformanceService;

public class AssignGoalsPanel extends JPanel {

    public AssignGoalsPanel(int companyId){

        JTextField emp = new JTextField(10);
        JTextField goals = new JTextField(10);

        JButton assign = new JButton("Achived");

        assign.addActionListener(e -> {
            new PerformanceService().assignGoals(
                    Integer.parseInt(emp.getText()),
                    Integer.parseInt(goals.getText()),
                    companyId
            );
            JOptionPane.showMessageDialog(this,"Goals Achived");
        });

        add(new JLabel("Emp ID")); add(emp);
        add(new JLabel("Goals")); add(goals);
        add(assign);
    }
}