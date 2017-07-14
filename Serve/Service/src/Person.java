
public class Person {
	String account,password,id,email=null,tele=null,name=null,mac,education,marrage,career,perinfo,payability;
	
	public Person(String acc,String pass,String id){
		account=acc;
		password=pass;
		this.id=id;
	}
	
	public Person(String acc,String pass,String id,String email,String tele,String name,String mac,String education,String marrage,String career,String per,String pay){
		account=acc;
		password=pass;
		this.id=id;
		this.email=email;
		this.tele=tele;
		this.name=name;
		this.mac=mac;
		this.education=education;
		this.marrage=marrage;
		this.career=career;
		this.perinfo=per;
		this.payability=pay;
		
	}
	
	public String getAccount(){
		return account;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getId(){
		return id;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getTele(){
		return tele;
	}
	
	public String getName(){
		return name;
	}
	
	public String getMac(){
		return mac;
	}
	
	public String getEducation(){
		return education;
	}
	
	public String getCareer(){
		return career;
	}
	
	public String getMarrage(){
		return marrage;
	}
	
	public String getPayability(){
		return payability;
	}
	
	public String getPerinfo(){
		return perinfo;
	}
	
	
	
	
	
}
