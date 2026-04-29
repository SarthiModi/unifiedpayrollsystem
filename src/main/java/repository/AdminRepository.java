package repository;
import config.DBConnection;
import java.sql.*;

public class AdminRepository 
{
    public void registerAdmin(int companyId,String username,String password){
    try{ 
        Connection con = DBConnection.getConnection();
        String checkSql = "SELECT * FROM admin WHERE company_id=?";
        PreparedStatement checkPs = con.prepareStatement(checkSql);
        checkPs.setInt(1, companyId);
        ResultSet rs = checkPs.executeQuery();
        if(rs.next()){
            System.out.println("Admin already exists for this company!");
            return;
        }
        String sql="INSERT INTO admin(company_id,username,password) VALUES(?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1,companyId);
        ps.setString(2,username);
        ps.setString(3,password);
        ps.executeUpdate();
        System.out.println("Admin registered successfully");
    }catch(Exception e){
        e.printStackTrace();
    }
}
    public int login(String username, String password){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "SELECT company_id FROM admin WHERE username=? AND password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,username);
        ps.setString(2,password);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("company_id"); 
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return -1; 
}
}
