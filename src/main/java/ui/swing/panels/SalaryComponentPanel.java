package ui.swing.panels;

import javax.swing.*;
import repository.EmployeeRepository;

public class SalaryComponentPanel extends JPanel {

    public SalaryComponentPanel(int companyId){

        JTextField emp = new JTextField(10);
        JTextField hra = new JTextField(10);
        JTextField da = new JTextField(10);
        JTextField ta = new JTextField(10);

        JButton update = new JButton("Update");

        update.addActionListener(e -> {
            new EmployeeRepository().updateSalaryComponents(
                    Integer.parseInt(emp.getText()),
                    companyId,
                    Double.parseDouble(hra.getText()),
                    Double.parseDouble(da.getText()),
                    Double.parseDouble(ta.getText())
            );
            JOptionPane.showMessageDialog(this,"Updated");
        });

        add(new JLabel("Emp ID")); add(emp);
        add(new JLabel("HRA")); add(hra);
        add(new JLabel("DA")); add(da);
        add(new JLabel("TA")); add(ta);
        add(update);
    }
}