package ui.swing.panels;
import javax.swing.*;
import repository.DepartmentRepository;
public class BudgetPanel extends JPanel {
    public BudgetPanel(int companyId){
        JTextField dept = new JTextField(10);
        JTextField budget = new JTextField(10);
        JButton update = new JButton("Update Budget");
        update.addActionListener(e -> {
            new DepartmentRepository().updateBudget(
                    Integer.parseInt(dept.getText()),
                    companyId,
                    Double.parseDouble(budget.getText())
            );
            JOptionPane.showMessageDialog(this,"Budget Updated");
        });
        add(new JLabel("Dept ID")); add(dept);
        add(new JLabel("Budget")); add(budget);
        add(update);
    }
}