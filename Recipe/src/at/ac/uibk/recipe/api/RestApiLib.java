package at.ac.uibk.recipe.api;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class RestApiLib {

	public static String doGet(String url) {
		String result = null;
		System.out.println(url);
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url);
		// Accept JSON
		httpget.addHeader("accept", "application/json");

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			System.out.println(response.getStatusLine().toString());
			// Get the response entity
			HttpEntity entity = response.getEntity();
			// If response entity is not null
			if (entity != null) {
				// get entity contents and convert it to string
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the json
		System.out.println(result);
		return result;
	}

	public static String doPost(String url, String string) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		StringEntity s = null;
		try {
			s = new StringEntity(string, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String retSrc = null;

		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");

		request.setEntity(s);
		request.addHeader("accept", "application/json");
		request.addHeader("Content-type", "application/json");

		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (entity != null) {
				retSrc = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(retSrc);
		return retSrc;

	}

	public static String doPut(String url, String c) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPut request = new HttpPut(url);
		StringEntity s = null;
		try {
			s = new StringEntity(c, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String retSrc = null;

		s.setContentEncoding("UTF-8");
		s.setContentType("application/json");

		request.setEntity(s);
		request.addHeader("accept", "application/json");

		try {
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
			System.out.println(response.getStatusLine());
			if (entity != null) {
				retSrc = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retSrc;
	}

	public static String doDelete(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpDelete delete = new HttpDelete(url);
		String retSrc = null;

		delete.addHeader("accept", "application/json");
		try {
			HttpResponse response = httpclient.execute(delete);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				retSrc = EntityUtils.toString(entity, "UTF-8");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retSrc;
	}

	public static String objectToJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		Writer strWriter = new StringWriter();
		try {
			mapper.writeValue(strWriter, o);
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
		return strWriter.toString();
	}

	// public static String convertStreamToString(InputStream is) throws
	// IOException {
	// if (is != null) {
	// StringBuilder sb = new StringBuilder();
	// String line;
	// try {
	// BufferedReader reader = new BufferedReader(
	// new InputStreamReader(is, "UTF-8"));
	// while ((line = reader.readLine()) != null) {
	// sb.append(line).append("\n");
	// }
	// } finally {
	// is.close();
	// }
	// return sb.toString();
	// } else {
	// return "";
	// }
	// }
}
