package service;

import config.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import model.Employee;
public class MonthEndService {
    public void processMonthEnd(int companyId){
        try{
            Connection con = DBConnection.getConnection();
            LocalDate today = LocalDate.now();
            LocalDate targetMonthDate = today.minusMonths(1);
            int month = targetMonthDate.getMonthValue();
            int year = targetMonthDate.getYear();
            String checkSql = "SELECT 1 FROM salary_history WHERE company_id=? AND month=? AND year=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, companyId);
            checkPs.setInt(2, month);
            checkPs.setInt(3, year);
            ResultSet checkRs = checkPs.executeQuery();
            if(checkRs.next()){
                System.out.println("Month-end already processed for " + month + "/" + year);
                return;
            }
            int daysInMonth = targetMonthDate.lengthOfMonth();
            for(int day = 1; day <= daysInMonth; day++){
                LocalDate date = LocalDate.of(year, month, day);
                if(date.getDayOfWeek().getValue() == 7) continue;
                String sql =
                    "INSERT INTO attendance(emp_id,company_id,date,status) " +
                    "SELECT emp_id, company_id, ?, 'ABSENT' FROM employee e " +
                    "WHERE company_id=? AND NOT EXISTS (" +
                    "SELECT 1 FROM attendance a WHERE a.emp_id=e.emp_id AND a.date=?" +
                    ")";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setDate(1, java.sql.Date.valueOf(date));
                ps.setInt(2, companyId);
                ps.setDate(3, java.sql.Date.valueOf(date));
                ps.executeUpdate();
            }
            String fetch = "SELECT * FROM employee WHERE company_id=?";
            PreparedStatement ps = con.prepareStatement(fetch);
            ps.setInt(1, companyId);
            ResultSet rs = ps.executeQuery();
            PayrollService payroll = new PayrollService();
            while(rs.next()){
                int empId = rs.getInt("emp_id");
                Employee e = new Employee(
                        empId,
                        rs.getInt("company_id"),
                        rs.getInt("dept_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("company_type"),
                        rs.getString("employee_type"),
                        rs.getDouble("salary")
                );
                e.setHraPercent(rs.getDouble("hra_percent"));
                e.setDaPercent(rs.getDouble("da_percent"));
                e.setTaPercent(rs.getDouble("ta_percent"));
                e.setIncrementPercent(rs.getDouble("increment_percent"));
                e.setBonus(rs.getDouble("bonus"));
                e.setGoals(rs.getInt("goals"));
                String leaveSql =
                    "SELECT COUNT(*) FROM attendance WHERE emp_id=? AND status='ABSENT' " +
                    "AND MONTH(date)=? AND YEAR(date)=?";
                PreparedStatement psLeave = con.prepareStatement(leaveSql);
                psLeave.setInt(1, empId);
                psLeave.setInt(2, month);
                psLeave.setInt(3, year);
                ResultSet rsLeave = psLeave.executeQuery();
                int leaves = 0;
                if(rsLeave.next()){
                    leaves = rsLeave.getInt(1);
                }
                e.setLeaves(leaves);
                double netSalary = payroll.calculateSalary(e);
                String insert =
                    "INSERT INTO salary_history(emp_id,company_id,month,year,base_salary,net_salary,bonus,increment_applied) " +
                    "VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps2 = con.prepareStatement(insert);
                ps2.setInt(1, empId);
                ps2.setInt(2, companyId);
                ps2.setInt(3, month);
                ps2.setInt(4, year);
                ps2.setDouble(5, e.getSalary());
                ps2.setDouble(6, netSalary);
                ps2.setDouble(7, e.getBonus());
                ps2.setDouble(8, e.getIncrementPercent());
                ps2.executeUpdate();
                double newBase = e.getSalary() + (e.getSalary() * e.getIncrementPercent()/100);
                String update =
                    "UPDATE employee SET salary=?, bonus=0, increment_percent=0, goals=0  WHERE emp_id=?";
                PreparedStatement ps3 = con.prepareStatement(update);
                ps3.setDouble(1, newBase);
                ps3.setInt(2, empId);
                ps3.executeUpdate();
            }
            String deptSql =
                "INSERT INTO dept_expense_history(dept_id,company_id,month,year,total_expense) " +
                "SELECT dept_id, company_id, ?, ?, SUM(salary) " +
                "FROM employee WHERE company_id=? GROUP BY dept_id";
            PreparedStatement ps4 = con.prepareStatement(deptSql);
            ps4.setInt(1, month);
            ps4.setInt(2, year);
            ps4.setInt(3, companyId);
            ps4.executeUpdate();
            System.out.println(" Month-end processed successfully for " + month + "/" + year);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}