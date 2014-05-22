package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.MyArrayAdapter;

public class AllFragment extends Fragment {

	private MyArrayAdapter adapter = null;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.all_fragment, container,
				false);

		// setContentView(rootView);

		// 1. pass context and data to the custom adapter
		 adapter = new MyArrayAdapter(getActivity(),
				generateData());

		// 2. Get ListView from activity_main.xml
		ListView listView = (ListView) rootView
				.findViewById(R.id.mainListViewAll);

		// 3. setListAdapter
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				
				Intent intent = new Intent(getActivity(), ShowRecipeActivity.class);
				Recipe recipe = adapter.getItem(position);
				intent.putExtra("SELECTED_RECIPE", recipe);
				intent.putExtra("WHICH_BEFORE", "All");
				startActivity(intent);
				
				
			//	Toast.makeText(getActivity(), "test "+ adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
			}

		});

		return rootView;
	}

	private ArrayList<Recipe> generateData() {
		ArrayList<Recipe> items = new ArrayList<Recipe>();
		items.add(new Recipe(1,"Pasta", "short description of recipe", "description",
				"Title","preparation"));
		items.add(new Recipe(2,"Pasta", "short description of recipe", "description",
				"Title","preparation"));
		items.add(new Recipe(3,"Pasta", "short description of recipe", "description",
				"Title","preparation"));

		return items;
	}

}
