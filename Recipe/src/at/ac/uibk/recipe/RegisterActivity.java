package at.ac.uibk.recipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
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

public class RegisterActivity extends Activity {

	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"honspeator:aaaaa:aaaaa:hannes.vieider@gmail.com:hannes:vieider",
			"aaaaa:aaaaa:aaaaa:aaaaa:aaaaa:aaaaa" };

	private UserRegisterTask mAuthTask = null;

	private String username = null;
	private String password = null;
	private String passwordRepeat = null;
	private String email = null;
	private String firstname = null;
	private String lastname = null;

	private EditText mUsernameView = null;
	private EditText mPasswordView = null;
	private EditText mPasswordRepeatView = null;
	private EditText mEmailView = null;
	private EditText mFirstnameView = null;
	private EditText mLastnameView = null;

	private View mRegisterFormView = null;
	private View mRegisterStatusView = null;
	private TextView mRegisterStatusMessageView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mUsernameView = (EditText) findViewById(R.id.usernameRegister);
		mPasswordView = (EditText) findViewById(R.id.passwordRegister);
		mPasswordRepeatView = (EditText) findViewById(R.id.passwordRegister2);
		mEmailView = (EditText) findViewById(R.id.emailRegister);
		mFirstnameView = (EditText) findViewById(R.id.firstnameRegister);
		mLastnameView = (EditText) findViewById(R.id.lastnameRegister);

		mLastnameView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == R.id.register
								|| actionId == EditorInfo.IME_NULL)
							attempRegister();
						return false;
					}
				});

		mRegisterFormView = findViewById(R.id.register_form);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);
		findViewById(R.id.register_button).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						attempRegister();
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
		return super.onOptionsItemSelected(item);
	}

	public void attempRegister() {
		if (mAuthTask != null) {
			return;
		}
		mUsernameView.setError(null);
		mPasswordView.setError(null);
		mPasswordRepeatView.setError(null);
		mEmailView.setError(null);
		mFirstnameView.setError(null);
		mLastnameView.setError(null);

		username = mUsernameView.getText().toString();
		password = mPasswordView.getText().toString();
		passwordRepeat = mPasswordRepeatView.getText().toString();
		email = mEmailView.getText().toString();
		firstname = mFirstnameView.getText().toString();
		lastname = mLastnameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		} else if (username.length() < 4) {
			mUsernameView.setError(getString(R.string.error_invalid_username));
			focusView = mUsernameView;
			cancel = true;
		}

		if (TextUtils.isEmpty(password)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		if (TextUtils.isEmpty(passwordRepeat)) {
			mPasswordRepeatView
					.setError(getString(R.string.error_field_required));
			focusView = mPasswordRepeatView;
			cancel = true;
		} else if (password.length() < 4) {
			mPasswordRepeatView
					.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordRepeatView;
			cancel = true;
		} else if (!password.equals(passwordRepeat)) {
			mPasswordRepeatView
					.setError(getString(R.string.error_password_not_same));
			focusView = mPasswordRepeatView;
			cancel = true;
		}

		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!email.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (TextUtils.isEmpty(firstname)) {
			mFirstnameView.setError(getString(R.string.error_field_required));
			focusView = mFirstnameView;
			cancel = true;
		} else if (firstname.length() < 4) {
			mFirstnameView.setError(getString(R.string.error_field_name));
			focusView = mFirstnameView;
			cancel = true;
		}

		if (TextUtils.isEmpty(lastname)) {
			mLastnameView.setError(getString(R.string.error_field_required));
			focusView = mLastnameView;
			cancel = true;
		} else if (lastname.length() < 4) {
			mLastnameView.setError(getString(R.string.error_field_name));
			focusView = mLastnameView;
			cancel = true;
		}

		if (cancel) {
			focusView.requestFocus();
		} else {
			mRegisterStatusMessageView
					.setText(R.string.register_progress_signing_in);
			showProgress(true);
			mAuthTask = new UserRegisterTask();
			mAuthTask.execute((Void) null);
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

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterStatusView
									.setVisibility(show ? View.VISIBLE
											: View.GONE);
						}
					});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegisterFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login task used to register the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {
		protected Boolean doInBackground(Void... oarams) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}
			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(username) && pieces[1].equals(password)
						&& pieces[2].equals(passwordRepeat)
						&& pieces[3].equals(email)
						&& pieces[4].equals(firstname)) {
					return pieces[5].equals(lastname);

				}
			}
			return true;
		}

		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);
			if (success) {
				finish();

			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
				mPasswordRepeatView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordRepeatView.requestFocus();

			}

		}

		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
