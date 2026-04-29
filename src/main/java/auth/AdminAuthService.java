package auth;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import service.AttendanceService;
public class AdminAuthService {
   public int login(int companyId, String username, String password){
    try{
        Connection con = DBConnection.getConnection();
        System.out.println("Connected DB: " + con.getCatalog());       
        System.out.println("INPUT -> " + companyId + " | " + username + " | " + password);
        String sql = "SELECT company_id FROM admin WHERE company_id=? AND username=? AND password=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, companyId);
        ps.setString(2, username.trim());
        ps.setString(3, password.trim());
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int cid = rs.getInt("company_id");
    new AttendanceService().fillMissingAttendance(cid);
    return cid;
        }

    }catch(Exception e){
        e.printStackTrace();
    }
    return -1;
}

}