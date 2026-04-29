
package model;

public class Employee {
    private int id;
    private int companyId;
    private int deptId;
    private String name;
    private String email;
    private String password;
    private String companyType;   
    private String employeeType;  
    private double salary;
    private int goals;
    private int leaves;
    private double hraPercent;
    private double daPercent;
    private double taPercent;
    private String tier;
    private double incrementPercent;
    private double bonus;

    public Employee(int id,int companyId,int deptId,String name,
    String email,String password,String companyType,String employeeType,double salary){

        this.id = id;
        this.companyId = companyId;
        this.deptId = deptId;

        this.name = name;
        this.email = email;
        this.password = password;

        this.companyType = companyType;
        this.employeeType = employeeType;

        this.salary = salary;
    }

    public int getId(){ 
        return id; 
    }
    public String getName(){
         return name;
    }
    public String getCompanyType(){ 
        return companyType;
    }
    public String getEmployeeType(){
         return employeeType; 
    }
    public double getSalary(){ 
        return salary; 
    }
    public int getGoals(){ 
        return goals; 
    }
    public int getLeaves(){ 
        return leaves; 
    }
    public double getIncrementPercent(){ 
        return incrementPercent; 
    }
    public double getHraPercent(){ 
        return hraPercent; 
    }
    public double getDaPercent(){ 
        return daPercent; 
    }
    public double getTaPercent(){ 
        return taPercent; 
    }
    public String getTier(){ 
        return tier; 
    }
    public double getBonus(){
        return bonus; 
    }
    public int getCompanyId(){
        return companyId;
    }
    public String getEmail(){
    return email;
}

    public void setCompanyType(String c){ 
        this.companyType = c; 
    }
    public void setEmployeeType(String e){ 
        this.employeeType = e; 
    }
    public void setGoals(int goals){ 
        this.goals = goals; 
    }
    public void setLeaves(int leaves){ 
        this.leaves = leaves; 
    }
    public void setIncrementPercent(double increment){
        this.incrementPercent = increment;
    }
    public void setHraPercent(double h){ 
        this.hraPercent = h; 
    }
    public void setDaPercent(double d){ 
        this.daPercent = d; 
    }
    public void setTaPercent(double t){ 
        this.taPercent = t; 
    }
    public void setTier(String tier){ 
        this.tier = tier; 
    }
    public void setBonus(double bonus){ 
        this.bonus = bonus; 
    }
}