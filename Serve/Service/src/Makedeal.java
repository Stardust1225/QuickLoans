import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.javafx.collections.MappingChange.Map;
public class Makedeal extends HttpServlet{
	 public void doGet(HttpServletRequest request,HttpServletResponse response)
		     throws ServletException{
				 try{
					 String acc=request.getParameter("a");
					 String money=request.getParameter("m");
					 String year=request.getParameter("y");
					 String rate=request.getParameter("ra");
					 String risk=request.getParameter("ri");			 
					 String kind=request.getParameter("k");
					 String account=request.getParameter("acc");
					 String time=request.getParameter("t");
					 
					 int mon=Integer.parseInt(money);
					 int yea=Integer.parseInt(year);
					 int rat=Integer.parseInt(rate);
					 int ris=Integer.parseInt(risk);

					 Dealsql db=new Dealsql();
					 db.ChangeStatue(new Deal(acc,mon,yea,rat,ris,kind),account,time);
					 
					
					 
				 }catch(Exception e){}
			 }
			 public void doPost(HttpServletRequest request,HttpServletResponse response){
				 try {
					doGet(request,response);
				} catch (ServletException e) {}
				 
			 }

}
