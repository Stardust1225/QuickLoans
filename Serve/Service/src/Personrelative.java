
public class Personrelative {
	String lendperson,borrowperson;
	int weight;
	
	public Personrelative(String borrowperson,String lendperson,int edge){
		this.lendperson=lendperson;
		this.weight=edge;
		this.borrowperson=borrowperson;
	}
	
	public String getLendperson(){
		return lendperson;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public String getBorrowperson(){
		return borrowperson;
	}
}
