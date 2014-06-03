package at.ac.uibk.Beans;


public class User {
	private String username;
	private String password;
	private String email;
	private String firstname ;
	private String lastname ;
	private int isActive ;
	private byte[] foto = null;
	
	

	/** Hibernate default constructor */
	public User(){
		
	}
	/**
	 * Constructor to create a new user, without asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email
	 */
	public User(String uname, String password, String email){
		this.username = uname;
		this.password = password;
		this.email = email;
		this.firstname = null;
		this.lastname = null;
	}
	
	/**
	 * Constructor to create a new user, with asking for surname and name
	 * @param uname	Username of the new user
	 * @param password Password of the new user
	 * @param email Email of the new user
	 * @param vname surname of the new user
	 * @param nname name of the new user
	 */
	public User(String uname, String password, String email, String firstname, String lastname){
		this.username = uname;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
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
