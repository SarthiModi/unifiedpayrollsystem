package ui.swing.panels;
import javax.swing.*;
import service.PayrollService;
public class BonusPanel extends JPanel {
    public BonusPanel(int companyId){
        JTextField emp = new JTextField(10);
        JTextField bonus = new JTextField(10);
        JButton give = new JButton("Give Bonus");
        give.addActionListener(e -> {
            new PayrollService().giveBonus(
                    Integer.parseInt(emp.getText()),
                    companyId,
                    Double.parseDouble(bonus.getText())
            );
            JOptionPane.showMessageDialog(this,"Bonus Added");
        });
        add(new JLabel("Emp ID")); add(emp);
        add(new JLabel("Bonus")); add(bonus);
        add(give);
    }
}