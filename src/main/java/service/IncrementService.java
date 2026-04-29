package service;
import config.DBConnection;
import java.sql.*;

public class IncrementService {
    public void applyIncrement(int empId,int companyId,double percent){
        try{
            if(percent < 0){
                System.out.println("Invalid increment!");
                return;
            }
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE employee SET increment_percent=? WHERE emp_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1,percent);
            ps.setInt(2,empId);
            ps.setInt(3,companyId);
            int rows = ps.executeUpdate();
            if(rows == 0){
                System.out.println("Employee not found in your company!");
            } else {
                System.out.println("Increment applied");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void departmentIncrement(int deptId,int companyId,double percent){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE employee SET increment_percent=? WHERE dept_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1,percent);
            ps.setInt(2,deptId);
            ps.setInt(3,companyId);
            ps.executeUpdate();
            System.out.println("Department increment applied");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}