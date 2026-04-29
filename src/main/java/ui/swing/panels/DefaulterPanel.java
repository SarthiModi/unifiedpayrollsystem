package ui.swing.panels;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import analytics.DefaulterAnalyzer;
public class DefaulterPanel extends JPanel {

    public DefaulterPanel(int companyId){

        setLayout(new BorderLayout());

        String[] cols = {"Name","Goals","Leaves"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);
        DefaulterAnalyzer analyzer = new DefaulterAnalyzer();
        List<Object[]> data = analyzer.getDefaulters(companyId);

        for(Object[] row : data){
               model.addRow(row);
        }
        add(new JScrollPane(table));
    }
}