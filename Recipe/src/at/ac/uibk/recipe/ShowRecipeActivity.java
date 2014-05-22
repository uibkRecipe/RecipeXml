package at.ac.uibk.recipe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import at.ac.uibk.Beans.Recipe;

public class ShowRecipeActivity extends Activity {

	private Recipe recipe = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_recipe);

		Intent i = getIntent();
		recipe = (Recipe) i.getSerializableExtra("SELECTED_RECIPE");
		String which = i.getExtras().getString("WHICH_BEFORE");

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
		
		
		TextView titleView = (TextView)findViewById(R.id.show_recipe_title);
		titleView.setText(recipe.getTitle());

		TextView ingredientsView = (TextView)findViewById(R.id.show_recipe_ingredient);
		ingredientsView.setText(recipe.getIngredients());

		TextView preparationView = (TextView)findViewById(R.id.show_recipe_preparation);
		preparationView.setText(recipe.getPreparation());


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

}
