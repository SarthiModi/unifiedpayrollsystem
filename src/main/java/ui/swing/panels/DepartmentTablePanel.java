package ui.swing.panels;

import config.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class DepartmentTablePanel extends JPanel {

    public DepartmentTablePanel(int companyId){

        setLayout(new BorderLayout());

        String[] cols = {"Dept ID","Name","Budget"};
        DefaultTableModel model = new DefaultTableModel(cols,0);
        JTable table = new JTable(model);

        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM department WHERE company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,companyId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                model.addRow(new Object[]{
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getDouble("budget")
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        add(new JScrollPane(table),BorderLayout.CENTER);
    }
}