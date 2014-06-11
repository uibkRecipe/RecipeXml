package at.ac.uibk.Beans;

public class Ingredient {
	int ID;
	int ingredientTypeID;
	int city;
	String brandName;

	public Ingredient() {

	}

	public Ingredient(int ingredientTypeID, int city, String brandname) {
		this.ingredientTypeID = ingredientTypeID;
		this.city = city;
		this.brandName = brandname;

	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getIngredientTypeID() {
		return ingredientTypeID;
	}

	public void setIngredientTypeID(int ingredientTypeID) {
		this.ingredientTypeID = ingredientTypeID;
	}

	public int getCity() {
		return city;
	}

	public void setCity(int city) {
		this.city = city;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
