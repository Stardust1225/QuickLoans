import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Requestdeal extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
			 String acc=request.getParameter("a");
			 String money=request.getParameter("m");
			 String year=request.getParameter("y");
			 String rate=request.getParameter("ra");
			 String risk=request.getParameter("ri");			 
			 
			 int mon=Integer.parseInt(money);
			 int yea=Integer.parseInt(year);
			 int rat=Integer.parseInt(rate);
			 int ris=Integer.parseInt(risk);

			 Dealsql db=new Dealsql();
			 String flag=db.found(new Deal(acc,mon,yea,rat,ris,""));
			 response.getWriter().print(flag);
			 
		 }catch(Exception e){}
	 }
	 public void doPost(HttpServletRequest request,HttpServletResponse response){
		 try {
			doGet(request,response);
		} catch (ServletException e) {}
		 
	 }

}
