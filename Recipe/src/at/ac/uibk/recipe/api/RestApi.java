package at.ac.uibk.recipe.api;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import at.ac.uibk.Beans.City;
import at.ac.uibk.Beans.Country;
import at.ac.uibk.Beans.Ingredient;
import at.ac.uibk.Beans.IngredientType;
import at.ac.uibk.Beans.Rating;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.Beans.RecipeIngredients;
import at.ac.uibk.Beans.Region;
import at.ac.uibk.Beans.User;

public class RestApi {

	private static final String URLBASE = "http://138.232.65.234:8080/RestServer/rest/manager/";

	private static RestApi instance;

	private RestApi() {
	}

	private static final ObjectMapper mapper = new ObjectMapper();

	public static RestApi getInstance() {
		if (instance == null) {
			instance = new RestApi();
		}
		return instance;
	}

	/****************************************************************
	 * 
	 * User Functionalities
	 * 
	 ****************************************************************/

	public User login(String username, String password) {
		String test = at.ac.uibk.recipe.api.RestApiLib.doGet(URLBASE + "login/"
				+ username + "/" + password);

		User o = null;
		try {
			o = mapper.readValue(test, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return o;
	}

	public boolean addUser(String username, String password, String email,
			String firstname, String lastname, City city) {
		boolean ret = false;

		User newUser = new User(username, password, email, firstname, lastname,
				city);
		newUser.setIsActive(1);

		// byte[] foto = null;
		// File fi = new File("test.jpg");
		// try {
		// foto = Files.readAllBytes(fi.toPath());
		// } catch (IOException e2) {
		// // TODO Automatisch generierter Erfassungsblock
		// e2.printStackTrace();
		// }
		// newUser.setIsActive(1);
		// newUser.setFoto(foto);

		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(newUser);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(URLBASE
				+ "register", userDataJSON);
		System.out.println(response);
		try {
			ret = mapper.readValue(response, boolean.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	public boolean addUser(String username, String password, String email,
			String firstname, String lastname, byte[] foto, City city) {
		boolean ret = false;

		User newUser = new User(username, password, email, firstname, lastname,
				city);
		newUser.setIsActive(1);

		newUser.setFoto(foto);

		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(newUser);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(URLBASE
				+ "register", userDataJSON);
		System.out.println(response);
		try {
			ret = mapper.readValue(response, boolean.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	public User findUserById(String userName) {
		String url = URLBASE + "findUser/" + userName;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		User newUser = new User();
		try {
			newUser = mapper.readValue(json, User.class);
		} catch (Exception e) {
		}
		return newUser;

	}

	/****************************************************************
	 * 
	 * Country Functionalities
	 * 
	 ****************************************************************/

	public List<Country> getCountryList() {
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(URLBASE
				+ "country");
		List<Country> countries = null;
		try {
			countries = mapper.readValue(json,
					new TypeReference<List<Country>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countries;
	}

	public Country getCountryByCode(String countryCode) {
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(URLBASE
				+ "countryByCode/" + countryCode);
		ObjectMapper mapper = new ObjectMapper();
		Country o = null;
		try {
			o = mapper.readValue(json, Country.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public List<String> findCountryByName(String countryName) {
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(URLBASE
				+ "country/" + countryName);
		ObjectMapper mapper = new ObjectMapper();
		List<String> countries = null;
		try {
			countries = mapper.readValue(json,
					new TypeReference<List<String>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countries;
	}

	public String findCountryCodeByName(String countryName) {
		String url = URLBASE + "countryCode/" + countryName;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		ObjectMapper mapper = new ObjectMapper();
		String countryCode = null;
		try {
			countryCode = mapper.readValue(json, String.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countryCode;
	}

	/*****************************************************************
	 * 
	 * City Functionalities
	 * 
	 *****************************************************************/

	public City findCityById(int cityId) {
		String url = URLBASE + "citybyID/" + cityId;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		City c = null;
		try {
			c = mapper.readValue(json, City.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

	public List<City> findCityByName(String cityName) {
		String url = URLBASE + "citybyName/" + cityName;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<City> cities = null;
		try {
			cities = mapper.readValue(json, new TypeReference<List<City>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}

	public List<City> findCityByCountryAndRegion(String country, String region) {
		String url = URLBASE + "city/" + country + "/" + region;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<City> cities = null;
		try {
			cities = mapper.readValue(json, new TypeReference<List<City>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}

	public List<City> findCityByCountry(String country) {
		String url = URLBASE + "cityByCountry/" + country;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<City> cities = null;
		try {
			cities = mapper.readValue(json, new TypeReference<List<City>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}

	/******************************************************************
	 * 
	 * Friends functionalities
	 * 
	 ******************************************************************/

	public List<String> getFriends(String username) {
		String url = URLBASE + "getFriends/" + username;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<String> friends = null;
		try {
			friends = mapper.readValue(json, new TypeReference<List<String>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}

	public boolean addFriend(String username1, String username2) {
		boolean ret = false;
		String url = URLBASE + "addFriend/" + username2;
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(username1);

		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		try {
			ret = mapper.readValue(response, boolean.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean deleteFriend(String username1, String username2) {
		boolean ret = false;
		String url = URLBASE + "deleteFriend/" + username1 + "/" + username2;
		String json = at.ac.uibk.recipe.api.RestApiLib.doDelete(url);
		try {
			ret = mapper.readValue(json, boolean.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;

	}

	public boolean existFriend(String username1, String username2) {
		String url = URLBASE + "existFriend/" + username1 + "/" + username2;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		boolean ret = false;
		try {
			ret = mapper.readValue(json, boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/****************************************************************
	 * 
	 * Recipe functionalities
	 * 
	 ****************************************************************/

	public boolean addRecipe(Recipe r) {
		boolean ret = false;
		String url = URLBASE + "addRecipe";
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib.objectToJson(r);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		try {
			ret = mapper.readValue(response, boolean.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	public boolean findRecipeByAutor(String author) {
		String url = URLBASE + "findRecipeByAuthor/" + author;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		boolean ret = false;
		try {
			ret = mapper.readValue(json, boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean removeRecipe(String username, int recipeID) {
		boolean ret = false;
		String url = URLBASE + "recipe/" + username + "/" + recipeID;
		String response = at.ac.uibk.recipe.api.RestApiLib.doDelete(url);
		try {
			ret = mapper.readValue(response, boolean.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	/***************************************************************
	 * 
	 * IngredientType manager
	 * 
	 ***************************************************************/
	public List<IngredientType> getAllIngredientType() {
		String test = at.ac.uibk.recipe.api.RestApiLib.doGet(URLBASE
				+ "ingredientType");
		List<IngredientType> iTypes = null;
		try {
			iTypes = mapper.readValue(test,
					new TypeReference<List<IngredientType>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iTypes;
	}

	public List<IngredientType> findIngredientByName(String ingredientname) {
		String url = URLBASE + "ingredientType" + ingredientname;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<IngredientType> ingredients = null;
		try {
			ingredients = mapper.readValue(json,
					new TypeReference<List<IngredientType>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredients;
	}

	public boolean addIngredientType(IngredientType ingredientType) {
		boolean ret = false;
		String url = URLBASE + "addIngredientType";
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(ingredientType);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		try {
			ret = mapper.readValue(response, boolean.class);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	/***********************************************************
	 * 
	 * Composed Of
	 * 
	 ***********************************************************/
	public List<IngredientType> getIngredients(int recipeId) {
		String url = URLBASE + "ingredient/" + recipeId;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<IngredientType> ingredients = null;
		try {
			ingredients = mapper.readValue(json,
					new TypeReference<List<IngredientType>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredients;
	}

	// public boolean findRezeptByIngredient() {
	// String test =
	// doGet("http://138.232.65.234:8080/RestServer/rest/recipe/ingredients");
	// ObjectMapper mapper = new ObjectMapper();
	// boolean o = false;
	// try {
	// o = mapper.readValue(test, boolean.class);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return o;
	// }

	public boolean addIngredientToRecipe(int recipeID,
			RecipeIngredients recIngredients) {
		boolean ret = false;
		String url = URLBASE + "ingredient/" + recipeID;
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(recIngredients);
		System.out.println(userDataJSON);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		System.out.println(response);
		try {
			ret = mapper.readValue(response, boolean.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/****************************************************************
	 * 
	 * Ingredient
	 * 
	 ****************************************************************/

	public boolean addIngredient(Ingredient ingredient) {
		boolean ret = false;
		String url = URLBASE + "addIngredient";
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(ingredient);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		try {
			ret = mapper.readValue(response, boolean.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/*****************************************************************
	 * 
	 * Rating
	 * 
	 *****************************************************************/

	public boolean addRating(Rating rating) {
		boolean ret = false;
		String url = URLBASE + "addRating";
		String userDataJSON = at.ac.uibk.recipe.api.RestApiLib
				.objectToJson(rating);
		String response = at.ac.uibk.recipe.api.RestApiLib.doPost(url,
				userDataJSON);
		try {
			ret = mapper.readValue(response, boolean.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/****************************************************************
	 * 
	 * Region
	 * 
	 *****************************************************************/

	public List<Region> getRegionByCountryCode(String countryCode) {
		String url = URLBASE + "region/" + countryCode;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<Region> regions = null;
		try {
			regions = mapper.readValue(json, new TypeReference<List<Region>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regions;
	}

	/****************************************************************
	 * 
	 * Region
	 * 
	 *****************************************************************/

	public List<Region> findRegionByCountryCode(String countryCode) {
		String url = URLBASE + "region/" + countryCode;
		String json = at.ac.uibk.recipe.api.RestApiLib.doGet(url);
		List<Region> regions = null;
		try {
			regions = mapper.readValue(json, new TypeReference<List<Region>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return regions;
	}

}