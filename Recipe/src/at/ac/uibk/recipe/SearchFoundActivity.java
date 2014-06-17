package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
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
import at.ac.uibk.recipe.adapter.MyArrayAdapter;

public class SearchFoundActivity extends FragmentActivity implements
		OnClickListener {

	private MyArrayAdapter adapter = null;

	List<Recipe> data = null;
	ListView listView = null;

	String f = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_found);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			List<Recipe> serializable = ((List<Recipe>) extras
					.getSerializable("LIST_RECIPE"));

			f = (String) extras.getSerializable("FRIENDS");
			data = serializable;
		} else {
			Toast.makeText(SearchFoundActivity.this, "Error",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(SearchFoundActivity.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			finish();
		}

		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

		if (f != null) {
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

		} else {
			ImageButton home = (ImageButton) findViewById(R.id.home);
			home.setColorFilter(Color.WHITE);

			ImageButton search = (ImageButton) findViewById(R.id.searchTab);
			search.setColorFilter(Color.WHITE);
			search.setColorFilter(Color.rgb(41, 205, 255));
			search.setImageResource(R.drawable.ic_action_search2_selected);

			ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
			favorites.setColorFilter(Color.WHITE);

			ImageButton profile = (ImageButton) findViewById(R.id.profile);
			profile.setColorFilter(Color.WHITE);
		}

		// 2. Get ListView from activity_main.xml
		listView = (ListView) findViewById(R.id.mainListViewFound);

		// setContentView(rootView);

		// 1. pass context and data to the custom adapter
		adapter = new MyArrayAdapter(SearchFoundActivity.this,
				(ArrayList<Recipe>) data);

		// 3. setListAdapter
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(SearchFoundActivity.this,
						ShowRecipeActivity.class);
				Recipe recipe = adapter.getItem(position);

				intent.putExtra("SELECTED_RECIPE", recipe);
				intent.putExtra("WHICH_BEFORE", "All");
				startActivity(intent);

				// Toast.makeText(getActivity(), "test "+
				// adapter.getItem(position).toString(),
				// Toast.LENGTH_LONG).show();
			}

		});

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

			Toast.makeText(SearchFoundActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();
			Intent intent = new Intent(SearchFoundActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (f != null) {

			if (v.getId() == R.id.home) {

				Intent intent = new Intent(SearchFoundActivity.this,
						LoggedInActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			} else if (v.getId() == R.id.searchTab) {

				Intent intent = new Intent(SearchFoundActivity.this,
						SearchLoggedInActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			} else if (v.getId() == R.id.favorites) {

				Intent intent = new Intent(SearchFoundActivity.this,
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
				profile.setImageResource(R.drawable.ic_action_search2_selected);

			}

		} else {
			if (v.getId() == R.id.home) {

				Intent intent = new Intent(SearchFoundActivity.this,
						LoggedInActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			} else if (v.getId() == R.id.searchTab) {
				ImageButton home = (ImageButton) findViewById(R.id.home);
				home.setColorFilter(Color.WHITE, Mode.CLEAR);
				home.setColorFilter(Color.WHITE);

				ImageButton search = (ImageButton) findViewById(R.id.searchTab);
				search.setColorFilter(100, Mode.CLEAR);
				search.setColorFilter(Color.rgb(41, 205, 255));
				search.setImageResource(R.drawable.ic_action_search2_selected);

				ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
				favorites.setColorFilter(Color.WHITE, Mode.CLEAR);
				favorites.setColorFilter(Color.WHITE);

				ImageButton profile = (ImageButton) findViewById(R.id.profile);
				profile.setColorFilter(Color.WHITE, Mode.CLEAR);
				profile.setColorFilter(Color.WHITE);

			} else if (v.getId() == R.id.favorites) {

				Intent intent = new Intent(SearchFoundActivity.this,
						FavoritesActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			} else if (v.getId() == R.id.profile) {
				Intent intent = new Intent(SearchFoundActivity.this,
						ProfileActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			}
		}

	}
}
