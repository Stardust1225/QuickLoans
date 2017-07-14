import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Recommend extends HttpServlet{
	
	public void doGet(HttpServletRequest request,HttpServletResponse response)
		     throws ServletException{
		try{
				 
				String account=request.getParameter("a");
				String res="";
				Relativesql db=new Relativesql();
				Dealsql db2=new Dealsql();
				String s=db.foundBorrow(account);			
				int i=-1,j=s.indexOf("&");
				while(j<s.length()&&j>0){
					res+=db2.foundrecommend(s.substring(i+1,j));
					i=j;
					j=s.indexOf("&",i+1);
					
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(res);
		}catch(Exception e){}
	}
			 
			 
	
	public void doPost(HttpServletRequest request,HttpServletResponse response){
				 try {
					doGet(request,response);
				} catch (ServletException e) {}
	}
}

