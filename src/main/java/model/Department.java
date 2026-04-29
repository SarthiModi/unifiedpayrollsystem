package model;

public class Department {

    private int deptId;
    private int companyId;
    private String name;
    private double budget;

    public Department(int deptId,int companyId,String name,double budget){

        this.deptId=deptId;
        this.companyId=companyId;
        this.name=name;
        this.budget=budget;
    }

    public int getDeptId(){
        return deptId;
    }

    public String getName(){
        return name;
    }

    public double getBudget(){
        return budget;
    }
}