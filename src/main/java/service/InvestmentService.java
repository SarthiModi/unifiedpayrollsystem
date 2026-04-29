package service;

import config.DBConnection;
import java.sql.*;

public class InvestmentService {
    public void addInvestment(int empId,int companyId,String stock,double amount){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO investment(emp_id,stock_name,amount) " +
                     "SELECT emp_id, ?, ? FROM employee WHERE emp_id=? AND company_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,stock);
        ps.setDouble(2,amount);
        ps.setInt(3,empId);
        ps.setInt(4,companyId);
        int rows = ps.executeUpdate();
        if(rows == 0){
            System.out.println(" Employee not found in your company!");
        } else {
            System.out.println("Investment recorded");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public void showInvestments(int empId){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM investment WHERE emp_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,empId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(
                        rs.getString("stock_name")+
                        " "+rs.getDouble("amount")
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}