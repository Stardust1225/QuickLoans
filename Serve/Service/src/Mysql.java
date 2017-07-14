import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mysql {

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
	
	
	 static int insert(Person student) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into person(account,password,id,email,tele,name,mac,education,career,marrage,perinfo,payability) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, student.getAccount());
	        pstmt.setString(2, student.getPassword());
	        pstmt.setString(3, student.getId());
	        pstmt.setString(4, student.getEmail());
	        pstmt.setString(5, student.getTele());
	        pstmt.setString(6, student.getName());
	        pstmt.setString(7, student.getMac());
	        pstmt.setString(8, student.getEducation());
	        pstmt.setString(9, student.getCareer());
	        pstmt.setString(10, student.getMarrage());
	        pstmt.setString(11, student.getPerinfo());
	        pstmt.setString(12, student.getPayability());
	       
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	 
	static int changeIdStatue(String id,String account) {
		    Connection conn = getConn();
		    int i = 0;
		    String sql = "update person set mac='" + id + "' where account='" + account + "'";
		    PreparedStatement pstmt;
		    try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        i = pstmt.executeUpdate();
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return i;
		}
	
	static int changelendmoney(String money,String account) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update person set lendmoney='" + money + "' where account='" + account + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	static int changeborrowmoney(String money,String account) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update person set borrowmoney='" + money + "' where account='" + account + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	static String found(String account) {
	    Connection conn = getConn();
	    String sql = "select * from person where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("mac");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static String foundpass(String account) {
	    Connection conn = getConn();
	    String sql = "select * from person where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("password");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static String changefound(String account) {
	    Connection conn = getConn();
	    String sql = "select * from person where account='"+account+"'";
	    String s="3";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        rs.next();
	        s=rs.getString("mac");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return s;
	}
	
	static void delete(String account) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from person where account='" + account + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	static String getinfo(String account) {
	    Connection conn = getConn();
	    String s="";
	    String sql = "select * from person where account='" + account + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        rs.next();
	        s=rs.getString("id")+"\n"+rs.getString("email")+"\n"+rs.getString("tele")+"\n"+rs.getString("name")
	        +"\n"+rs.getString("education")+"\n"+rs.getString("career")+"\n"+rs.getString("marrage");
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static int foundsame(String account,String tele,String id) {
	    Connection conn = getConn();
	    String sql1 = "select * from person where account='"+account+"'";
	    String sql2 = "select * from person where tele='"+tele+"'";
	    String sql3 = "select * from person where id='"+id+"'";
	    int i=0;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql1);
	        i += pstmt.executeUpdate();
	        pstmt = (PreparedStatement)conn.prepareStatement(sql2);
	        i += pstmt.executeUpdate();
	        pstmt = (PreparedStatement)conn.prepareStatement(sql3);
	        i += pstmt.executeUpdate();
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}

	    return i;
		
	}
}
