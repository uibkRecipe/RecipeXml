package at.ac.uibk.recipe;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import at.ac.uibk.Beans.City;
import at.ac.uibk.Beans.Country;
import at.ac.uibk.Beans.Region;
import at.ac.uibk.recipe.api.RestApi;



/**
 * Only JPEG fotos can be uploaded now. 
 * See if you can read the type out of bitmap to change form jpeg to png
 * 
 * 
 * 
 * @author Hannes
 *
 */
public class RegisterActivity extends Activity {

	private static final int SELECT_PICTURE = 1;

	private List<City> cityList = null;
	private List<Region> regionList = null;

	private String selectedImagePath;
	private String filemanagerstring;

	private UserRegisterTask mAuthTask = null;
	private UserCities mUserCitiesTask = null;
	private UserRegion mUserRegionTask = null;

	private String username = null;
	private String password = null;
	private String passwordRepeat = null;
	private String email = null;
	private String firstname = null;
	private String lastname = null;
	private String country = null;
	private Country countryCount = null;
	private Region regionCount = null;
	private City cityCit = null;

	private String city = null;
	private String region = null;
	private byte[] foto = null;

	ArrayAdapter<String> adapter = null;

	private EditText mUsernameView = null;
	private EditText mPasswordView = null;
	private EditText mPasswordRepeatView = null;
	private EditText mEmailView = null;
	private EditText mFirstnameView = null;
	private EditText mLastnameView = null;
	private EditText mCountryView = null;
	private EditText mCityView = null;
	private EditText mRegionView = null;

	private View mRegisterFormView = null;
	private View mRegisterStatusView = null;
	private TextView mRegisterStatusMessageView = null;

	private TextView textview = null;

	String[] cities = null;
	String[] regions = null;

	private AutoCompleteTextView autoComplete1, autoComplete2, autoComplete3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mUsernameView = (EditText) findViewById(R.id.usernameRegister);
		mPasswordView = (EditText) findViewById(R.id.passwordRegister);
		mPasswordRepeatView = (EditText) findViewById(R.id.passwordRegister2);
		mEmailView = (EditText) findViewById(R.id.emailRegister);
		mFirstnameView = (EditText) findViewById(R.id.firstnameRegister);
		mLastnameView = (EditText) findViewById(R.id.lastnameRegister);
		mCountryView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		mRegionView = (AutoCompleteTextView) findViewById(R.id.autocomplete_region);
		mCityView = (AutoCompleteTextView) findViewById(R.id.autocomplete_city);

		cities = getResources().getStringArray(R.array.city_array);

		// countries = getResources().getStringArray(R.array.country_array);

		mRegisterFormView = findViewById(R.id.register_form);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attempRegister();
					}
				});

		ColorStateList colors = mLastnameView.getHintTextColors();
		textview = (TextView) findViewById(R.id.selected_image);
		textview.setTextColor(colors);

		((Button) findViewById(R.id.select_image))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {

						// in onCreate or any event where your want the user to
						// select a file
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(
								Intent.createChooser(intent, "Select Picture"),
								SELECT_PICTURE);

					}
				});

		// Get a reference to the AutoCompleteTextView in the layout
		autoComplete2 = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);

		// Create the adapter and set it to the AutoCompleteTextView
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, MainActivity.countries);
		autoComplete2.setAdapter(adapter);

		autoComplete2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				country = mCountryView.getText().toString();

				for (Country c : MainActivity.o) {
					if (c.getName().equals(country))
						countryCount = c;
				}

				mUserRegionTask = new UserRegion();
				mUserRegionTask.execute();

				// mUserCitiesTask = new UserCities();
				// mUserCitiesTask.execute();

			}
		});

		// // Get a reference to the AutoCompleteTextView in the layout
		// autoComplete1 = (AutoCompleteTextView)
		// findViewById(R.id.autocomplete_city);
		//
		// // Create the adapter and set it to the AutoCompleteTextView
		// adapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, cities);
		//
		// autoComplete1.setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void attempRegister() {
		if (mAuthTask != null) {
			return;
		}
		mUsernameView.setError(null);
		mPasswordView.setError(null);
		mPasswordRepeatView.setError(null);
		mEmailView.setError(null);
		mFirstnameView.setError(null);
		mLastnameView.setError(null);
		mCityView.setError(null);
		mCountryView.setError(null);
		mRegionView.setError(null);

		username = mUsernameView.getText().toString();
		password = mPasswordView.getText().toString();
		passwordRepeat = mPasswordRepeatView.getText().toString();
		email = mEmailView.getText().toString();
		firstname = mFirstnameView.getText().toString();
		lastname = mLastnameView.getText().toString();
		country = mCountryView.getText().toString();
		city = mCityView.getText().toString();
		region = mRegionView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (username.length() < 4) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}

		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(passwordRepeat)) {
			mPasswordRepeatView
					.setError(getString(R.string.error_field_required));
			focusView = mPasswordRepeatView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordRepeatView
					.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordRepeatView;
			cancel = true;
		} else if (!password.equals(passwordRepeat)) {
			mPasswordRepeatView
					.setError(getString(R.string.error_password_not_same));
			focusView = mPasswordRepeatView;
			cancel = true;
		}

		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!email.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (TextUtils.isEmpty(firstname)) {
			mFirstnameView.setError(getString(R.string.error_field_required));
			focusView = mFirstnameView;
			cancel = true;
		} else if (firstname.length() < 4) {
			mFirstnameView.setError(getString(R.string.error_field_name));
			focusView = mFirstnameView;
			cancel = true;
		}

		if (TextUtils.isEmpty(lastname)) {
			mLastnameView.setError(getString(R.string.error_field_required));
			focusView = mLastnameView;
			cancel = true;
		} else if (lastname.length() < 4) {
			mLastnameView.setError(getString(R.string.error_field_name));
			focusView = mLastnameView;
			cancel = true;
		}
		if (TextUtils.isEmpty(country)) {
			mCountryView.setError(getString(R.string.error_field_required));
			focusView = mCountryView;
			cancel = true;

		} else if (!Arrays.asList(MainActivity.countries).contains(country)) {
			mCountryView.setError(getString(R.string.error_country_invalid));
			focusView = mCountryView;
			cancel = true;
		}

		if (TextUtils.isEmpty(region)) {
			mRegionView.setError(getString(R.string.error_field_required));
			focusView = mRegionView;
			cancel = true;

		} else if (!Arrays.asList(regions).contains(region)) {
			mRegionView.setError(getString(R.string.error_city_invalid));
			focusView = mRegionView;
			cancel = true;
		}

		if (TextUtils.isEmpty(city)) {
			mCityView.setError(getString(R.string.error_field_required));
			focusView = mCityView;
			cancel = true;

		} else if (!Arrays.asList(cities).contains(city)) {
			mCityView.setError(getString(R.string.error_city_invalid));
			focusView = mCityView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {

			int i = 0;
			for (String c : cities) {

				if (c.equals(city)) {

					break;
				}
				i++;
			}

			System.out.println(i);
			cityCit = cityList.get(i);

			mRegisterStatusMessageView
					.setText(R.string.register_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserRegisterTask();
			mAuthTask.execute();
		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterStatusView
									.setVisibility(show ? View.VISIBLE
											: View.GONE);
						}
					});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
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

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				// OI FILE Manager
				filemanagerstring = selectedImageUri.getPath();

				// MEDIA GALLERY
				selectedImagePath = getPath(selectedImageUri);

				// DEBUG PURPOSE - you can delete this if you want
				if (selectedImagePath != null)
					Log.i("ABCD", selectedImagePath);
				else
					Log.i("ABCD", "selectedImagePath is null");
				if (filemanagerstring != null)
					Log.i("ABCD", filemanagerstring);
				else
					Log.i("ABCD", "filemanagerstring is null");

				Bitmap myBitmap = null;
				// NOW WE HAVE OUR WANTED STRING
				if (selectedImagePath != null) {

					textview.setText(selectedImagePath + " ");
					InputStream is;

					try {
						is = this.getContentResolver().openInputStream(
								selectedImageUri);
						myBitmap = BitmapFactory.decodeStream(is);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();

					myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
					myBitmap.recycle();
					foto = stream.toByteArray();

					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					textview.setText(filemanagerstring + " ");

					InputStream is;
					try {
						is = this.getContentResolver().openInputStream(
								selectedImageUri);
						myBitmap = BitmapFactory.decodeStream(is);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();

					myBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
					myBitmap.recycle();

					foto = stream.toByteArray();

					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Log.i("ABCD", "filemanagerstring is the right one for you!");
				}

			}
		}
	}

	// UPDATED!
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	/**
	 * Represents an asynchronous login task used to register the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void... oarams) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			Boolean o = false;

			if (foto == null) {

				// in countryCount is country name witht type Country

				System.out.println(lastname);
				o = RestApi.getInstance().addUser(username, password, email,
						firstname, lastname, cityCit);
			} else {
				o = RestApi.getInstance().addUser(username, password, email,
						firstname, lastname, foto, cityCit);
			}
			if (o == null) {
				return false;

			}
			return o;
		}

		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			if (success) {
				finish();

			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
				mPasswordRepeatView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordRepeatView.requestFocus();

			}

		}

		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	public class UserRegion extends AsyncTask<Void, Void, Boolean> {
		private String[] resultRegion = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			regionList = RestApi.getInstance().findRegionByCountryCode(
					countryCount.getCode());

			resultRegion = new String[regionList.size()];
			regions = new String[resultRegion.length];

			if (regionList != null) {
				int i = 0;
				for (Region c : regionList) {
					resultRegion[i] = c.getName();
					i++;
				}
				return true;
			} else {
				resultRegion = null;
				// resultCity = null;
				return false;
			}

		}

		public String[] getResultRegion() {
			return resultRegion;
		}

		protected void onPostExecute(final Boolean success) {

			if (success) {

				regions = getResultRegion();

				// Get a reference to the AutoCompleteTextView in the layout
				autoComplete3 = (AutoCompleteTextView) findViewById(R.id.autocomplete_region);

				// Create the adapter and set it to the AutoCompleteTextView
				adapter = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_list_item_1, regions);
				autoComplete3.setAdapter(adapter);

				autoComplete3.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						region = mRegionView.getText().toString();

						for (Region c : regionList) {
							if (c.getName().equals(region))
								regionCount = c;
						}

						mUserCitiesTask = new UserCities();
						mUserCitiesTask.execute();

					}
				});

			} else {
				regions[0] = "Error finding regions!";
				// cities[0] = "false";

			}
		}

		protected void onCancelled() {
			mUserCitiesTask = null;

		}
	}

	public class UserCities extends AsyncTask<Void, Void, Boolean> {
		private String[] resultCities = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			cityList = RestApi.getInstance().findCityByCountryAndRegion(
					countryCount.getCode(), regionCount.getCode());

			resultCities = new String[cityList.size()];
			cities = new String[resultCities.length];

			if (cityList != null) {
				int i = 0;
				for (City c : cityList) {
					resultCities[i] = c.getName();
					i++;
				}
				Log.e("ABCD", resultCities[10] + " ");
				return true;
			} else {
				resultCities = null;
				// resultCity = null;
				return false;
			}

		}

		public String[] getResultCities() {
			return resultCities;
		}

		// public String[] getResultCity() {
		// return resultCity;
		// }

		protected void onPostExecute(final Boolean success) {

			if (success) {

				cities = getResultCities();
				Log.e("ABCD", cities[10] + " ");

				// Get a reference to the AutoCompleteTextView in the layout
				autoComplete1 = (AutoCompleteTextView) findViewById(R.id.autocomplete_city);

				// Create the adapter and set it to the AutoCompleteTextView
				adapter = new ArrayAdapter<String>(RegisterActivity.this,
						android.R.layout.simple_list_item_1, cities);
				autoComplete1.setAdapter(adapter);

			} else {
				cities[0] = "Error finding countries!";
				// cities[0] = "false";

			}
		}

		protected void onCancelled() {
			mUserCitiesTask = null;

		}
	}
}
