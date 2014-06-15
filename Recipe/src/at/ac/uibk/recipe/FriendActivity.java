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
import android.util.Log;
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
import at.ac.uibk.recipe.adapter.MyArrayFriendAdapter;
import at.ac.uibk.recipe.api.RestApi;

public class FriendActivity extends Activity implements OnClickListener {

	private MyArrayFriendAdapter adapter = null;

	List<String> data = null;
	ListView listView = null;

	String which = null;
	UserFriends mUserFriends = null;
	ShowRecipeFriend sRf = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);

		mUserFriends = new UserFriends();
		mUserFriends.execute();

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
		listView = (ListView) findViewById(R.id.mainListViewFriend);

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

			Toast.makeText(FriendActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();
			Intent intent = new Intent(FriendActivity.this, MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {

			Intent intent = new Intent(FriendActivity.this,
					LoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.searchTab) {

			Intent intent = new Intent(FriendActivity.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.favorites) {

			Intent intent = new Intent(FriendActivity.this,
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

	public class UserFriends extends AsyncTask<Void, Void, Boolean> {

		List<String> result = null;

		@Override
		protected Boolean doInBackground(Void... urls) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(FriendActivity.this);

			String username = sharedPreferences.getString("username", "ab");
			List<String> o = null;
			if (username != "ab") {
				o = RestApi.getInstance().getFriends(username);
			}
			if (o != null) {
				result = o;
				return true;
			}
			return false;
		}

		protected void onPostExecute(final Boolean success) {
			mUserFriends = null;
			if (success) {

				if (result.size() != 0) {
					// 1. pass context and data to the custom adapter
					adapter = new MyArrayFriendAdapter(FriendActivity.this,
							(ArrayList<String>) result);

					data = result;

					// 3. setListAdapter
					listView.setAdapter(adapter);

					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							which = adapter.getItem(position);
							ShowRecipeFriend sRf = new ShowRecipeFriend();
							sRf.execute();
						}

					});
				} else {
					Toast.makeText(
							FriendActivity.this,
							"You have no friends up to now. You can get friends by clicking on the autor of a Recipe",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(FriendActivity.this,
							ProfileActivity.class);
					startActivity(intent);
					overridePendingTransition(0, 0);

				}
			}

		}
	}

	protected void onCancelled() {
		mUserFriends = null;
	}

	public class ShowRecipeFriend extends AsyncTask<Void, Void, Boolean> {
		private List<Recipe> result = null;

		protected Boolean doInBackground(Void... urls) {
			result = RestApi.getInstance().findRecipeByAutor(which);
			if (result != null || result.size() > 0)
				return true;
			return false;
		}

		protected void onPostExecute(final Boolean success) {
			if (success) {
				if (result.size() > 0) {
					Intent intent = new Intent(FriendActivity.this,
							SearchFoundActivity.class);
					intent.putExtra("LIST_RECIPE", (ArrayList<Recipe>) result);
					intent.putExtra("FRIENDS", "FRIENDS");
					startActivity(intent);
				} else {
					Toast.makeText(FriendActivity.this,
							"No Recipes found with autor " + which,
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(FriendActivity.this,
						"No Recipes found with autor " + which,
						Toast.LENGTH_SHORT).show();
			}

		}

		protected void onCancelled() {

		}
	}

}
