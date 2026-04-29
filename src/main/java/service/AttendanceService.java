package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;

import config.DBConnection;

public class AttendanceService {
   private void markAttendance(int empId,int companyId){
    try{
        LocalDate today = LocalDate.now();
        if(today.getDayOfWeek().getValue() == 7){
            System.out.println("Sunday - No PRESENT marking");
            return;
        }
        Connection con = DBConnection.getConnection();
        String sql =
            "INSERT INTO attendance(emp_id,company_id,date,status) " +
            "SELECT ?,?,CURDATE(),'PRESENT' " +
            "WHERE NOT EXISTS (" +
            "SELECT 1 FROM attendance WHERE emp_id=? AND company_id=? AND date=CURDATE()" +
            ")";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, empId);
        ps.setInt(2, companyId);
        ps.setInt(3, empId);
        ps.setInt(4, companyId);
        ps.executeUpdate();
    }catch(Exception e){
        e.printStackTrace();
    }
}


public void fillMissingAttendance(int companyId) {
    try {
        Connection con = DBConnection.getConnection();
        String empQuery = "SELECT emp_id FROM employee WHERE company_id=?";
        PreparedStatement empPs = con.prepareStatement(empQuery);
        empPs.setInt(1, companyId);
        ResultSet rs = empPs.executeQuery();
        while (rs.next()) {
            int empId = rs.getInt("emp_id");
            for (int i = 1; i <= 30; i++) {
                LocalDate date = LocalDate.now().minusDays(i);
                if (date.getDayOfWeek() == DayOfWeek.SUNDAY) continue;
                String checkQuery =
                    "SELECT 1 FROM attendance WHERE emp_id=? AND company_id=? AND date=?";
                PreparedStatement checkPs = con.prepareStatement(checkQuery);
                checkPs.setInt(1, empId);
                checkPs.setInt(2, companyId);
                checkPs.setDate(3, java.sql.Date.valueOf(date));
                ResultSet checkRs = checkPs.executeQuery();
                if (!checkRs.next()) {
                    String insertQuery =
                        "INSERT INTO attendance(emp_id, company_id, date, status) VALUES (?, ?, ?, 'ABSENT')";
                    PreparedStatement insertPs = con.prepareStatement(insertQuery);
                    insertPs.setInt(1, empId);
                    insertPs.setInt(2, companyId);
                    insertPs.setDate(3, java.sql.Date.valueOf(date));
                    insertPs.executeUpdate();
                }
            }
        }
        String updateLeaves =
            "UPDATE employee e SET leaves_taken = (" +
            "SELECT COUNT(*) FROM attendance a WHERE a.emp_id=e.emp_id AND a.company_id=e.company_id " +
            "AND a.status='ABSENT' AND MONTH(a.date)=MONTH(CURDATE()) AND YEAR(a.date)=YEAR(CURDATE())) " +
           "WHERE e.company_id=?";
        PreparedStatement ps2 = con.prepareStatement(updateLeaves);
        ps2.setInt(1, companyId);
        ps2.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
