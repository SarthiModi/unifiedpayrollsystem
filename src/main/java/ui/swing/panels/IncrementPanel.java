package ui.swing.panels;

import javax.swing.*;
import service.IncrementService;
public class IncrementPanel extends JPanel {
    public IncrementPanel(int companyId){
        JTextField emp = new JTextField(10);
        JTextField percent = new JTextField(10);
        JButton apply = new JButton("Apply");
        apply.addActionListener(e -> {
            new IncrementService().applyIncrement(
                    Integer.parseInt(emp.getText()),
                    companyId,
                    Double.parseDouble(percent.getText())
            );
            JOptionPane.showMessageDialog(this,"Increment Applied");
        });

        add(new JLabel("Emp ID")); add(emp);
        add(new JLabel("Percent")); add(percent);
        add(apply);
    }
}