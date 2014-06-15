package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.MyArrayAdapter;
import at.ac.uibk.recipe.api.RestApi;

public class VegetarianFragment extends Fragment {

	private MyArrayAdapter adapter = null;

	List<Recipe> data = null;
	UserGetDataVegetarian mUserGetDataVegetarian = null;
	ListView listView = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.vegetarian_fragment,
				container, false);

		mUserGetDataVegetarian = new UserGetDataVegetarian();
		mUserGetDataVegetarian.execute();

		// 2. Get ListView from activity_main.xml
		listView = (ListView) rootView
				.findViewById(R.id.mainListViewVegetarian);

		return rootView;
	}

	private class UserGetDataVegetarian extends AsyncTask<Void, Void, Boolean> {
		private List<Recipe> result;

		@Override
		protected Boolean doInBackground(Void... urls) {

			List<Recipe> o = RestApi.getInstance().findRecipeByCategory(
					"Vegetarian");

			if (o != null) {
				result = o;
				return true;
			} else {
				result = null;
				return false;
			}

		}

		public List<Recipe> getResult() {
			return result;
		}

		protected void onPostExecute(final Boolean success) {
			mUserGetDataVegetarian = null;
			if (success) {

				// setContentView(rootView);

				// 1. pass context and data to the custom adapter
				adapter = new MyArrayAdapter(getActivity(),
						(ArrayList<Recipe>) getResult());

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
						intent.putExtra("WHICH_BEFORE", "Vegetarian");
						startActivity(intent);

						// Toast.makeText(getActivity(), "test "+
						// adapter.getItem(position).toString(),
						// Toast.LENGTH_LONG).show();
					}

				});

			} else {
				// I dont care
			}

		}

		protected void onCancelled() {

			mUserGetDataVegetarian = null;
		}
	}

}
