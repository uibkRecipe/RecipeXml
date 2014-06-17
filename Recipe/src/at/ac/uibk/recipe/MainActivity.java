package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import at.ac.uibk.Beans.Country;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.api.RestApi;

public class MainActivity extends ActionBarActivity {

	static String[] countries = null;
	static String[] cities = null;

	static List<Country> o = null;

	UserSearch userSearch = null;
	UserCountries mUserCountrie = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String name = sharedPreferences.getString("username", "ab");

		if (name.equals("ab")) {
			final Button loginButton = (Button) findViewById(R.id.login);
			loginButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					Intent intent = new Intent(MainActivity.this,
							LoginActivity.class);
					startActivity(intent);
				}
			});

			final Button registerButton = (Button) findViewById(R.id.register);
			registerButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					mUserCountrie = new UserCountries();
					mUserCountrie.execute();

				}
			});

			final Button searchButton = (Button) findViewById(R.id.searchNot);
			searchButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					userSearch = new UserSearch();
					userSearch.execute();

				}
			});
		} else {
			Intent intent = new Intent(MainActivity.this,
					LoggedInActivity.class);
			startActivity(intent);

			Toast.makeText(MainActivity.this,
					SaveSharedPreference.getUserName(MainActivity.this),
					Toast.LENGTH_LONG).show();

		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// For the main activity, make sure the app icon in the action bar
			// does not behave as a button
			ActionBar actionBar = getActionBar();
			actionBar.setHomeButtonEnabled(false);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	

	/**
	 * to stop all resources that CPU is not used
	 */
	public void onPause() {
		// first call
		super.onPause();
	}

	/**
	 * reopen all resources
	 */
	public void onResume() {
		// first call
		super.onResume();
	}

	/**
	 * finally close every open thread
	 */
	public void onDestroy() {
		// first call
		super.onDestroy();
	}

	/**
	 * called to save username
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// last call
		// super.onSaveInstanceState(savedInstanceState);
	}

	/**
	 * for restoring username
	 */
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// first call
		super.onRestoreInstanceState(savedInstanceState);
	}

	public class UserCountries extends AsyncTask<Void, Void, Boolean> {
		private String[] resultCountry = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			o = RestApi.getInstance().getCountryList();

			resultCountry = new String[o.size()];
			countries = new String[resultCountry.length];

			if (o != null) {
				int i = 0;
				for (Country c : o) {
					resultCountry[i] = c.getName();
					i++;
				}
				return true;
			} else {
				resultCountry = null;
				// resultCity = null;
				return false;
			}

		}

		public String[] getResultCountry() {
			return resultCountry;
		}

		protected void onPostExecute(final Boolean success) {
			if (success) {

				countries = getResultCountry();
				// cities = getResultCity();

				Intent intent = new Intent(MainActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();

			} else {
				countries[0] = "Error finding countries!";
				// cities[0] = "false";

			}
		}

		protected void onCancelled() {
			mUserCountrie = null;

		}
	}

	public class UserSearch extends AsyncTask<Void, Void, Boolean> {
		private List<Recipe> result = null;

		@Override
		protected Boolean doInBackground(Void... urls) {
			List<Recipe> o = RestApi.getInstance().getAllRecipes();
			if (o != null) {
				result = o;
				return true;
			}
			return false;
		}

		protected void onPostExecute(final Boolean success) {
			if (success) {

				if (result.size() > 0) {
					List<Recipe> rat = new ArrayList<Recipe>();
					int i  = 0;
					for(Recipe r: result){
						rat.add(r);
						i++;
						if(i == 5)
							break;

					}
					Intent intent = new Intent(MainActivity.this,
							SearchActivityNotLoggedIn.class);
					intent.putExtra("LIST_RECIPE", (ArrayList<Recipe>) rat);
					startActivity(intent);
				}

			} else {
				Toast.makeText(MainActivity.this, "Error connecting to DB",
						Toast.LENGTH_SHORT).show();
			}
		}

		protected void onCancelled() {

		}
	}
}
