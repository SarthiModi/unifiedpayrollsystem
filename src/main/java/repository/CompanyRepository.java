package repository;
import config.DBConnection;
import java.sql.*;
public class CompanyRepository {
    public int createCompany(String name,String type){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO company(company_name,type) VALUES(?,?)";
        PreparedStatement ps = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,name);
        ps.setString(2,type);
        ps.executeUpdate();
        ResultSet rs=ps.getGeneratedKeys();
        if(rs.next()){
            int id=rs.getInt(1);
            System.out.println("Company created. Your Company ID: " + id);
            return id;
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return -1;
}
public String getCompanyType(int companyId){
    try{
        Connection con = DBConnection.getConnection();
        String sql = "SELECT type FROM company WHERE company_id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, companyId);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            return rs.getString("type");
        }

    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}

}
