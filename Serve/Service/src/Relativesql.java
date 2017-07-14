import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Relativesql {

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
	
	
	 static void insert(Personrelative info) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into personrelative(borrowperson,lendperson,weight) values(?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, info.getBorrowperson());
	        pstmt.setString(2, info.getLendperson());
	        pstmt.setInt(3, info.getWeight());
	        pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) { }
	}
	
	 
	static void changeWeight(Personrelative info) {
		    Connection conn = getConn();
		    int i = 0;
		    String sql = "update personrelative set weight='" + info.getWeight() + "' where lendperson='" + info.getLendperson() 
		    				+ "' and borrowperson= '"+info.getBorrowperson()+"'";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        pstmt.executeUpdate();
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {}
		}
	

	 static int found(String borrowperson,String lendperson) {
	    Connection conn = getConn();
	    String sql = "select * from personrelative where lendperson= '"+lendperson+"' and borrowperson='"+borrowperson+"'";
	    int i=-1;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();	        
	        rs.next();
	        i=rs.getInt("weight");  
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) { }
	    return i;
	}
	 
	 static String foundBorrow(String lendperson) {
		    Connection conn = getConn();
		    String sql = "select * from personrelative where lendperson= '"+lendperson+"'";
		    String s="";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        ResultSet rs = pstmt.executeQuery();	        
		        while(rs.next())
		        	s+=rs.getString("borrowperson")+"&";  
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) { }
		    return s;
		}
}
