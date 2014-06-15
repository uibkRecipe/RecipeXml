package at.ac.uibk.Beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

public class Recipe implements Serializable {
	int ID;
	String autor;
	String name;
	String subtitle;
	int time;
	int cooked;
	int numberOfRatings;
	float averageRating;
	String preparation;

	String category;
	byte[] foto;

	double distance;

	public Recipe() {

	}

	public Recipe(String autor, String name, String subtitle, int time,
			String preparation, String category) {
		super();
		this.autor = autor;
		this.name = name;
		this.subtitle = subtitle;
		this.time = time;
		this.preparation = preparation;
		this.cooked = 0;
		this.averageRating = 0;
		this.category = category;
		// /this.foto = foto;
	}

	public Recipe(String autor, String name, String subtitle, int time,
			String preparation, File f) {
		super();
		this.autor = autor;
		this.name = name;
		this.subtitle = subtitle;
		this.time = time;
		this.preparation = preparation;
		foto = new byte[(int) f.length()];
		this.cooked = 0;

		try {
			FileInputStream fileInputStream = new FileInputStream(f);
			fileInputStream.read(foto);
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getCooked() {
		return cooked;
	}

	public void setCooked(int cooked) {
		this.cooked = cooked;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(int numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public Float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Float averageRating) {
		this.averageRating = averageRating;
	}

	public String getPreparation() {
		return preparation;
	}

	public void setPreparation(String preparation) {
		this.preparation = preparation;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String toString() {
		return this.name + " " + this.subtitle;
	}

}
