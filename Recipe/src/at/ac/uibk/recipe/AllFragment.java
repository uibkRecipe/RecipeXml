package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.Arrays;

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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.all_fragment, container,
				false);

		// setContentView(rootView);

		// 1. pass context and data to the custom adapter
		MyArrayAdapter adapter = new MyArrayAdapter(getActivity(),
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
				Toast.makeText(getActivity(),
						"Click ListItem Number " + position, Toast.LENGTH_LONG)
						.show();
			}

		});

		return rootView;
	}

	private ArrayList<Recipe> generateData() {
		ArrayList<Recipe> items = new ArrayList<Recipe>();
		items.add(new Recipe("Pasta", "Pasta description", "description",
				"Title"));
		items.add(new Recipe("Pasta", "Pasta description", "description",
				"Title"));
		items.add(new Recipe("Pasta", "Pasta description", "description",
				"Title"));

		return items;
	}

}
