package auth;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.DBConnection;
import service.AttendanceService;
public class EmployeeAuthService {
    public boolean login(String email,String password){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
    int empId = rs.getInt("emp_id");
    int companyId = rs.getInt("company_id");
    AttendanceService service = new AttendanceService();
    service.fillMissingAttendance(companyId);
    markAttendance(empId, companyId);
    return true;
    }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
private void markAttendance(int empId,int companyId){
    try{
        Connection con = DBConnection.getConnection();
        String sql =
            "INSERT INTO attendance(emp_id,company_id,date,status) " +
            "SELECT ?,?,CURDATE(),'PRESENT' " +
            "WHERE NOT EXISTS (" +
            "SELECT 1 FROM attendance WHERE emp_id=? AND date=CURDATE()" +
            ")";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, empId);
        ps.setInt(2, companyId);
        ps.setInt(3, empId);   
        ps.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}
}