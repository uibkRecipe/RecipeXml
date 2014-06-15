package at.ac.uibk.recipe;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.adapter.MyArrayAdapter;
import at.ac.uibk.recipe.api.RestApi;

public class Co2NeutralFragment extends Fragment {

	private MyArrayAdapter adapter = null;

	List<Recipe> data = null;
	UserGetDataCo2neutral mUserGetDataCo2neutral = null;
	ListView listView = null;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.co2neutral_fragment,
				container, false);

		mUserGetDataCo2neutral = new UserGetDataCo2neutral();
		mUserGetDataCo2neutral.execute();

		// 2. Get ListView from activity_main.xml
		listView = (ListView) rootView.findViewById(R.id.mainListViewCo2);

		return rootView;
	}

	private class UserGetDataCo2neutral extends AsyncTask<Void, Void, Boolean> {
		private List<Recipe> result;

		@Override
		protected Boolean doInBackground(Void... urls) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(Co2NeutralFragment.this
							.getActivity());
			String name = sharedPreferences.getString("username", "ab");

			if (name.equals("ab"))
				return false;

			List<Recipe> o = RestApi.getInstance().getCO2FriendlyRec(name);
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
			mUserGetDataCo2neutral = null;
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
						intent.putExtra("WHICH_BEFORE", "Co2neutral");
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

			mUserGetDataCo2neutral = null;
		}
	}

}
