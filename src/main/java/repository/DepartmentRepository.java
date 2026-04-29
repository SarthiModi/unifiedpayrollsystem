package repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;

public class DepartmentRepository {
    public void addDepartment(int companyId, String name, double budget){
    try
        {
        Connection con = DBConnection.getConnection();
        name = name.trim().toLowerCase();
        String checkSql = "SELECT * FROM department WHERE company_id=? AND LOWER(dept_name)=?";
        PreparedStatement checkPs = con.prepareStatement(checkSql);
        checkPs.setInt(1, companyId);
        checkPs.setString(2, name);
        ResultSet rs=checkPs.executeQuery();
        if(rs.next()){
            System.out.println("Department already exists in this company!");
            return;
        }
        String sql = "INSERT INTO department(company_id,dept_name,budget) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, companyId);
        ps.setString(2, name); 
        ps.setDouble(3, budget);
        ps.executeUpdate();
        System.out.println("Department added successfully");
    }catch(Exception e){
        e.printStackTrace();
    }
}

    public void updateBudget(int deptId,int companyId,double newBudget){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE department SET budget=? WHERE dept_id=? AND company_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1,newBudget);
        ps.setInt(2,deptId);
        ps.setInt(3,companyId);
        int rows = ps.executeUpdate();
        if(rows==0){
            System.out.println("Invalid department!");
        }
        else{
            System.out.println("Budget updated");
        }
    }catch(Exception e){
        System.out.println("Error updating budget");
    }
   }
   public boolean departmentExists(int deptId, int companyId){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "SELECT 1 FROM department WHERE dept_id=? AND company_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, deptId);
        ps.setInt(2, companyId);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }catch(Exception e){
        return false;
    }
}
}
