package at.ac.uibk.recipe.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.provider.MediaStore.Files;
import at.ac.uibk.Beans.User;

public class RestApi {

	private static final String URLBASE = "http://138.232.65.234:8080/RestServer2/rest/manager/";

	private static RestApi instance;

	private RestApi() {
	}

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
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(test);
		User o = null;
		try {
			o = mapper.readValue(test, User.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return o;
	}

	public Boolean addUser(String username, String password, String email,
			String firstname, String lastname, byte[] foto) {

		User newUser = new User(username, password, email, firstname, lastname);
		newUser.setIsActive(1);
		newUser.setFoto(foto);
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();

		Boolean ret = false;

		try {
			mapper.writeValue(strWriter, newUser);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String userDataJSON = strWriter.toString();

		try {
			HttpResponse response1 = doPost(URLBASE + "register", userDataJSON);
			System.out.println(response1.getStatusLine().toString());
			HttpEntity entity = response1.getEntity();
			if (entity != null) {
				String retSrc = EntityUtils.toString(entity);
				ret = mapper.readValue(retSrc, Boolean.class);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}

	public Boolean addUser(String username, String password, String email,
			String firstname, String lastname) {

		User newUser = new User(username, password, email, firstname, lastname);
		newUser.setIsActive(1);
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();

		Boolean ret = false;

		try {
			mapper.writeValue(strWriter, newUser);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String userDataJSON = strWriter.toString();

		try {
			HttpResponse response1 = doPost(URLBASE + "register", userDataJSON);
			System.out.println(response1.getStatusLine().toString());
			HttpEntity entity = response1.getEntity();
			if (entity != null) {
				String retSrc = EntityUtils.toString(entity);
				ret = mapper.readValue(retSrc, Boolean.class);
			}

		} catch (ClientProtocolException e) {
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

	public List<String> getCountryList() {
		String test = doGet(URLBASE + "country");
		System.out.println(test);
		ObjectMapper mapper = new ObjectMapper();
		List<String> countries = null;
		try {
			countries = mapper.readValue(test,
					new TypeReference<List<String>>() {
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String s : countries) {
			System.out.println("hoi" + s);
		}
		return countries;
	}

	public boolean getCountryByCode(String countryCode) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/countrybycode/"
				+ countryCode);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findCountryByName(String countryName) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/country/"
				+ countryName);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findCountryCodeByName(String countryName) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/countrycode/"
				+ countryName);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/*****************************************************************
	 * 
	 * City Functionalities
	 * 
	 *****************************************************************/

	public boolean findCityById(int cityId) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/city/"
				+ cityId);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findCityByName(String cityName) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/city/"
				+ cityName);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findCityByCountryAndRegion(String country, String region) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/city/"
				+ country + "/" + region);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/******************************************************************
	 * 
	 * Friends functionalities
	 * 
	 ******************************************************************/

	public boolean getFriend(String username) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/friend/"
				+ username);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	// public boolean addFriend() {
	//
	// }
	//
	// public boolean deleteFriend() {
	//
	// }

	public boolean existFriend(String username1, String username2) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/friend/"
				+ username1 + "/" + username2);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/****************************************************************
	 * 
	 * Recipe functionalities
	 * 
	 ****************************************************************/

	// public boolean addRecipe() {
	//
	// }

	public boolean findRecipeByAutor(String author) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/recipe/"
				+ author);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	// public boolean removeRecipe() {
	//
	// }

	/***************************************************************
	 * 
	 * IngredientType manager
	 * 
	 ***************************************************************/
	public boolean getAllIngredientType() {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/ingredientType/");
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findIngredientByName(String ingredientname) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/ingredientType/"
				+ ingredientname);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	// public boolean addIngredientType() {
	//
	// }

	/***********************************************************
	 * 
	 * Composed Of
	 * 
	 ***********************************************************/

	public boolean getIngredients(int recipeId) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/ingredient/"
				+ recipeId);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	public boolean findRezeptByIngredient() {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/recipe/ingredients");
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	// public boolean addIngredientToRecipe() {
	//
	// }

	/****************************************************************
	 * 
	 * Ingredient
	 * 
	 ****************************************************************/

	// public boolean addIngredient() {
	//
	// }

	/*****************************************************************
	 * 
	 * Rating
	 * 
	 *****************************************************************/

	// public boolean addRating() {
	//
	// }

	/****************************************************************
	 * 
	 * Region
	 * 
	 *****************************************************************/

	public boolean getRegionByCountryCode(String countryCode) {
		String test = doGet("http://138.232.65.234:8080/RestServer/rest/region/"
				+ countryCode);
		ObjectMapper mapper = new ObjectMapper();
		Boolean o = null;
		try {
			o = mapper.readValue(test, Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}

	/****************************************************************
	 * 
	 * Functions: doGet() doPost() convertStreamToString()
	 * 
	 ****************************************************************/

	public String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	public String doGet(String url) {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Accept JSON
		httpget.addHeader("accept", "application/json");
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Get the response entity
			HttpEntity entity = response.getEntity();
			// If response entity is not null
			if (entity != null) {
				// get entity contents and convert it to string
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				// Closing the input stream will trigger connection release
				instream.close();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the json
		return result;
	}

	public static HttpResponse doPost(String url, String string)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		StringEntity s = new StringEntity(string);

		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");

		request.setEntity(s);
		request.addHeader("accept", "application/json");
		request.addHeader("Content-type", "application/json");

		return httpclient.execute(request);
	}

}
