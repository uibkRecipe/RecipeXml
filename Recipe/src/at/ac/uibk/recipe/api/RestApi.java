package at.ac.uibk.recipe.api;

import static at.ac.uibk.recipe.api.RestApiLib.doDelete;
import static at.ac.uibk.recipe.api.RestApiLib.doGet;
import static at.ac.uibk.recipe.api.RestApiLib.doPost;
import static at.ac.uibk.recipe.api.RestApiLib.doPut;
import static at.ac.uibk.recipe.api.RestApiLib.objectToJson;

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

	private static final String URLBASE = "http://138.232.65.234:8080/RestServer2/rest/manager/";

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
		String test = doGet(URLBASE + "login/" + username + "/" + password);
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
				city); // new User(username, password, email, firstname,
						// lastname);
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

		String userDataJSON = objectToJson(newUser);
		String response = doPost(URLBASE + "register", userDataJSON);
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
				city); // new User(username, password, email, firstname,
						// lastname);
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
		newUser.setFoto(foto);
		String userDataJSON = objectToJson(newUser);
		String response = doPost(URLBASE + "register", userDataJSON);
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
		String json = doGet(url);
		User newUser = new User();
		try {
			newUser = mapper.readValue(json, User.class);
		} catch (Exception e) {
		}
		return newUser;

	}
	
	public boolean changePassword(String username, String oldPassword,
			String newPassword, String newPasswordConfirm) {
		boolean ret = false;
		String url = URLBASE + "changePassword/" + username + "/" + oldPassword + "/" + newPassword + "/" + newPasswordConfirm;

		String response = doPut(url, "");
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
	 * Country Functionalities
	 * 
	 ****************************************************************/

	public List<Country> getCountryList() {
		String json = doGet(URLBASE + "country");
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
		String json = doGet(URLBASE + "countryByCode/" + countryCode);
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
		String json = doGet(URLBASE + "country/" + countryName);
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
		String json = doGet(url);
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
		String json = doGet(url);
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
		String json = doGet(url);
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
		String json = doGet(url);
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
		String json = doGet(url);
		List<City> cities = null;
		try {
			cities = mapper.readValue(json, new TypeReference<List<City>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
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
		String json = doGet(url);
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
		String userDataJSON = objectToJson(username1);

		String response = doPost(url, username1);
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
		String json = doDelete(url);
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
		String json = doGet(url);
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
		String userDataJSON = objectToJson(r);
		String response = doPost(url, userDataJSON);
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

	public List<Recipe> findRecipeByAutor(String author) {
		String url = URLBASE + "finRecipeByAuthor/" + author;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public boolean removeRecipe(String username, int recipeID) {
		boolean ret = false;
		String url = URLBASE + "recipe/" + username + "/" + recipeID;
		String response = doDelete(url);
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
	
	public List<Recipe> findRecipeByCategory(String category) {
		String url = URLBASE + "findRecipe/" + category;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public Recipe findRecipeById(int recipeID) {
		String url = URLBASE + "findRecipeID/" + recipeID;
		String json = doGet(url);
		Recipe ret = null;
		try {
			ret = mapper.readValue(json, Recipe.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public List<Recipe> getAllRecipes() {
		String url = URLBASE + "getAllRecipes";
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Recipe> findRecipeByName(String name) {
		String url = URLBASE + "findRecipeName/" + name;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Recipe> findRecipeByTime(int mintime, int maxtime) {
		String url = URLBASE + "findRecipeTime/" + mintime + "/" + maxtime;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public Recipe calculateCO2(int recipeID, String username) {
		String url = URLBASE + "Co2Value/" + recipeID + "/" + username;
		String json = doGet(url);
		Recipe ret = null;
		try {
			ret = mapper.readValue(json, Recipe.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public List<Recipe> getCO2FriendlyRec(String username){
		String url = URLBASE + "getCO2FriendlyRec/" + username;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
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
		String test = doGet(URLBASE + "ingredientType");
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
		String json = doGet(url);
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
		String userDataJSON = objectToJson(ingredientType);
		String response = doPost(url, userDataJSON);
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
	public RecipeIngredients getIngredients(int recipeId) {
		String url = URLBASE + "ingredient/" + recipeId;
		String json = doGet(url);
		RecipeIngredients ingredients = null;
		try {
			ingredients = mapper.readValue(json,
					RecipeIngredients.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredients;
	}



	public boolean addIngredientToRecipe(int recipeID,
			RecipeIngredients recIngredients) {
		boolean ret = false;
		String url = URLBASE + "ingredient/" + recipeID;
		String userDataJSON = objectToJson(recIngredients);
		System.out.println(userDataJSON);
		String response = doPost(url, userDataJSON);
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
	
	public List<Recipe> findRecipeByIngredient(int ingredient1) {
		String url = URLBASE + "findRecipe1/" + ingredient1;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}


	
	public List<Recipe> findRecipeByIngredient(int ingredient1, int ingredient2) {
		String url = URLBASE + "findRecipe2/" + ingredient1 + "/" +ingredient2;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}


	
	public List<Recipe> findRecipeByIngredient(int ingredient1,
			int ingredient2, int ingredient3) {
		String url = URLBASE + "findRecipe3/" + ingredient1 + "/" +ingredient2 + "/" + ingredient3;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
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
		String userDataJSON = objectToJson(ingredient);
		String response = doPost(url, userDataJSON);
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
	
	public List<Ingredient> findIngredientsByIngredientType(int ingredientTypeID) {
		String url = URLBASE + "findIngredientByType/" + ingredientTypeID;
		String json = doGet(url);
		List<Ingredient> ingredients = null;
		try {
			ingredients = mapper.readValue(json, new TypeReference<List<Ingredient>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ingredients;
	}

	/*****************************************************************
	 * 
	 * Rating
	 * 
	 *****************************************************************/

	public boolean addRating(Rating rating) {
		boolean ret = false;
		String url = URLBASE + "addRating";
		String userDataJSON = objectToJson(rating);
		String response = doPost(url, userDataJSON);
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

	public List<Region> findRegionByCountryCode(String countryCode) {
		String url = URLBASE + "region/" + countryCode;
		String json = doGet(url);
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
	
	/******************************************************************
	 * 
	 * Miscellaneous 
	 * 
	 *
	 * 
	 *******************************************************************/
		
	public List<Recipe> findFavoriteRecipe(String username) {
		String url = URLBASE + "findFavRecipe/" + username;
		String json = doGet(url);
		List<Recipe> ret = null;
		try {
			ret = mapper.readValue(json, new TypeReference<List<Recipe>>() {
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}


	public boolean addFavoriteRecipe(int recipeID, String username) {
		boolean ret = false;
		String url = URLBASE + "addFavRecipe/" + username;
		String userDataJSON = objectToJson(recipeID);
		String response = doPost(url, userDataJSON);
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



	public boolean addCooked(int recipeID) {
		boolean ret = false;
		String url = URLBASE + "addCooked/" + recipeID;
		String userDataJSON = objectToJson(recipeID);
		String response = doPost(url, userDataJSON);
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

}
