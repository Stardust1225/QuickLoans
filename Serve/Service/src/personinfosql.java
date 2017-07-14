import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class personinfosql {

	static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://localhost:3306/person?useUnicode=true&characterEncoding=utf-8";
	    String username = "root";
	    String password = "123";
	    Connection conn = null;
	    try {
	        Class.forName(driver); 
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {}
	    return conn;
	}
	
	
	 static void insert(Dealinfo info) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into persondeal(account,firstdeal,dealnumber,frequency,moneyamount,averagemoney) values(?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, info.getAccount());
	        pstmt.setString(2, info.getFirstDeal());
	        pstmt.setString(3, info.getDealnumber());
	        pstmt.setString(4, info.getFrequency());
	        pstmt.setString(5, info.getMoneyamount());
	        pstmt.setString(6, info.getAveragemoney());
	        pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) { }
	}
	
	 
	static void changeStatue(Dealinfo info) {
		    Connection conn = getConn();
		    int i = 0;
		    String sql = "update persondeal set dealnumber='" + info.getDealnumber() + "' where account='" + info.getAccount() + "'";
		    String sql1 = "update persondeal set frequency='" + info.getFrequency() + "' where account='" + info.getAccount() + "'";
		    String sql2 = "update persondeal set moneyamount='" + info.getMoneyamount() + "' where account='" + info.getAccount() + "'";
		    String sql3 = "update persondeal set averagemoney='" + info.getAveragemoney() + "' where account='" + info.getAccount() + "'";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        pstmt.executeUpdate();
		        
		        pstmt = (PreparedStatement) conn.prepareStatement(sql1);
		        pstmt.executeUpdate();
		        
		        pstmt = (PreparedStatement) conn.prepareStatement(sql2);
		        pstmt.executeUpdate();
		        
		        pstmt = (PreparedStatement) conn.prepareStatement(sql3);
		        pstmt.executeUpdate();
		        
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {}
		}
	
	
	static String found(String account) {
	    Connection conn = getConn();
	    String sql = "select * from persondeal where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery(); 
	        rs.next();
	        s=rs.getString("firstdeal")+"&"+rs.getString("dealnumber")+"&"+rs.getString("frequency")+"&"+rs.getString("moneyamount")+"&"+rs.getString("averagemoney");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
}
