package ui.swing.panels;
import javax.swing.*;
import service.InvestmentService;
public class InvestmentPanel extends JPanel {
    public InvestmentPanel(int companyId){
        JTextField emp = new JTextField(10);
        JTextField stock = new JTextField(10);
        JTextField amount = new JTextField(10);
        JButton add = new JButton("Add Investment");
        add.addActionListener(e -> {
            new InvestmentService().addInvestment(
                    Integer.parseInt(emp.getText()),
                    companyId,
                    stock.getText(),
                    Double.parseDouble(amount.getText())
            );
            JOptionPane.showMessageDialog(this,"Added");
        });
        add(new JLabel("Emp ID")); add(emp);
        add(new JLabel("Stock")); add(stock);
        add(new JLabel("Amount")); add(amount);
        add(add);
    }
}