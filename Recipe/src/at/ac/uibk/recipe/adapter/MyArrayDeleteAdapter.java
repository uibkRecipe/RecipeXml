package at.ac.uibk.recipe.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.recipe.R;

public class MyArrayDeleteAdapter extends ArrayAdapter<Recipe> {

	private final Context context;
	private final ArrayList<Recipe> items;

	public MyArrayDeleteAdapter(Context context, ArrayList<Recipe> items) {

		super(context, R.layout.favorites_simplerow, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.delete_simplerow, parent,
				false);

		// 3. Get the two text view from the rowView
		TextView labelView = (TextView) rowView.findViewById(R.id.recipe_title);
		// TextView valueView = (TextView) rowView
		// .findViewById(R.id.recipe_description);

		// 4. Set the text for textView
		labelView.setText(items.get(position).getName());
		// valueView.setText(items.get(position).getDescription());

		// 5. retrn rowView
		return rowView;
	}
}
