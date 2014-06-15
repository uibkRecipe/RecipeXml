package at.ac.uibk.Beans;

import java.io.Serializable;

public class FavoriteRecipe implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int recipeID;
	String user;

	public FavoriteRecipe() {
	}

	public int getRecipeID() {
		return recipeID;
	}

	public FavoriteRecipe(int recipeID, String user) {
		super();
		this.recipeID = recipeID;
		this.user = user;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean equals(Object o) {
		FavoriteRecipe fr = (FavoriteRecipe) o;
		if (this.recipeID == fr.recipeID && this.user == fr.user)
			return true;
		else
			return false;
	}

	public String toString() {
		return "FAV RECIPE OF " + this.user + " = " + this.recipeID;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		hash = hash * 17 + user.hashCode();
		hash = hash * 31 + recipeID;
		return hash;
	}
}
