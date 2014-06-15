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

public class MyArrayFriendAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final ArrayList<String> items;

	public MyArrayFriendAdapter(Context context, ArrayList<String> items) {

		super(context, R.layout.friends_simplerow, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// 1. Create inflater
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.friends_simplerow, parent,
				false);

		// 3. Get the two text view from the rowView
		TextView labelView = (TextView) rowView.findViewById(R.id.name_friend);
		// TextView valueView = (TextView) rowView
		// .findViewById(R.id.recipe_description);

		// 4. Set the text for textView
		labelView.setText(items.get(position).toString());
		// valueView.setText(items.get(position).getDescription());

		// 5. return rowView
		return rowView;
	}
}
