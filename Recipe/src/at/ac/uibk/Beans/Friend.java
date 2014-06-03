package at.ac.uibk.Beans;

import java.io.Serializable;

public class Friend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username1; //src
	private String username2; //dest
	//private boolean isValid;
	
	public Friend(){
		
	}
	public Friend(String username1, String username2){
		this.username1 = username1;
		this.username2 = username2;
		//isValid = false;
	}
	public String getUsername1() {
		return username1;
	}
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	public String getUsername2() {
		return username2;
	}
	public void setUsername2(String username2) {
		this.username2 = username2;
	}
	
	public boolean equals(Object o){
		Friend f = (Friend) o;
		boolean toRet = false;
		if(this.username1 == f.getUsername1() && this.username2 == f.getUsername2())
			toRet = true;
		else if(this.username1 == f.getUsername2() && this.username2 == f.getUsername1())
			toRet = true;
		return toRet;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + username1.hashCode();
		hash = hash * 31 + username2.hashCode();
		return hash;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
