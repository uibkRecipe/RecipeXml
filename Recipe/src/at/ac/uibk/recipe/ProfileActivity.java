package at.ac.uibk.recipe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends FragmentActivity implements
		OnClickListener {

	private Button friends = null;
	private Button add = null;
	private Button delete = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

		ImageButton home = (ImageButton) findViewById(R.id.home);
		home.setColorFilter(Color.WHITE);

		ImageButton search = (ImageButton) findViewById(R.id.searchTab);
		search.setColorFilter(Color.WHITE);

		ImageButton favorites = (ImageButton) findViewById(R.id.favorites);
		favorites.setColorFilter(Color.WHITE);

		ImageButton profile = (ImageButton) findViewById(R.id.profile);
		profile.setColorFilter(Color.WHITE);
		profile.setColorFilter(Color.rgb(41, 205, 255));
		profile.setImageResource(R.drawable.ic_action_person_selected);

		TextView username = (TextView) findViewById(R.id.username);
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String name = sharedPreferences.getString("username", "ab");

		username.setText(name);

		String f = sharedPreferences.getString("foto", null);
		if (f != null) {

			byte[] foto = Base64.decode(f, Base64.DEFAULT);

			if (foto != null && foto.length > 0) {

				ImageView img = (ImageView) findViewById(R.id.user_image);
				Bitmap bm = BitmapFactory.decodeByteArray(foto, 0, foto.length);
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);

				img.setMinimumHeight(dm.heightPixels);
				img.setMinimumWidth(dm.widthPixels);
				img.setImageBitmap(bm);
			}
		}

		delete = (Button) findViewById(R.id.deleteRecipe);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(ProfileActivity.this,
						DeleteRecipeActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		friends = (Button) findViewById(R.id.friendsButton);
		friends.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileActivity.this,
						FriendActivity.class);
				startActivity(intent);
				overridePendingTransition(0, 0);
			}
		});

		add = (Button) findViewById(R.id.addRecipe);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProfileActivity.this,
						AddRecipe.class);
				startActivity(intent);
				overridePendingTransition(0, 0);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
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

			Toast.makeText(ProfileActivity.this, "Goodbye " + name,
					Toast.LENGTH_LONG).show();

			editor.clear();
			editor.commit();

			Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
			startActivity(intent);
			finish();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {

			Intent intent = new Intent(ProfileActivity.this,
					LoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.searchTab) {

			Intent intent = new Intent(ProfileActivity.this,
					SearchLoggedInActivity.class);
			startActivity(intent);
			overridePendingTransition(0, 0);

		} else if (v.getId() == R.id.favorites) {

			Intent intent = new Intent(ProfileActivity.this,
					FavoritesActivity.class);
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

}
