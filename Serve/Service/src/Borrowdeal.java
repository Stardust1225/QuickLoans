import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Borrowdeal extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		     throws ServletException{
				 try{
				 
					 
				 String acc=request.getParameter("a");
				
			
				 
				 
				 Dealsql db=new Dealsql();
				 
				 response.getWriter().print(db.returnborrowsuccess(acc));
				
				 
				 }catch(Exception e){}
			 }
			 
			 public void doPost(HttpServletRequest request,HttpServletResponse response){
				 try {
					doGet(request,response);
				} catch (ServletException e) {}
				 
			 }
}
