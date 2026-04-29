package ui.swing.panels;
import config.DBConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class EmployeeTablePanel extends JPanel {
    public EmployeeTablePanel(int companyId){
        setLayout(new BorderLayout());
        String[] cols = {"ID","Name","Email","Dept","Salary","Goals","Leaves"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        JTextField search = new JTextField(15);
        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            public void insertUpdate(javax.swing.event.DocumentEvent e){ filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e){ filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e){ filter(); }
            private void filter(){
                String text = search.getText();
                sorter.setRowFilter(text.length()==0 ? null : RowFilter.regexFilter("(?i)"+text));
            }
        });
        try{
            Connection con = DBConnection.getConnection();
            String sql =
    "SELECT e.emp_id, e.name, e.email, e.dept_id, e.salary, e.goals, " +
    "COALESCE(SUM(CASE WHEN a.status='ABSENT' THEN 1 ELSE 0 END),0) AS leaves " +
    "FROM employee e " +
    "LEFT JOIN attendance a " +
    "ON e.emp_id = a.emp_id AND e.company_id = a.company_id " +
    "AND MONTH(a.date) = MONTH(CURDATE()) " +   // 🔥 FIX
    "AND YEAR(a.date) = YEAR(CURDATE()) " +     // 🔥 FIX
    "WHERE e.company_id=? " +
    "GROUP BY e.emp_id";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,companyId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("dept_id"),
                        rs.getDouble("salary"),
                        rs.getInt("goals"),
                        rs.getInt("leaves")
                });
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        top.add(search);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}