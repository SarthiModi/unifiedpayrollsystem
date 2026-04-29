package model;

public class Company {

    private int companyId;
    private String companyName;
    private String type;

    public Company(int id,String name,String type){
        this.companyId=id;
        this.companyName=name;
        this.type=type;
    }
    public int getCompanyId(){
        return companyId;
    }
    public String getCompanyName(){
        return companyName;
    }
    public String getType(){
        return type;
    }
}