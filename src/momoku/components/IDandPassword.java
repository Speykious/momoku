package src.momoku.components;

public class IDandPassword {
    
    HashMap<String,String> BDD = new HashMap<String,String>();
	
	IDandPasswords(){
		
		BDD.put("Xie Lian","Prince123");
		BDD.put("SOFIA","PASSWORD");
	}
	
	public HashMap getBDD(){
		return BDD;
	}
}
