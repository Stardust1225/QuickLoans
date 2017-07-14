import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
		 
			 
		 String acc=request.getParameter("a");
		 String pass=request.getParameter("p");
		 String id=request.getParameter("i");
		 
		 
		 Mysql db=new Mysql();
		 
		 String flag=db.foundpass(acc);
		 if(flag.charAt(0)=='3')
			 	response.getWriter().print("4");
		 else if(flag.equals(pass)){
			 	
			 	response.getWriter().print("1");
			 	db.changeIdStatue(id, acc);
			 	
		 }
		 else
			 	 response.getWriter().print("2");
	 
		 }catch(Exception e){}
	 }
	 
	 public void doPost(HttpServletRequest request,HttpServletResponse response){
		 try {
			doGet(request,response);
		} catch (ServletException e) {}
		 
	 }

}
