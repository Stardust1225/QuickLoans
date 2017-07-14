
public class Dealinfo {
	String account,firstdeal,dealnumber,frequency,moneyamount,averagemoney;
	
	public Dealinfo(String account,String firstdeal,String dealnumber,String frequency,String money1,String money2){
		this.account=account;
		this.firstdeal=firstdeal;
		this.dealnumber=dealnumber;
		this.frequency=frequency;
		this.moneyamount=money1;
		this.averagemoney=money2;
	}
	
	public String getAccount(){
		return account;
	}
	
	public String getFirstDeal(){
		return firstdeal;
	}
	
	public String getDealnumber(){
		return dealnumber;
	}
	
	public String getFrequency(){
		return frequency;
	}
	
	public String getMoneyamount(){
		return moneyamount;
	}
	
	public String getAveragemoney(){
		return averagemoney;
	}
}
