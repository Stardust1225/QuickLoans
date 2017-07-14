import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dealsql {

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
	
	
	 static int insert(Deal deal) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into deal(account,money,year,rate,risk,kind,statue) values(?,?,?,?,?,?,?)";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, deal.getAccount());
	        pstmt.setInt(2, deal.getMoney());
	        pstmt.setInt(3, deal.getYear());
	        pstmt.setInt(4, deal.getRate());
	        pstmt.setInt(5, deal.getRisk());
	        pstmt.setString(6, deal.getKind());
	        pstmt.setString(7,"0");
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	 
	
	
	
	static String found(Deal deal) {
	    Connection conn = getConn();
	    String sql = "select * from deal where risk<='"+deal.getRisk()+"' and rate >='"+deal.getRate()+"' and money <= '"+deal.getMoney()+"' and year <= '"+deal.getYear()
	    		+"' and statue = 0";
	    String s="";
	    
	    String rate="";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"\n";
	        }
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static void ChangeStatue(Deal deal,String account,String time) {
	    Connection conn = getConn();
	    String sql = "update deal set statue = 1 where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
	    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    String sql1 = "update deal set who = '"+account+"' where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    String sql2= "update deal set lendtime = '"+time+"' where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.executeUpdate();
	        pstmt = (PreparedStatement)conn.prepareStatement(sql1);
	        pstmt.executeUpdate();
	        pstmt = (PreparedStatement)conn.prepareStatement(sql2);
	        pstmt.executeUpdate();
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	}
	
	static String returnborrowsuccess(String account) {
	    Connection conn = getConn();
	    String sql = "select * from deal where account='"+account+"' and statue = '1'";
	    String sql1 = "select * from deal where account='"+account+"' and statue = '2'";
	    String s="";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"&"+rs.getString("statue")+"\n";
	        }
	        
	        pstmt = (PreparedStatement)conn.prepareStatement(sql1);
	        rs = pstmt.executeQuery();
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"&"+rs.getString("statue")+"\n";
	        }
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	
	static String returnlendsuccess(String account) {
	    Connection conn = getConn();
	    String sql = "select * from deal where who='"+account+"' and statue = '1'";
	    String sql1 = "select * from deal where who='"+account+"' and statue = '2'";
	    String s="";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"&"+rs.getString("statue")+"\n";
	        }
	        
	        
	        pstmt = (PreparedStatement)conn.prepareStatement(sql1);
	        rs = pstmt.executeQuery();
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"&"+rs.getString("statue")+"\n";
	        }
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	static int foundsame(Deal deal) {
	    Connection conn = getConn();
	    String sql = "select * from deal where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    int i=0;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        
	    
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return i;
	}
	
	static void finishdeal(Deal deal,String account,String time) {
	    Connection conn = getConn();
	    String sql = "update deal set statue = 2 where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
	    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    String sql1= "update deal set repaytime = '"+time+"' where account='"+deal.getAccount()+"' and rate='"+deal.getRate()+
    			"' and money='"+deal.getMoney()+"' and year='"+deal.getYear()+"' and kind='"+deal.getKind()+"' and risk='"+deal.getRisk()+"'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        pstmt.executeUpdate();
	        pstmt = (PreparedStatement)conn.prepareStatement(sql1);
	        pstmt.executeUpdate();
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	}
	
	static String foundrecommend(String account){
	    Connection conn = getConn();
	    String sql = "select * from deal where account='"+account+"' and statue = 0";    		
	    String s="";
	    String rate="";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        
	        while(rs.next()){
	        	s=s+rs.getString("account")+"&"+String.valueOf(rs.getInt("money"))+"&"+String.valueOf(rs.getInt("year"))+"&"
	        			+String.valueOf(rs.getInt("rate"))+"&"+String.valueOf(rs.getInt("risk"))+"&"+rs.getString("kind")+"\n";
	        }
	        conn.close();
	        pstmt.close();
	    } catch (SQLException e) {}
	    return s;
	}
	
	
}
