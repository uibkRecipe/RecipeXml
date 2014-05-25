package at.ac.uibk.recipe;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.FavoritesArrayAdapter;

public class FlourFragment extends Fragment {

	private FavoritesArrayAdapter adapter = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.flour_fragment, container,
				false);

		// setContentView(rootView);

		// 1. pass context and data to the custom adapter
		adapter = new FavoritesArrayAdapter(getActivity(), generateData());

		// 2. Get ListView from activity_main.xml
		ListView listView = (ListView) rootView
				.findViewById(R.id.mainListViewFlour);

		// 3. setListAdapter
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),
						ShowRecipeActivity.class);
				Recipe recipe = adapter.getItem(position);
				intent.putExtra("SELECTED_RECIPE", recipe);
				intent.putExtra("WHICH_BEFORE", "Flour");

				startActivity(intent);
			}

		});

		return rootView;
	}

	private ArrayList<Recipe> generateData() {
		ArrayList<Recipe> items = new ArrayList<Recipe>();
		items.add(new Recipe(1, "Pasta", "short description of recipe",
				"description", "Title", "preparation"));
		items.add(new Recipe(2, "Pasta", "short description of recipe",
				"description", "Title", "preparation"));
		items.add(new Recipe(3, "Pasta", "short description of recipe",
				"description", "Title", "preparation"));

		return items;
	}
}
