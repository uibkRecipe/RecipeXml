package at.ac.uibk.recipe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.R;

public class MyArrayAdapter extends ArrayAdapter<Recipe> {

	private final Context context;
	private final ArrayList<Recipe> items;

	public MyArrayAdapter(Context context, ArrayList<Recipe> items) {

		super(context, R.layout.simplerow_even, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = null;
		if (position % 2 == 0) {
			// 2. Get rowView from inflater
			rowView = inflater.inflate(R.layout.simplerow_even, parent, false);
		} else {
			rowView = inflater.inflate(R.layout.simplerow_odd, parent, false);
		}

		// 3. Get the two text view from the rowView
		TextView labelView = (TextView) rowView.findViewById(R.id.recipe_title);
		TextView valueView = (TextView) rowView
				.findViewById(R.id.recipe_description);

		// 4. Set the text for textView
		labelView.setText(items.get(position).getTitle());
		valueView.setText(items.get(position).getDescription());

		// 5. retrn rowView
		return rowView;
	}
}
