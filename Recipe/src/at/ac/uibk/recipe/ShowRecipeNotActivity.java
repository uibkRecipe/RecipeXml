package at.ac.uibk.recipe;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import at.ac.uibk.Beans.IngredientType;
import at.ac.uibk.Beans.Rating;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.Beans.RecipeIngredients;
import at.ac.uibk.recipe.api.RestApi;

public class ShowRecipeNotActivity extends Activity implements OnClickListener {

	private Recipe recipe = null;

	private UserCo2 mUserCo2 = null;
	private Rating ratinga = null;
	private TextView autor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_recipe_not);

		Intent i = getIntent();
		recipe = (Recipe) i.getSerializableExtra("SELECTED_RECIPE");

		mUserCo2 = new UserCo2();
		mUserCo2.execute();

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

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBackPressed() {

		finish();
	}

	private class UserCo2 extends AsyncTask<Void, Void, Boolean> {
		private double res = -1;
		private RecipeIngredients resIng = null;

		@Override
		protected Boolean doInBackground(Void... urls) {

			RecipeIngredients ri = RestApi.getInstance().getIngredients(
					recipe.getID());

			if (ri == null) {
				return false;
			}
			resIng = ri;
			return true;

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

			
				RatingBar ratingBarAvg = (RatingBar) findViewById(R.id.ratingBarAvg);
				ratingBarAvg.setRating(recipe.getAverageRating());
				ratingBarAvg.setOnTouchListener(new OnTouchListener() {
					public boolean onTouch(View v, MotionEvent event) {
						return true;
					}
				});
				ratingBarAvg.setFocusable(false);

				

			}

		}

		protected void onCancelled() {
			mUserCo2 = null;
		}
	}


}
