import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Finishdeal extends HttpServlet{
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
				/*	 int yea=Integer.parseInt(year);
					 int rat=Integer.parseInt(rate);
					 int ris=Integer.parseInt(risk);

					 Dealsql db=new Dealsql();
					 db.finishdeal(new Deal(acc,mon,yea,rat,ris,kind),account,time);
				*/
					 
					 personinfosql db1=new personinfosql();
					 String s=db1.found(account);
					 if(s.equals("3"))
						 db1.insert(new Dealinfo(acc,time,"1","1",money,money));
					 else{
						 int i=s.indexOf("&");
						 int j=s.indexOf("&",i+1);
						 String firstdeal=s.substring(0,i);				
						 int dealnumber=Integer.parseInt(s.substring(i+1,j));
						 dealnumber++;
						 i=s.indexOf("&",j+1);
						 float frequency=Float.parseFloat(s.substring(j+1,i));
						 j=s.indexOf("&",i+1);
						 
						 float moneyamo=Float.parseFloat(s.substring(i+1,j));
						 
						 float averagemon=Float.parseFloat(s.substring(j+1,s.length()));
						 
						 Date a1=new SimpleDateFormat("yyyy.MM.dd").parse(time);
						 Date b1=new SimpleDateFormat("yyyy.MM.dd").parse(firstdeal);
						 long day=(a1.getTime()-b1.getTime())/(24*60*60*1000);
						 day++;
						 float fre=dealnumber/day;
						 
						 
						 db1.changeStatue(new Dealinfo(account,"",String.valueOf(dealnumber),
								 String.valueOf(fre),String.valueOf(moneyamo+mon),String.valueOf((moneyamo+mon)/dealnumber) ));
					 }
					 
					 Relativesql db3=new Relativesql();
					 int i=db3.found(acc,account);
					 if(i==-1)
						 db3.insert(new Personrelative(acc,account,1));
					 else{
						 i++;
						 db3.changeWeight(new Personrelative(acc,account,i));
					 }
					 
					 
				 }catch(Exception e){}
			 }
			 public void doPost(HttpServletRequest request,HttpServletResponse response){
				 try {
					doGet(request,response);
				} catch (ServletException e) {}
				 
			 }

}