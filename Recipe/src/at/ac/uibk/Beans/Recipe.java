package at.ac.uibk.Beans;

public class Recipe {

	private String name = null;
	private String title = null;

	private String description = null;
	private String ingredients = null;
	private byte[] picture = null;

	public Recipe(String name, String description, String ingredients,
			String title) {
		this.name = name;
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
