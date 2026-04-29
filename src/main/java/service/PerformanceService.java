package service;
import config.DBConnection;
import java.sql.*;

public class PerformanceService {

    public boolean employeeExists(int empId,int companyId){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE emp_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,empId);
            ps.setInt(2,companyId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }

    public void assignGoals(int empId,int goals,int companyId){
        try{
            if(!employeeExists(empId,companyId)){
                System.out.println("Employee not found in your company!");
                return;
            }
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE employee SET goals=? WHERE emp_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,goals);
            ps.setInt(2,empId);
            ps.setInt(3,companyId);
            ps.executeUpdate();
            System.out.println("Goals updated successfully");
        }catch(Exception e){
            System.out.println("Error updating goals");
        }
    }
}