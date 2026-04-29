package ui.swing.panels;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import analytics.DepartmentAnalytics;
public class DepartmentAnalyticsPanel extends JPanel {
    public DepartmentAnalyticsPanel(int companyId){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        String[] cols = {"Dept ID","Dept Name","Total Salary"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);
        DepartmentAnalytics analyzer = new DepartmentAnalytics();
        List<Object[]> data = analyzer.getDepartmentExpenditure(companyId);
        for(Object[] row : data){
            model.addRow(row);
        }
        add(new JScrollPane(table));
    }
}