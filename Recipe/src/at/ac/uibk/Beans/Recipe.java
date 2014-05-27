package at.ac.uibk.Beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Recipe implements Serializable {

	private int id = -1;
	private String name = null;
	private String title = null;

	private String description = null;
	private String ingredients = null;
	private String preparation = null;
	private byte[] picture = null;

	public Recipe(int id, String name, String description, String ingredients,
			String title, String preparation) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.description = description;
		this.ingredients = ingredients;
		this.preparation = preparation;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return this.id + " " + this.name + " " + this.title + " "
				+ this.description + " " + this.ingredients + " "
				+ this.preparation;
	}

}