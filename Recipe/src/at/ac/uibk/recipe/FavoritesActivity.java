package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.FavoritesArrayAdapter;
import at.ac.uibk.recipe.api.RestApi;

public class FavoritesActivity extends Activity implements OnClickListener {

	private FavoritesArrayAdapter adapter = null;

	ListView listView = null;

	private List<Recipe> myList = null;
	private UserFavorites mUserFavorites = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String name = sharedPreferences.getString("username", "ab");
		if(name.equals("ab")){
			Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}

		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

		mUserFavorites = new UserFavorites();
		mUserFavorites.execute();

		ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setColorFilter(Color.WHITE);

		ImageButton search = (ImageButton) findViewById(R.id.searchTab);
		search.setColorFilter(Color.WHITE);

		ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
		favorites.setColorFilter(Color.WHITE);
		favorites.setColorFilter(Color.rgb(41, 205, 255));
		favorites.setImageResource(R.drawable.ic_action_favorite_selected);

		ImageButton profile = (ImageButton) findViewById(R.id.profile);
		profile.setColorFilter(Color.WHITE);

		listView = (ListView) findViewById(R.id.mainListViewFavorites);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logged_in, menu);
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
		if (id == R.id.action_logout) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			String name = sharedPreferences.getString("username", "ab");

			Editor editor = sharedPreferences.edit();

			Toast.makeText(FavoritesActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();

			Intent intent = new Intent(FavoritesActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {
			Intent intent = new Intent(FavoritesActivity.this,
					LoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.searchTab) {

			Intent intent = new Intent(FavoritesActivity.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.favorites) {

			ImageButton home = (ImageButton) findViewById(R.id.home);
			home.setColorFilter(Color.WHITE, Mode.CLEAR);
			home.setColorFilter(Color.WHITE);

			ImageButton search = (ImageButton) findViewById(R.id.searchTab);
			search.setColorFilter(Color.WHITE, Mode.CLEAR);
			search.setColorFilter(Color.WHITE);

			ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
			favorites.setColorFilter(100, Mode.CLEAR);
			favorites.setColorFilter(Color.rgb(41, 205, 255));
			favorites.setImageResource(R.drawable.ic_action_favorite_selected);

			ImageButton profile = (ImageButton) findViewById(R.id.profile);
			profile.setColorFilter(Color.WHITE, Mode.CLEAR);
			profile.setColorFilter(Color.WHITE);

		} else if (v.getId() == R.id.profile) {
			Intent intent = new Intent(FavoritesActivity.this,
					ProfileActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		}

	}

	private class UserFavorites extends AsyncTask<Void, Void, Boolean> {

		private List<Recipe> result = null;

		@Override
		protected Boolean doInBackground(Void... urls) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(FavoritesActivity.this);
			String username = sharedPreferences.getString("username", "ab");
			if (username == "ab")
				return false;
			myList = RestApi.getInstance().findFavoriteRecipe(username);
			if (myList == null)
				return false;
			result = myList;
			return true;
		}

		public List<Recipe> getResult() {
			return result;
		}

		protected void onPostExecute(final Boolean success) {

			if (success) {

				if (getResult().size() != 0 && getResult() != null) {

					adapter = new FavoritesArrayAdapter(FavoritesActivity.this,
							(ArrayList<Recipe>) getResult());

					// 3. setListAdapter
					listView.setAdapter(adapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							Intent intent = new Intent(FavoritesActivity.this,
									ShowRecipeActivity.class);
							Recipe recipe = adapter.getItem(position);
							intent.putExtra("SELECTED_RECIPE", recipe);
							intent.putExtra("WHICH_BEFORE", "All");
							startActivity(intent);
						}
					});

				} else {
					Toast.makeText(FavoritesActivity.this,
							"No Favorite Recipes found", Toast.LENGTH_LONG)
							.show();
				}
			} else {

			}

		}
	}
}
