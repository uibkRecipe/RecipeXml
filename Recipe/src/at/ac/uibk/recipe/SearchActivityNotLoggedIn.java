package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.MyArrayAdapterNot;

public class SearchActivityNotLoggedIn extends FragmentActivity implements
		OnClickListener {

	ListView listView = null;

	MyArrayAdapterNot adapter = null;

	List<Recipe> data = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			List<Recipe> serializable = ((List<Recipe>) extras
					.getSerializable("LIST_RECIPE"));

			data = serializable;
		} else {
			Toast.makeText(SearchActivityNotLoggedIn.this, "Error",
					Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(SearchActivityNotLoggedIn.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		}

		// 2. Get ListView from activity_main.xml
		listView = (ListView) findViewById(R.id.mainListViewFoundNot);

		// setContentView(rootView);

		// 1. pass context and data to the custom adapter
		adapter = new MyArrayAdapterNot(SearchActivityNotLoggedIn.this,
				(ArrayList<Recipe>) data);

		// 3. setListAdapter
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent intent = new Intent(SearchActivityNotLoggedIn.this,
						ShowRecipeNotActivity.class);
				Recipe recipe = adapter.getItem(position);

				intent.putExtra("SELECTED_RECIPE", recipe);

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

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View v) {

	}
}
