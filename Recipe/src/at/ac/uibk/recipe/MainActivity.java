package at.ac.uibk.recipe;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import at.ac.uibk.Beans.City;
import at.ac.uibk.Beans.Country;
import at.ac.uibk.recipe.api.RestApi;

public class MainActivity extends ActionBarActivity {

	static String[] countries = null;
	static String[] cities = null;

	static List<Country> o = null;
	
	UserCountries mUserCountrie = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
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

			final Button searchButton = (Button) findViewById(R.id.search);
			searchButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(MainActivity.this,
							SearchActivity.class);
					startActivity(intent);

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
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public void setUserVisibleHint(boolean isVisibleToUser) {
			super.setUserVisibleHint(isVisibleToUser);
			if (isVisibleToUser) {
				Activity a = getActivity();
				if (a != null)
					a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
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

		// public String[] getResultCity() {
		// return resultCity;
		// }

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
}
