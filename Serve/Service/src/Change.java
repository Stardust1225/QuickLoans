
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Change extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
			 String acc=request.getParameter("a");
			 String pass=request.getParameter("p");
			 String id=request.getParameter("i");
			 String email=request.getParameter("e");
			 String tele=request.getParameter("t");
			 String name=request.getParameter("n");
			 String mac=request.getParameter("m");
			 String education=request.getParameter("edu");
			 String career=request.getParameter("c");
			 String marrage=request.getParameter("marr");
			 
			 Mysql db=new Mysql();
			 
			 int perinfo=0;
				
				switch(marrage.charAt(0)){
					case '0':perinfo=7;break;
					case '1':perinfo=10;break;
					case '2':perinfo=5;break;
				}
				
				switch(education.charAt(0)){
					case '0':perinfo+=0;break;
					case '1':perinfo+=2;break;
					case '2':perinfo+=4;break;
					case '3':perinfo+=6;break;
					case '4':perinfo+=8;break;
					case '5':perinfo+=10;break;		
				}
				
				perinfo=perinfo/2;
				
				int pay=0;
				switch(career.charAt(0)){
					case '0':perinfo+=5;break;
					case '1':perinfo+=6;break;
					case '2':perinfo+=7;break;
					case '3':perinfo+=8;break;
					case '4':perinfo+=9;break;
				}
			 
			 String flag=db.changefound(acc);
			 System.out.println(mac);
			 System.out.println(flag);
			 if(flag.equals(mac)){
				 response.getWriter().print("1");
				 db.delete(acc);
				 db.insert(new Person(acc,pass,id,email,tele,name,mac,education,marrage,career,String.valueOf(perinfo),String.valueOf(pay)));
			 
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