package ui.swing;

import config.DBConnection;
import java.sql.*;
import java.time.*;
import java.util.concurrent.*;
import service.AttendanceService;

public class MainApp {
    public static void main(String[] args) {
        startScheduler();
        new MainFrame();
    }
    private static void startScheduler(){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        long delay = computeDelayToMidnight();
        scheduler.scheduleAtFixedRate(() -> {
            runAttendanceForAllCompanies();
        }, delay, 24, TimeUnit.HOURS);
    }
    private static void runAttendanceForAllCompanies(){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT company_id FROM company";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            AttendanceService service = new AttendanceService();
            while(rs.next()){
                int companyId = rs.getInt("company_id");
                service.fillMissingAttendance(companyId);
            }
            System.out.println("Attendance processed for all companies");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static long computeDelayToMidnight(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.toLocalDate().plusDays(1).atStartOfDay();
        return Duration.between(now, nextRun).getSeconds();
    }


}