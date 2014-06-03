package at.ac.uibk.recipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import at.ac.uibk.Beans.User;
import at.ac.uibk.recipe.api.RestApi;

public class LoginActivity extends Activity {

	private static User loggedInUser = null;

	private String username = null;

	private String password = null;

	private EditText mUsernameView = null;
	private EditText mPasswordView = null;
	private View mLoginFormView = null;
	private View mLoginStatusView = null;
	private TextView mLoginStatusMessageView = null;

	UserLoginTask user = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		mUsernameView = (EditText) findViewById(R.id.username);

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == R.id.login
								|| actionId == EditorInfo.IME_NULL)
							attempLogin();
						return false;
					}
				});
		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attempLogin();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public UserLoginTask getUser() {
		return user;
	}

	public void setUser(UserLoginTask user) {
		this.user = user;
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
		return super.onOptionsItemSelected(item);
	}

	public static User getLoggedInUser() {
		return loggedInUser;
	}

	public static void setLoggedInUser(User loggedInUser) {
		LoginActivity.loggedInUser = loggedInUser;
	}

	/**
	 * onclick sends username/password to the server for authentication.
	 * 
	 * 
	 */
	public void attempLogin() {
		// if (mAuthTask != null) {
		// return;
		// }
		mUsernameView.setError(null);
		mPasswordView.setError(null);

		username = mUsernameView.getText().toString();
		password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (username.length() < 4) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);

			user = new UserLoginTask();

			user.execute();

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

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */

	private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		private User result;

		@Override
		protected Boolean doInBackground(Void... urls) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return null;
			}
			User o = RestApi.getInstance().login(username, password);

			if (o != null) {
				result = o;
				return true;
			} else {
				result = null;
				return false;
			}

		}

		public User getResult() {
			return result;
		}

		protected void onPostExecute(final Boolean success) {
			user = null;

			showProgress(false);

			if (success) {

				Intent intent = new Intent(LoginActivity.this,
						LoggedInActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

				loggedInUser = getResult();

				finish();

			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mUsernameView
						.setError(getString(R.string.error_incorrect_password));
				mUsernameView.requestFocus();
			}

		}

		protected void onCancelled() {
			user = null;
			showProgress(false);
		}
	}

}
