package ui.swing.panels;

import config.DBConnection;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SalaryHistoryPanel extends JPanel {

    public SalaryHistoryPanel(int companyId){

    setLayout(new BorderLayout());

    JTextField empField = new JTextField(10);
    JButton load = new JButton("Load");

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Month","Year","Base","Net","Bonus"},0
    );

    JTable table = new JTable(model);

    // 🔥 TOP PANEL (FIXED UI)
    JPanel top = new JPanel();
    top.add(new JLabel("Employee ID:"));
    top.add(empField);
    top.add(load);

    // 🔥 BUTTON ACTION
    load.addActionListener(e -> {

        model.setRowCount(0);

        if(empField.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter Employee ID!");
            return;
        }

        try{
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM salary_history WHERE emp_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,Integer.parseInt(empField.getText()));

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while(rs.next()){
                found = true;
                model.addRow(new Object[]{
                        rs.getInt("month"),
                        rs.getInt("year"),
                        rs.getDouble("base_salary"),
                        rs.getDouble("net_salary"),
                        rs.getDouble("bonus")
                });
            }

            if(!found){
                JOptionPane.showMessageDialog(this,"No records found!");
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid input!");
        }
    });

    add(top,BorderLayout.NORTH);
    add(new JScrollPane(table),BorderLayout.CENTER);
}
} 