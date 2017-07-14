import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Adddeal extends HttpServlet 
{

	 public void doGet(HttpServletRequest request,HttpServletResponse response)
     throws ServletException{
		 try{
			 String acc=request.getParameter("a");
			 String money=request.getParameter("m");
			 String year=request.getParameter("y");
			 String rate=request.getParameter("r");
			 String kind=request.getParameter("k");
			 
			 int risk=0;
			 int mon=Integer.parseInt(money);
			 int yea=Integer.parseInt(year);
			 int rat=Integer.parseInt(rate);
			 int kin=Integer.parseInt(kind);
			 
			 if(mon<500)
				 risk+=1;
			 else if(mon<1000)
				 risk+=2;
			 else if(mon<5000)
				 risk+=3;
			 else if(mon<10000)
				 risk+=4;
			 else if(mon<20000)
				 risk+=5;
			 else if(mon<50000)
				 risk+=6;
			 else if(mon<70000)
				 risk+=7;
			 else if(mon<100000)
				 risk+=8;
			 else if(mon<200000)
				 risk+=9;
			 else
				 risk+=10;
			 
			 switch(yea){
			 	case 0:risk+=2;break;
			 	case 1:risk+=4;break;
			 	case 2:risk+=6;break;
			 	case 3:risk+=8;break;
			 	case 4:risk+=10;break;
			 }
			 
			 switch(rat){
			 	case 0:risk+=1;break;
			 	case 1:risk+=2;break;
			 	case 2:risk+=3;break;
			 	case 3:risk+=5;break;
			 	case 4:risk+=7;break;
			 	case 5:risk+=8;break;
			 	case 6:risk+=9;break;
			 	case 7:risk+=10;break;
			 }
			 
			 switch(kin){
			 	case 0:risk+=2;break;
			 	case 1:risk+=4;break;
			 	case 2:risk+=10;break;
			 	case 3:risk+=8;break;
			 	case 4:risk+=6;break;
			 }
			 
			 risk=risk/4;
			 
			 Dealsql db=new Dealsql();
			 int i=db.foundsame(new Deal(acc,mon,yea,rat,risk,kind));
			 if(i==0)
				 db.insert(new Deal(acc,mon,yea,rat,risk,kind));
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
