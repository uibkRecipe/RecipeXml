package at.ac.uibk.Beans;


public class City {
	int ID;
	String country;
	String region;
	String name;
	double latitude;
	double longitude;

	
	public City(){}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String toString(){
		return this.name;
	}
	public boolean equals(Object o){
		City c = (City) o;
		if(c.latitude == this.latitude && c.longitude == this.longitude)
			return true;
		else 
			return false;
	}
}
