package at.ac.uibk.Beans;

/**
 * (DE := BEWERTUNG)
 * 
 * @author mirko
 * 
 */
public class Rating {
	int ID;
	int recipeID;
	String username;
	int star;
	String comment;

	public Rating() {

	}

	public Rating(int recipeID, String username, int star) {

		this.recipeID = recipeID;
		this.username = username;
		this.star = star;
	}

	public Rating(int recipeID, String username, int star, String comment) {
		this(recipeID, username, star);
		this.comment = comment;
	}

	public int getRecipeID() {
		return recipeID;
	}

	public void setRecipeID(int recipeID) {
		this.recipeID = recipeID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
