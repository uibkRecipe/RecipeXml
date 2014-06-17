package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import at.ac.uibk.Beans.IngredientType;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.api.RestApi;

public class SearchLoggedInActivity extends FragmentActivity implements
		OnClickListener {

	private SeekBar seekbar = null;
	private TextView textview = null;
	private EditText searchtitle = null;
	private Spinner spinner = null;

	private String category = null;
	private String title = null;
	private String autoOne = null;
	private String autoTwo = null;
	private String autoThree = null;
	private int idOne = -1;
	private int idTwo = -1;
	private int idThree = -1;

	private int time = 0;

	private TextView mSearchStatusMessageView = null;
	private View mSearchFormView = null;
	private View mSearchStatusView = null;

	private AutoCompleteTextView autoComplete1, autoComplete2,
			autoComplete3 = null;

	private UserIngredients mUserIngredients = null;
	private UserRecipesSearch mUserRecipesSearch = null;

	private List<IngredientType> ingredientsList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_logged_in);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String name = sharedPreferences.getString("username", "ab");
		if (name.equals("ab")) {
			Intent intent = new Intent(SearchLoggedInActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		}

		mUserIngredients = new UserIngredients();
		mUserIngredients.execute();

		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

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

		searchtitle = (EditText) findViewById(R.id.search_title);
		ColorStateList colors = searchtitle.getHintTextColors();
		textview = (TextView) findViewById(R.id.search_time_text);
		textview.setTextColor(colors);

		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categories, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		seekbar = (SeekBar) findViewById(R.id.seekbar);

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress += 10;
				textview.setText(progress + " min");
			}
		});

		searchtitle = (EditText) findViewById(R.id.search_title);

		mSearchFormView = findViewById(R.id.search_form);
		mSearchStatusView = findViewById(R.id.search_status);
		mSearchStatusMessageView = (TextView) findViewById(R.id.search_status_message);

		findViewById(R.id.searchlogged_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attemptSearch();
					}
				});

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

			Toast.makeText(SearchLoggedInActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();

			Intent intent = new Intent(SearchLoggedInActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {

			Intent intent = new Intent(SearchLoggedInActivity.this,
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

			Intent intent = new Intent(SearchLoggedInActivity.this,
					FavoritesActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.profile) {
			Intent intent = new Intent(SearchLoggedInActivity.this,
					ProfileActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		}

	}

	/**
	 * Shows the progress UI and hides the search form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSearchStatusView.setVisibility(View.VISIBLE);
			mSearchStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSearchStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mSearchFormView.setVisibility(View.VISIBLE);
			mSearchFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSearchFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mSearchStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public void attemptSearch() {

		category = spinner.getSelectedItem().toString();
		title = searchtitle.getText().toString();
		autoOne = autoComplete1.getText().toString();
		autoTwo = autoComplete2.getText().toString();
		autoThree = autoComplete3.getText().toString();
		time = seekbar.getProgress();

		for (IngredientType i : ingredientsList) {
			if (i.getName().equals(autoOne)) {
				idOne = i.getID();
			}
			if (i.getName().equals(autoTwo)) {
				idTwo = i.getID();
			}
			if (i.getName().equals(autoThree)) {
				idThree = i.getID();
			}
		}

		mSearchStatusMessageView.setText(R.string.search_progress_signing_in);

		showProgress(true);

		mUserRecipesSearch = new UserRecipesSearch();
		mUserRecipesSearch.execute();

	}

	public class UserIngredients extends AsyncTask<Void, Void, Boolean> {

		private String[] result = null;

		@Override
		protected Boolean doInBackground(Void... urls) {
			ingredientsList = RestApi.getInstance().getAllIngredientType();

			if (ingredientsList != null) {
				result = new String[ingredientsList.size()];
				int i = 0;
				for (IngredientType c : ingredientsList) {
					result[i] = c.getName();
					i++;
				}
				return true;
			} else {
				result = null;
				return false;
			}
		}

		public String[] getResult() {
			return result;
		}

		protected void onPostExecute(final Boolean success) {
			mUserIngredients = null;

			if (success) {

				// Get a reference to the AutoCompleteTextView in the layout
				autoComplete1 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients1);

				// Create the adapter and set it to the AutoCompleteTextView
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						SearchLoggedInActivity.this,
						android.R.layout.simple_list_item_1, getResult());
				autoComplete1.setAdapter(adapter);

				autoComplete2 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients2);
				autoComplete2.setAdapter(adapter);
				autoComplete2.setVisibility(View.GONE);

				autoComplete3 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients3);
				autoComplete3.setAdapter(adapter);
				autoComplete3.setVisibility(View.GONE);

				autoComplete1.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						autoComplete2.setVisibility(View.VISIBLE);
						autoComplete2.requestFocus();
					}
				});

				autoComplete2.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						autoComplete3.setVisibility(View.VISIBLE);
						autoComplete3.requestFocus();

					}
				});

			} else {

			}
		}

		protected void onCancelled() {
			mUserIngredients = null;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class UserRecipesSearch extends AsyncTask<Void, Void, Boolean> {

		private List<Recipe> result = null;
		private List<Recipe> temp = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			if (autoOne != null && autoOne.length() != 0) {
				if (autoTwo != null && autoTwo.length() != 0) {
					if (autoThree != null && autoThree.length() != 0) {
						temp = RestApi.getInstance().findRecipeByIngredient(
								idOne, idTwo, idThree);

					} else {

						temp = RestApi.getInstance().findRecipeByIngredient(
								idOne, idTwo);
					}
				} else {
					if (idOne != -1) {
						temp = RestApi.getInstance().findRecipeByIngredient(
								idOne);
					}
				}
			} else {
				temp = RestApi.getInstance().getAllRecipes();
			}

			if (temp != null) {
				return true;
			}

			return false;
		}

		public List<Recipe> getResult() {
			return result;
		}

		protected void onPostExecute(final Boolean success) {
			mUserRecipesSearch = null;
			Log.e("ABCD", "a asdasda");
			if (success) {
				result = new ArrayList<Recipe>();

				if (category.contains("All")) {
					if (title != null && title.length() != 0) {
						for (Recipe r : temp) {
							if (r.getName().contains(title)
									&& r.getTime() <= time) {
								result.add(r);
							}
						}
					} else {
						for (Recipe r : temp) {
							if (r.getTime() <= time) {
								result.add(r);
							}
						}
					}

				} else if (category.contains("Veg")) {
					if (title != null && title.length() != 0) {
						for (Recipe r : temp) {
							if (r.getName().contains(title)
									&& r.getCategory().contains("Veg")) {
								result.add(r);
							}
						}
					} else {
						for (Recipe r : temp) {
							if (r.getTime() <= time
									&& r.getCategory().contains("Veg")) {
								result.add(r);
							}
						}
					}

				} else if (category.contains("Meat")) {
					if (title != null && title.length() != 0) {
						for (Recipe r : temp) {
							if (r.getName().contains(title)
									&& r.getCategory().contains("Meat")) {
								result.add(r);
							}
						}

					} else {
						for (Recipe r : temp) {
							if (r.getTime() <= time
									&& r.getCategory().contains("Meat")) {
								result.add(r);
							}
						}
					}
				} else if (category.contains("Flour")) {
					if (title != null && title.length() != 0) {
						for (Recipe r : temp) {
							if (r.getName().contains(title)
									&& r.getCategory().contains("Flour")) {
								result.add(r);
							}
						}

					} else {
						for (Recipe r : temp) {
							if (r.getTime() <= time
									&& r.getCategory().contains("Flour")) {
								result.add(r);
							}
						}
					}
				}

				if (result.size() > 0) {
					showProgress(false);

					Intent intent = new Intent(SearchLoggedInActivity.this,
							SearchFoundActivity.class);
					intent.putExtra("LIST_RECIPE", (ArrayList<Recipe>) result);
					startActivity(intent);
				} else {
					showProgress(false);
					Toast.makeText(SearchLoggedInActivity.this,
							"No Recipes found", Toast.LENGTH_LONG).show();
				}
			} else {
				showProgress(false);
				Toast.makeText(SearchLoggedInActivity.this,
						"Sorry Error appeared while searching",
						Toast.LENGTH_LONG).show();
			}
		}

		protected void onCancelled() {
			mUserRecipesSearch = null;
		}
	}
}
