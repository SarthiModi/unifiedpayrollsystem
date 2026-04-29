package ui.swing.panels;
import javax.swing.*;
import service.MonthEndService;
public class MonthEndPanel extends JPanel {
    public MonthEndPanel(int companyId){
        JButton run = new JButton("Process Month End");
        run.addActionListener(e -> {
            new MonthEndService().processMonthEnd(companyId);
            JOptionPane.showMessageDialog(this,"Month End Done");
        });
        add(run);
    }
}