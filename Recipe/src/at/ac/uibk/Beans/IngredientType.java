package at.ac.uibk.Beans;

public class IngredientType {
	private int ID;
	private String name;
	
	public IngredientType(){
		
	}
	
	public IngredientType(String name){
		this.name = name;
	}
	
	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String toString(){
		return this.ID + " " +this.name;
	}
}
