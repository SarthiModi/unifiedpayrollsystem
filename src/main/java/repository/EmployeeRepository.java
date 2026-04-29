package repository;
import config.DBConnection;
import java.sql.*;
import model.Employee;
public class EmployeeRepository {
    public boolean emailExists(String email){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE email=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }
 public boolean emailExists(String email, int companyId){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT 1 FROM employee WHERE email=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,email);
            ps.setInt(2,companyId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(Exception e){
            return false;
        }
    }

    public boolean registerEmployee(int companyId,int deptId,String name,String email,
    String password,String companyType,String employeeType,double salary,String tier){

        try{
            if(name == null || name.trim().isEmpty()) return false;
            if(emailExists(email, companyId)) return false;

            DepartmentRepository deptRepo = new DepartmentRepository();
            if(!deptRepo.departmentExists(deptId, companyId)) return false;

            Connection con = DBConnection.getConnection();

            double hra = 20, da = 10, ta = 5;

            if(companyType.equalsIgnoreCase("government")){
                if("tier1".equalsIgnoreCase(tier)){
                    hra = 30; da = 15; ta = 10;
                } else if("tier2".equalsIgnoreCase(tier)){
                    hra = 25; da = 12; ta = 8;
                }
            }

            String sql = "INSERT INTO employee(company_id,dept_id,name,email,password,company_type,employee_type,salary,hra_percent,da_percent,ta_percent,tier,bonus) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,0)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,companyId);
            ps.setInt(2,deptId);
            ps.setString(3,name);
            ps.setString(4,email);
            ps.setString(5,password);
            ps.setString(6,companyType);
            ps.setString(7,employeeType);
            ps.setDouble(8,salary);
            ps.setDouble(9,hra);
            ps.setDouble(10,da);
            ps.setDouble(11,ta);
            ps.setString(12,tier);

            ps.executeUpdate();

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

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

    public Employee findByEmail(String email,int companyId){

        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE email=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,email);
            ps.setInt(2,companyId); 
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getInt("company_id"),
                        rs.getInt("dept_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("company_type"),     
                        rs.getString("employee_type"),    
                        rs.getDouble("salary")
                );

                emp.setGoals(rs.getInt("goals"));
                emp.setLeaves(rs.getInt("leaves_taken"));
                emp.setIncrementPercent(rs.getDouble("increment_percent"));
                emp.setHraPercent(rs.getDouble("hra_percent"));
                emp.setDaPercent(rs.getDouble("da_percent"));
                emp.setTaPercent(rs.getDouble("ta_percent"));
                emp.setTier(rs.getString("tier"));
                emp.setBonus(rs.getDouble("bonus"));   
                return emp;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Employee findByEmail(String email){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM employee WHERE email=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,email);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            Employee emp = new Employee(
                    rs.getInt("emp_id"),
                    rs.getInt("company_id"),
                    rs.getInt("dept_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("company_type"),
                    rs.getString("employee_type"),
                    rs.getDouble("salary")
            );

            emp.setGoals(rs.getInt("goals"));
            emp.setLeaves(rs.getInt("leaves_taken"));
            emp.setIncrementPercent(rs.getDouble("increment_percent"));
            emp.setHraPercent(rs.getDouble("hra_percent"));
            emp.setDaPercent(rs.getDouble("da_percent"));
            emp.setTaPercent(rs.getDouble("ta_percent"));
            emp.setTier(rs.getString("tier"));
            emp.setBonus(rs.getDouble("bonus"));

            return emp;
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}

    public Employee findById(int empId,int companyId){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM employee WHERE emp_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,empId);
            ps.setInt(2,companyId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Employee emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getInt("company_id"),
                        rs.getInt("dept_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("company_type"),   
                        rs.getString("employee_type"),  
                        rs.getDouble("salary")
                );

                emp.setGoals(rs.getInt("goals"));
                emp.setLeaves(rs.getInt("leaves_taken"));
                emp.setIncrementPercent(rs.getDouble("increment_percent"));
                emp.setHraPercent(rs.getDouble("hra_percent"));
                emp.setDaPercent(rs.getDouble("da_percent"));
                emp.setTaPercent(rs.getDouble("ta_percent"));
                emp.setTier(rs.getString("tier"));
                emp.setBonus(rs.getDouble("bonus"));   
                return emp;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateSalaryComponents(int empId,int companyId,double hra,double da,double ta){
        try{
            Connection con = DBConnection.getConnection();
            String sql = "UPDATE employee SET hra_percent=?, da_percent=?, ta_percent=? WHERE emp_id=? AND company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1,hra);
            ps.setDouble(2,da);
            ps.setDouble(3,ta);
            ps.setInt(4,empId);
            ps.setInt(5,companyId);
            int rows = ps.executeUpdate();
            if(rows==0)
                System.out.println("Employee not found in your company!");
            else
                System.out.println("Salary components updated");
        }catch(Exception e){
            System.out.println("Error updating components");
        }
    }
}