package at.ac.uibk.Beans;

import java.io.Serializable;

/**
 * (OLD besteht_aus)
 * @author mirko
 *
 */
public class ComposedOf implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int recipeID;
	int ingredientTypeID;
	String quantity;
	
	public ComposedOf(){
		
	}
	public ComposedOf(int recipeID, int ingredientTypeID, String quantity){
		this.recipeID = recipeID;
		this.ingredientTypeID = ingredientTypeID;
		this.quantity = quantity;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public int getIngredientTypeID() {
		return ingredientTypeID;
	}

	public void setIngredientTypeID(int ingredientTypeID) {
		this.ingredientTypeID = ingredientTypeID;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String toString(){
		return this.recipeID + "";
	}
	public boolean equals(Object o){
		ComposedOf c = (ComposedOf)o;
		if(this.recipeID == c.recipeID && this.ingredientTypeID == c.ingredientTypeID)
			return true;
		return false;
	}
	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + recipeID;
		hash = hash * 31 + quantity.hashCode();
		hash = hash * 13 + ingredientTypeID;
		return hash;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
