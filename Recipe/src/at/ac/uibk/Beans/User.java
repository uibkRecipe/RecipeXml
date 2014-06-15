package at.ac.uibk.Beans;

import java.io.File;
import java.io.FileInputStream;



public class User {
	private String username;
	private String password;
	private String email;
	private String firstname;
	private String lastname;
	private int isActive;
	private byte[] foto;
	private int city;
	private String country;
	

	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCode(String country) {
		this.country = country;
	}
	/** Hibernate default constructor */
	public User(){
		
	}
	/**
	 * Constructor to create a new user, without asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email
	 */
	public User(String uname, String password, String email, int city, String country){
		this.username = uname;
		this.password = password;
		this.email = email;
		this.firstname = null;
		this.lastname = null;
		this.city = city;
		this.country = country;
		this.isActive = 1;
	}
	
	/**
	 * Constructor to create a new user, without asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email
	 */
	public User(String uname, String password, String email,
			String firstname, String lastname, City c){
		this(uname,password, email,firstname, lastname, c.getID(), c.getCountry());
		
	}
	
	/**
	 * Constructor to create a new user, with asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email Email of the new user
	 * @param vname surname of the new user
	 * @param nname name of the new user
	 */
	public User(String uname, String password, String email, String firstname, String lastname, int city, String country){
		this.username = uname;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.country = country;
		this.isActive = 1;
	}

	/**
	 * Constructor to create a new user, with asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email Email of the new user
	 * @param vname surname of the new user
	 * @param nname name of the new user
	 * @param file to add a photo to the profile
	 */
	public User(String uname, String password, String email, String firstname, String lastname, int city, String country, File f){
		this.username = uname;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		foto = new byte[(int)f.length()];
		this.isActive = 1;
		
		try {
			FileInputStream fileInputStream = new FileInputStream(f);
			fileInputStream.read(foto);
			fileInputStream.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public String toString(){
		return "ID = " + username + ", email = " + email + ", isActive" + isActive;
	}
	
	

}
