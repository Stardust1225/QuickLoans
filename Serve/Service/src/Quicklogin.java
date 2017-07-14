import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Quicklogin extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
		 
			 
		 String acc=request.getParameter("a");
		 String pass=request.getParameter("p");
		 String id=request.getParameter("i");
		 
		 
		 Mysql db=new Mysql();
		 
		 String flag=db.found(acc);
		 
		 if(id.equals(flag))
		 			response.getWriter().print("1");
		 else 
					response.getWriter().print("3");

		 
		 }catch(Exception e){}
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response){
		 try {
			doGet(request,response);
		} catch (ServletException e) {}
		 
	 }

}
