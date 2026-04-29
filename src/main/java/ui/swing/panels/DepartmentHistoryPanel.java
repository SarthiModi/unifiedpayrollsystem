package ui.swing.panels;

import config.DBConnection;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class DepartmentHistoryPanel extends JPanel {
    public DepartmentHistoryPanel(int companyId){
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Dept ID","Dept Name","Month","Year","Expense"},0
        );
        JTable table = new JTable(model);
        try{
            Connection con = DBConnection.getConnection();
            String sql = 
"SELECT h.dept_id, d.dept_name, h.month, h.year, h.total_expense " +
"FROM dept_expense_history h " +
"JOIN department d ON h.dept_id = d.dept_id " +
"WHERE h.company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,companyId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               model.addRow(new Object[]{
        rs.getInt(1),
        rs.getString(2),
        rs.getInt(3),
        rs.getInt(4),
        rs.getDouble(5)
});
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JScrollPane(table));
    }
}