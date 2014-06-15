package at.ac.uibk.recipe;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import at.ac.uibk.Beans.IngredientType;
import at.ac.uibk.Beans.Rating;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.Beans.RecipeIngredients;
import at.ac.uibk.recipe.api.RestApi;

public class ShowRecipeActivity extends Activity implements OnClickListener {

	private Recipe recipe = null;

	private Button rate = null;
	private Button cooked = null;
	private Button favorite = null;

	private RatingBar rating = null;

	private UserCo2 mUserCo2 = null;
	private UserRating mUserRating = null;
	private UserCooked mUserCooked = null;
	private UserFavorite mUserFavorite = null;
	private Rating ratinga = null;
	private TextView autor = null;
	private UserAddFriend mUserAddFriend = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_recipe);

		Intent i = getIntent();
		recipe = (Recipe) i.getSerializableExtra("SELECTED_RECIPE");
		String which = i.getExtras().getString("WHICH_BEFORE");

		mUserCo2 = new UserCo2();
		mUserCo2.execute();

		if (recipe == null) {
			if (which == "All") {
				Intent intent = new Intent(ShowRecipeActivity.this,
						AllFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			} else if (which == "Co2") {
				Intent intent = new Intent(ShowRecipeActivity.this,
						Co2NeutralFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			} else if (which == "Vegetarian") {
				Intent intent = new Intent(ShowRecipeActivity.this,
						VegetarianFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			} else if (which == "Meat") {
				Intent intent = new Intent(ShowRecipeActivity.this,
						MeatFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			} else if (which == "Flour") {
				Intent intent = new Intent(ShowRecipeActivity.this,
						FlourFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			} else {
				Intent intent = new Intent(ShowRecipeActivity.this,
						AllFragment.class);
				Toast.makeText(ShowRecipeActivity.this,
						"ERROR while selecting Recipe", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_recipe, menu);
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

			Toast.makeText(ShowRecipeActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();

			Intent intent = new Intent(ShowRecipeActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, LoggedInActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);

		finish();
	}

	private class UserCo2 extends AsyncTask<Void, Void, Boolean> {
		private double res = -1;
		private RecipeIngredients resIng = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(ShowRecipeActivity.this);
			String username = sharedPreferences.getString("username", "ab");
			if (username.equals("ab"))
				return false;
			Recipe o = RestApi.getInstance().calculateCO2(recipe.getID(),
					username);
			RecipeIngredients ri = RestApi.getInstance().getIngredients(
					recipe.getID());

			if (o == null && ri == null) {
				return false;
			}
			resIng = ri;
			res = o.getDistance();
			return true;

		}

		public double getResult() {
			return Math.round(100.0 * res) / 100.0;
		}

		public RecipeIngredients getResIng() {
			return resIng;
		}

		protected void onPostExecute(final Boolean success) {

			mUserCo2 = null;

			if (success) {

				if (recipe.getFoto() != null && recipe.getFoto().length > 0) {

					ImageView img = (ImageView) findViewById(R.id.show_recipe_image);
					Bitmap bm = BitmapFactory.decodeByteArray(recipe.getFoto(),
							0, recipe.getFoto().length);
					DisplayMetrics dm = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dm);

					img.setMinimumHeight(dm.heightPixels);
					img.setMinimumWidth(dm.widthPixels);
					img.setImageBitmap(bm);
				}

				TextView titleView = (TextView) findViewById(R.id.show_recipe_title);
				titleView.setText(recipe.getName());

				TextView ingredientsView = (TextView) findViewById(R.id.show_recipe_ingredient);

				List<String> quantities = resIng.getQuantities();
				List<IngredientType> ingredients = resIng.getIngredients();

				String re = " ";
				for (int i = 0; i < quantities.size(); i++) {
					re += quantities.get(i).toString() + " "
							+ ingredients.get(i).getName() + "\n";
				}

				ingredientsView.setText(re);

				TextView preparationView = (TextView) findViewById(R.id.show_recipe_preparation);
				preparationView.setText(recipe.getPreparation());

				TextView show_recipe_timeView = (TextView) findViewById(R.id.show_recipe_time);
				show_recipe_timeView.setText(recipe.getTime() + " min");

				TextView show_recipe_cookedtimeView = (TextView) findViewById(R.id.show_recipe_cookedtime);
				show_recipe_cookedtimeView.setText(recipe.getCooked()
						+ " times");

				TextView show_co2valueView = (TextView) findViewById(R.id.show_co2value);
				show_co2valueView.setText(getResult() + " km");

				autor = (TextView) findViewById(R.id.autor);
				SpannableString content = new SpannableString("autor: "
						+ recipe.getAutor());
				content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
				autor.setText(content);

				autor.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						mUserAddFriend = new UserAddFriend();
						mUserAddFriend.execute();

					}
				});

				RatingBar ratingBarAvg = (RatingBar) findViewById(R.id.ratingBarAvg);
				ratingBarAvg.setRating(recipe.getAverageRating());
				ratingBarAvg.setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
				});
				ratingBarAvg.setFocusable(false);

				rate = (Button) findViewById(R.id.btnSubmit);
				rate.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						rating = (RatingBar) findViewById(R.id.ratingBar);
						SharedPreferences sharedPreferences = PreferenceManager
								.getDefaultSharedPreferences(ShowRecipeActivity.this);
						String username = sharedPreferences.getString(
								"username", "ab");
						if (username.equals("ab"))
							return;

						ratinga = new Rating(recipe.getID(), username,
								(int) rating.getRating());

						mUserRating = new UserRating();
						mUserRating.execute();

						rating.setVisibility(View.GONE);
						v.setVisibility(View.GONE);
					}
				});

				cooked = (Button) findViewById(R.id.cooked);
				cooked.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(),
								"You selected the Recipe as cooked",
								Toast.LENGTH_LONG).show();

						mUserCooked = new UserCooked();
						mUserCooked.execute();

						v.setVisibility(View.GONE);
					}
				});

				favorite = (Button) findViewById(R.id.favorites);
				favorite.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getApplicationContext(),
								"You added the Recipe to your Favorites",
								Toast.LENGTH_LONG).show();

						mUserFavorite = new UserFavorite();
						mUserFavorite.execute();

						v.setVisibility(View.GONE);
					}
				});

			}

		}

		protected void onCancelled() {
			mUserCo2 = null;
		}
	}

	private class UserRating extends AsyncTask<Void, Void, Boolean> {
		Recipe res = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			boolean t = false;
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(ShowRecipeActivity.this);
			String username = sharedPreferences.getString("username", "ab");
			if (username.equals("ab"))
				return false;
			Recipe o = RestApi.getInstance().calculateCO2(recipe.getID(),
					username);

			if (o == null) {
				t = false;
			} else {
				t = true;
			}
			if (t == true) {
				res = o;
				return RestApi.getInstance().addRating(ratinga);
			} else {
				return false;
			}

		}

		public Recipe getResult() {
			return res;
		}

		protected void onPostExecute(final Boolean success) {

			RatingBar ratingBarAvg = (RatingBar) findViewById(R.id.ratingBarAvg);
			ratingBarAvg.setRating(getResult().getAverageRating());
			ratingBarAvg.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					return true;
				}
			});
			ratingBarAvg.setFocusable(false);

			mUserRating = null;

		}
	}

	private class UserCooked extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... urls) {

			return RestApi.getInstance().addCooked(recipe.getID());
			// return true;
		}

		protected void onPostExecute(final Boolean success) {

			mUserCooked = null;

		}
	}

	private class UserFavorite extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... urls) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(ShowRecipeActivity.this);
			String username = sharedPreferences.getString("username", "ab");
			if (username != "ab")
				return RestApi.getInstance().addFavoriteRecipe(recipe.getID(),
						username);
			return false;
		}

		protected void onPostExecute(final Boolean success) {

			mUserFavorite = null;
			if (!success) {
				Toast.makeText(ShowRecipeActivity.this,
						"Error adding to Favorites", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class UserAddFriend extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... urls) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(ShowRecipeActivity.this);
			String username = sharedPreferences.getString("username", "ab");
			String name = autor.getText().toString();
			name = name.substring(7);
			Log.e("ABCD",username +" "+ name);
			if (username != "ab")
				return RestApi.getInstance().addFriend(username, name);

			return false;
		}

		protected void onPostExecute(final Boolean success) {

			mUserAddFriend = null;
			if (success) {
				Toast.makeText(ShowRecipeActivity.this,
						"Friend was added to your List", Toast.LENGTH_SHORT)
						.show();
			}else{
				Toast.makeText(ShowRecipeActivity.this,
						"Error while adding friend", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}
}
