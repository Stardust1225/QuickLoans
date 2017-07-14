
public class Deal {
	
	private String account,kind;
	private int money,year,rate,risk;
	
	public Deal(String account,int money,int year,int rate,int risk,String kind){
		this.account=account;
		this.money=money;
		this.year=year;
		this.rate=rate;
		this.kind=kind;
		this.risk=risk;
	}
	
	public String getAccount(){
		return account;
	}
	
	public int getMoney(){
		return money;
	}
	
	public int getYear(){
		return year;
	}
	
	public int getRate(){
		return rate;
	}
	
	public String getKind(){
		return kind;
	}
	
	public int getRisk(){
		return risk;
	}

}
