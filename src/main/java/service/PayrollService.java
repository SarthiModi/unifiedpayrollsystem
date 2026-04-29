package service;
import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Employee;
import strategy.GovernmentSalaryStrategy;
import strategy.PrivateSalaryStrategy;
import strategy.SalaryStrategy;
public class PayrollService {
    public double calculateSalary(Employee e){
        SalaryStrategy strategy;
        if(e.getCompanyType().equalsIgnoreCase("government")){
            strategy = new GovernmentSalaryStrategy();
        } else {
            strategy = new PrivateSalaryStrategy();
        }
        return strategy.calculate(e);
    }
    public void generatePayslip(Employee e){
    double netSalary = calculateSalary(e);
    double base = e.getSalary();
    double hra = base * e.getHraPercent() / 100;
    double da  = base * e.getDaPercent() / 100;
    double ta  = base * e.getTaPercent() / 100;
    double performanceBonus = 0;
    if(e.getCompanyType().equalsIgnoreCase("private")){
        if(e.getEmployeeType().equalsIgnoreCase("regular")){
            performanceBonus = e.getGoals() * 2000;
        } else if(e.getEmployeeType().equalsIgnoreCase("contract")){
            performanceBonus = e.getGoals() * 1000;
        } else if(e.getEmployeeType().equalsIgnoreCase("intern")){
            performanceBonus = e.getGoals() * 500;
        }
    }
    double dbBonus = e.getBonus();
    double gross = base + hra + da + ta + performanceBonus + dbBonus;
    double incrementAmount = gross * e.getIncrementPercent() / 100;
    gross += incrementAmount;
    double tax = gross * 0.10;
    double leaveDeduction = 0;
    if(e.getLeaves() > 4){
        leaveDeduction = (gross / 30) * (e.getLeaves() - 4);
    }
    System.out.println("\n================ PAYSLIP ================");
    System.out.println("Employee Name   : " + e.getName());
    System.out.println("Company Type    : " + e.getCompanyType());
    System.out.println("Employee Type   : " + e.getEmployeeType());
    System.out.println("----------------------------------------");
    System.out.println("Base Salary     : " + base);
    System.out.println("HRA ("+e.getHraPercent()+"%)      : " + hra);
    System.out.println("DA ("+e.getDaPercent()+"%)       : " + da);
    System.out.println("TA ("+e.getTaPercent()+"%)       : " + ta);
    System.out.println("----------------------------------------");
    if(performanceBonus > 0)
        System.out.println("Performance Bonus : " + performanceBonus);
    if(dbBonus > 0)
        System.out.println("Extra Bonus       : " + dbBonus);
    if(incrementAmount > 0)
        System.out.println("Increment Applied : " + incrementAmount);
    System.out.println("----------------------------------------");
    System.out.println("Gross Salary   : " + gross);
    System.out.println("Tax (10%)      : " + tax);
    if(leaveDeduction > 0)
        System.out.println("Leave Deduction: " + leaveDeduction);
    System.out.println("----------------------------------------");
    System.out.println("Net Salary     : " + netSalary);
    System.out.println("Leaves Taken   : " + e.getLeaves());
    System.out.println("Goals Achieved : " + e.getGoals());
    System.out.println("========================================\n");
}
    public double calculateTax(double salary){
        return salary * 0.1;
    }
   public void giveBonus(int empId,int companyId,double bonus){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "UPDATE employee SET bonus = bonus + ? WHERE emp_id=? AND company_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1,bonus);
        ps.setInt(2,empId);
        ps.setInt(3,companyId);
        int rows = ps.executeUpdate();
        if(rows == 0){
            System.out.println(" Employee not found in your company!");
        } else {
            System.out.println("Bonus added");
        }
    }catch(Exception e){
        e.printStackTrace();
    }
}
}