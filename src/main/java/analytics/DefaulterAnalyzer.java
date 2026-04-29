package analytics;
import java.sql.Connection;
import java.sql. PreparedStatement;
import java.sql. ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.DBConnection;

public class DefaulterAnalyzer {

public List<Object[]> getDefaulters(int companyId){
List<Object[]> list =new ArrayList<>();
try {
Connection con=DBConnection.getConnection();
String sql="SELECT name,goals,leaves_taken FROM employee WHERE company_id=? ORDER BY goals ASC,leaves_taken DESC LIMIT 5";
PreparedStatement ps=con.prepareStatement(sql);
ps.setInt(1,companyId);
ResultSet rs=ps.executeQuery();
//adding the elements from table to the list
while(rs.next()){
list.add(new Object[]{ rs.getString("name"),rs.getInt("goals"),rs.getInt("leaves_taken")});
}
} catch (Exception e) {
e.printStackTrace();
}
//returning the table in form of list
return list;
}
}