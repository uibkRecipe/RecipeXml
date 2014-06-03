package at.ac.uibk.recipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SearchVegetarianFragment extends Fragment {

	private SeekBar seekbar = null;
	private TextView textview = null;

	private TextView mSearchStatusMessageView = null;
	private View mSearchFormView = null;
	private View mSearchStatusView = null;
	
	private AutoCompleteTextView autoComplete1, autoComplete2,
	autoComplete3 = null;


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_vegetarian_fragment,
				container, false);

		EditText editText = (EditText) rootView.findViewById(R.id.search_title);
		ColorStateList colors = editText.getHintTextColors();
		textview = (TextView) rootView.findViewById(R.id.search_time_text);
		textview.setTextColor(colors);

		seekbar = (SeekBar) rootView.findViewById(R.id.seekbar);

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress += 10;
				textview.setText(progress + " min");
			}
		});

		editText = (EditText) rootView.findViewById(R.id.search_title);

		mSearchFormView = rootView.findViewById(R.id.search_form);
		mSearchStatusView = rootView.findViewById(R.id.search_status);
		mSearchStatusMessageView = (TextView) rootView
				.findViewById(R.id.search_status_message);

		rootView.findViewById(R.id.search_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attemptSearch();
					}
				});

		// Get a reference to the AutoCompleteTextView in the layout
		autoComplete1 = (AutoCompleteTextView) rootView
				.findViewById(R.id.autocomplete_ingredients1);
		// Get the string array
		String[] ingredients = getResources().getStringArray(
				R.array.ingredients_array);

		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, ingredients);
		autoComplete1.setAdapter(adapter);

		autoComplete2 = (AutoCompleteTextView) rootView
				.findViewById(R.id.autocomplete_ingredients2);
		autoComplete2.setVisibility(View.GONE);

		autoComplete3 = (AutoCompleteTextView) rootView
				.findViewById(R.id.autocomplete_ingredients3);
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

		return rootView;
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

		mSearchStatusMessageView.setText(R.string.search_progress_signing_in);
		showProgress(true);

		showProgress(false);
		Intent intent = new Intent(getActivity(), SearchFoundActivity.class);
		startActivity(intent);
		// finish();

	}
}
