package at.ac.uibk.recipe;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import at.ac.uibk.Beans.IngredientType;
import at.ac.uibk.Beans.Recipe;
import at.ac.uibk.Beans.RecipeIngredients;
import at.ac.uibk.recipe.SearchLoggedInActivity.UserIngredients;
import at.ac.uibk.recipe.api.RestApi;

public class AddRecipe extends Activity implements OnClickListener {

	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath;
	private String filemanagerstring;

	private TextView tv = null;
	private TextView textview = null;
	private EditText nameAdd = null;

	private List<IngredientType> ingredientsList = null;

	private UserIngredients mUserIngredients = null;

	private byte[] foto = null;

	private EditText titleEdit = null;
	private EditText subtitleEdit = null;
	private EditText preparationEdit = null;
	private SeekBar seekbar = null;
	private Spinner spinner = null;

	private String title = null;
	private String subtitle = null;
	private String preparation = null;
	private int time = -1;
	private String cat = null;

	private View mAddFormView = null;
	private View mAddStatusView = null;
	private TextView mAddStatusMessageView = null;

	private AddRecipeTask mAuthTask = null;
//	private AutoCompleteTextView autoComplete, autoComplete1, autoComplete2,
//			autoComplete3, autoComplete4, autoComplete5 = null;
//	private EditText quantity, quantity1, quantity2, quantity3, quantity4,
//			quantity5 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);

//		mUserIngredients = new UserIngredients();
//		mUserIngredients.execute();

		nameAdd = (EditText) findViewById(R.id.nameAdd);
		ColorStateList colors = nameAdd.getHintTextColors();
		tv = (TextView) findViewById(R.id.add_time_text);
		tv.setTextColor(colors);

//		quantity = (EditText) findViewById(R.id.quantity);
//		quantity1 = (EditText) findViewById(R.id.quantity1);
//		quantity2 = (EditText) findViewById(R.id.quantity2);
//		quantity3 = (EditText) findViewById(R.id.quantity3);
//		quantity4 = (EditText) findViewById(R.id.quantity4);
//		quantity5 = (EditText) findViewById(R.id.quantity5);

		spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.categoriesWithout,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		textview = (TextView) findViewById(R.id.selected_image);
		seekbar = (SeekBar) findViewById(R.id.seekbar);

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress += 10;
				tv.setText(progress + " min");
			}
		});

		((Button) findViewById(R.id.select_image))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {

						// in onCreate or any event where your want the user to
						// select a file
						Intent intent = new Intent();
						intent.setType("image/*");
						intent.setAction(Intent.ACTION_GET_CONTENT);
						startActivityForResult(
								Intent.createChooser(intent, "Select Picture"),
								SELECT_PICTURE);

					}
				});

		mAddFormView = findViewById(R.id.add_form);
		mAddStatusView = findViewById(R.id.add_status);
		mAddStatusMessageView = (TextView) findViewById(R.id.add_status_message);
		findViewById(R.id.Add_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attempAdd();
						;
					}
				});

		titleEdit = (EditText) findViewById(R.id.nameAdd);
		subtitleEdit = (EditText) findViewById(R.id.subtitleAdd);
		preparationEdit = (EditText) findViewById(R.id.preparationAdd);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		spinner = (Spinner) findViewById(R.id.spinner);

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

		if (id == R.id.action_logout) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(this);
			String name = sharedPreferences.getString("username", "ab");

			Editor editor = sharedPreferences.edit();

			Toast.makeText(AddRecipe.this, "Goodbye " + name, Toast.LENGTH_LONG)
					.show();

			editor.clear();
			editor.commit();
			Intent intent = new Intent(AddRecipe.this, MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {

			Intent intent = new Intent(AddRecipe.this, LoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.searchTab) {

			Intent intent = new Intent(AddRecipe.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.favorites) {

			Intent intent = new Intent(AddRecipe.this, FavoritesActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.profile) {
			ImageButton home = (ImageButton) findViewById(R.id.home);
			home.setColorFilter(Color.WHITE, Mode.CLEAR);
			home.setColorFilter(Color.WHITE);

			ImageButton search = (ImageButton) findViewById(R.id.searchTab);
			search.setColorFilter(Color.WHITE, Mode.CLEAR);
			search.setColorFilter(Color.WHITE);

			ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
			favorites.setColorFilter(Color.WHITE, Mode.CLEAR);
			favorites.setColorFilter(Color.WHITE);

			ImageButton profile = (ImageButton) findViewById(R.id.profile);
			profile.setColorFilter(100, Mode.CLEAR);
			profile.setColorFilter(Color.rgb(41, 205, 255));
			profile.setImageResource(R.drawable.ic_action_person_selected);

		}

	}

	public void attempAdd() {

		titleEdit.setError(null);
		subtitleEdit.setError(null);
		preparationEdit.setError(null);

		title = titleEdit.getText().toString();
		subtitle = subtitleEdit.getText().toString();
		time = seekbar.getProgress() + 10;
		preparation = preparationEdit.getText().toString();
		cat = spinner.getSelectedItem().toString();

		boolean cancel = false;
		View focusView = null;
		if (TextUtils.isEmpty(title)) {
			titleEdit.setError("Title cant be empty");
			focusView = titleEdit;
			cancel = true;
		}

		if (TextUtils.isEmpty(subtitle)) {
			subtitleEdit.setError("Subtitle cant be empty");
			focusView = subtitleEdit;
			cancel = true;
		}

		if (TextUtils.isEmpty(preparation)) {
			preparationEdit.setError("Preparation cant be empty");
			focusView = titleEdit;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {

			mAddStatusMessageView.setText(R.string.add_progress_signing_in);
			showProgress(true);
			mAuthTask = new AddRecipeTask();
			mAuthTask.execute();

		}

	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mAddStatusView.setVisibility(View.VISIBLE);
			mAddStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mAddStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mAddFormView.setVisibility(View.VISIBLE);
			mAddFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mAddFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mAddStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mAddFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				// OI FILE Manager
				filemanagerstring = selectedImageUri.getPath();

				// MEDIA GALLERY
				selectedImagePath = getPath(selectedImageUri);

				// DEBUG PURPOSE - you can delete this if you want
				if (selectedImagePath != null)
					Log.i("ABCD", selectedImagePath);
				else
					Log.i("ABCD", "selectedImagePath is null");
				if (filemanagerstring != null)
					Log.i("ABCD", filemanagerstring);
				else
					Log.i("ABCD", "filemanagerstring is null");

				Bitmap myBitmap = null;
				// NOW WE HAVE OUR WANTED STRING
				if (selectedImagePath != null) {

					textview.setText(selectedImagePath + " ");
					InputStream is;

					try {
						is = this.getContentResolver().openInputStream(
								selectedImageUri);
						myBitmap = BitmapFactory.decodeStream(is);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();

					myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
					myBitmap.recycle();
					foto = stream.toByteArray();

					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					textview.setText(filemanagerstring + " ");

					InputStream is;
					try {
						is = this.getContentResolver().openInputStream(
								selectedImageUri);
						myBitmap = BitmapFactory.decodeStream(is);

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ByteArrayOutputStream stream = new ByteArrayOutputStream();

					myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
					myBitmap.recycle();

					foto = stream.toByteArray();

					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					Log.i("ABCD", "filemanagerstring is the right one for you!");
				}

			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public class AddRecipeTask extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Void... urls) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(AddRecipe.this);
			String name = sharedPreferences.getString("username", "ab");
			if (name.equals("ab"))
				return false;

			Recipe r = new Recipe(name, title, subtitle, time, preparation, cat);

			if (foto != null) {
				r.setFoto(foto);
			}

			return RestApi.getInstance().addRecipe(r);

//			List<Recipe> l = RestApi.getInstance().findRecipeByCategory(cat);
//			Recipe a = null;
//			for (Recipe ra : l) {
//				if (ra.getAutor().equals(name) && ra.getName().equals(time)) {
//					a = ra;
//				}
//			}
//			RecipeIngredients rI = null;
//			if (autoComplete.getText().toString() != null
//					&& quantity.getText().toString() != null) {
//				rI = new RecipeIngredients();
//				IngredientType i = new IngredientType(autoComplete.getText()
//						.toString());
//				rI.addIngredient(quantity.getText().toString(), i);
//			}
//			boolean temp2 = false;
//			if (rI != null)
//				temp2 = RestApi.getInstance().addIngredientToRecipe(a.getID(),
//						rI);
//
//			return true;
		}

		protected void onPostExecute(final Boolean success) {
			showProgress(false);
			if (success) {
				Toast.makeText(AddRecipe.this,
						"You successfully added a Recipe", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(AddRecipe.this,
						ProfileActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(AddRecipe.this, "Error Adding Recipe",
						Toast.LENGTH_SHORT).show();
				showProgress(false);

			}
		}

		protected void onCancelled() {
		}
	}

//	public class UserIngredients extends AsyncTask<Void, Void, Boolean> {
//
//		private String[] result = null;
//
//		@Override
//		protected Boolean doInBackground(Void... urls) {
//			ingredientsList = RestApi.getInstance().getAllIngredientType();
//
//			if (ingredientsList != null) {
//				result = new String[ingredientsList.size()];
//				int i = 0;
//				for (IngredientType c : ingredientsList) {
//					result[i] = c.getName();
//					i++;
//				}
//				return true;
//			} else {
//				result = null;
//				return false;
//			}
//		}
//
//		public String[] getResult() {
//			return result;
//		}
//
//		protected void onPostExecute(final Boolean success) {
//			mUserIngredients = null;
//
//			if (success) {
//
//				// Get a reference to the AutoCompleteTextView in the layout
//				autoComplete = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients);
//
//				// Create the adapter and set it to the AutoCompleteTextView
//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//						AddRecipe.this, android.R.layout.simple_list_item_1,
//						getResult());
//				autoComplete.setAdapter(adapter);
//
//				autoComplete1 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients1);
//				autoComplete1.setAdapter(adapter);
//				autoComplete2 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients2);
//				autoComplete2.setAdapter(adapter);
//				autoComplete3 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients3);
//				autoComplete3.setAdapter(adapter);
//				autoComplete4 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients4);
//				autoComplete4.setAdapter(adapter);
//				autoComplete5 = (AutoCompleteTextView) findViewById(R.id.autocomplete_ingredients5);
//				autoComplete5.setAdapter(adapter);
//
//			} else {
//
//			}
//		}
//
//		protected void onCancelled() {
//			mUserIngredients = null;
//		}
//	}
}
