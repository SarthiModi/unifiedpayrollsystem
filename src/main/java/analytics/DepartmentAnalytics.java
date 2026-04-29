package analytics;
//required libraries added
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;
public class DepartmentAnalytics {
public List<Object[]> getDepartmentExpenditure(int companyId){
List<Object[]> list = new ArrayList<>();
try{
Connection con = DBConnection.getConnection();
//creating query to check the expense of each department and grouping them according to department id and name
String sql = "SELECT d.dept_id, d.dept_name, SUM(e.salary) FROM department d LEFT JOIN employee e " +
"ON d.dept_id = e.dept_id AND d.company_id = e.company_id WHERE d.company_id=? " +
"GROUP BY d.dept_id, d.dept_name";
PreparedStatement ps = con.prepareStatement(sql);
ps.setInt(1, companyId);
ResultSet rs = ps.executeQuery();
//converting resultset to list
while(rs.next()){
list.add(new Object[]{rs.getInt(1),rs.getString(2),rs.getDouble(3)});
}
}catch(Exception e){
e.printStackTrace();
}
//returning the table in form of list
return list;
}
}