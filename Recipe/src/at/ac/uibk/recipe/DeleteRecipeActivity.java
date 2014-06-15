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
import at.ac.uibk.recipe.adapter.MyArrayDeleteAdapter;
import at.ac.uibk.recipe.api.RestApi;

public class DeleteRecipeActivity extends Activity implements OnClickListener {

	private MyArrayDeleteAdapter adapter = null;

	List<Recipe> data = null;
	ListView listView = null;

	Recipe which = null;
	UserDelete mUserDelete = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_found);

		mUserDelete = new UserDelete();
		mUserDelete.execute();

		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

		ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setColorFilter(Color.WHITE);

		ImageButton search = (ImageButton) findViewById(R.id.searchTab);
		search.setColorFilter(Color.WHITE);
		
		ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
		favorites.setColorFilter(Color.WHITE);

		ImageButton profile = (ImageButton) findViewById(R.id.profile);
		profile.setColorFilter(Color.WHITE);
		profile.setColorFilter(Color.rgb(41, 205, 255));
		profile.setImageResource(R.drawable.ic_action_person_selected);


		// 2. Get ListView from activity_main.xml
		listView = (ListView) findViewById(R.id.mainListViewFound);

		// setContentView(rootView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_found, menu);
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

			Toast.makeText(DeleteRecipeActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();
			Intent intent = new Intent(DeleteRecipeActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {

			Intent intent = new Intent(DeleteRecipeActivity.this,
					LoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.searchTab) {

			Intent intent = new Intent(DeleteRecipeActivity.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.favorites) {

			Intent intent = new Intent(DeleteRecipeActivity.this,
					FavoritesActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.profile) {
			ImageButton home = (ImageButton) findViewById(R.id.home);
			home.setColorFilter(Color.WHITE, Mode.CLEAR);
			home.setColorFilter(Color.WHITE);

			ImageButton search = (ImageButton) findViewById(R.id.searchTab);
			search.setColorFilter(Color.WHITE, Mode.CLEAR);
			search.setColorFilter(Color.WHITE);

			ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
			favorites.setColorFilter(Color.WHITE, Mode.CLEAR);
			favorites.setColorFilter(Color.WHITE);

			ImageButton profile = (ImageButton) findViewById(R.id.profile);
			profile.setColorFilter(100, Mode.CLEAR);
			profile.setColorFilter(Color.rgb(41, 205, 255));
			profile.setImageResource(R.drawable.ic_action_person_selected);

		}

	}

	public class UserDelete extends AsyncTask<Void, Void, Boolean> {

		List<Recipe> result = null;

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
			mUserDelete = null;
			if (success) {
				List<Recipe> data = new ArrayList<Recipe>();
				SharedPreferences sharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(DeleteRecipeActivity.this);
				String name = sharedPreferences.getString("username", "ab");

				if (name != "ab") {

					for (Recipe r : result) {
						if (r.getAutor().equals(name)) {
							data.add(r);
						}
					}
					if (data.size() != 0) {
						// 1. pass context and data to the custom adapter
						adapter = new MyArrayDeleteAdapter(
								DeleteRecipeActivity.this,
								(ArrayList<Recipe>) data);

						// 3. setListAdapter
						listView.setAdapter(adapter);

						listView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {

								which = adapter.getItem(position);
								DeleteRecipe delete = new DeleteRecipe();
								delete.execute();
							}

						});
					} else {
						Toast.makeText(DeleteRecipeActivity.this,
								"You can't delete any Recipe. You can only delete your own recipes",
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent(DeleteRecipeActivity.this,
								ProfileActivity.class);
						startActivity(intent);
					}
				}

			}
		}

		protected void onCancelled() {
			mUserDelete = null;
		}
	}

	public class DeleteRecipe extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... urls) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(DeleteRecipeActivity.this);
			String name = sharedPreferences.getString("username", "ab");
			if (name != "ab")
				return RestApi.getInstance().removeRecipe(name, which.getID());
			return false;
		}

		protected void onPostExecute(final Boolean success) {
			if (success) {
				Toast.makeText(DeleteRecipeActivity.this, "Recipe was deleted",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(DeleteRecipeActivity.this,
						ProfileActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(DeleteRecipeActivity.this,
						"Error deleting Recipe", Toast.LENGTH_SHORT).show();

			}
		}

		protected void onCancelled() {
		}
	}

}
