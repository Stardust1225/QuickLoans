import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Lenddeal extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		     throws ServletException{
				 try{
				 
					 
				 String acc=request.getParameter("a");
				
				 Dealsql db=new Dealsql();
				 
				 response.getWriter().print(db.returnlendsuccess(acc));
				
				 
				 }catch(Exception e){}
			 }
			 
			 public void doPost(HttpServletRequest request,HttpServletResponse response){
				 try {
					doGet(request,response);
				} catch (ServletException e) {}
				 
			 }
}
