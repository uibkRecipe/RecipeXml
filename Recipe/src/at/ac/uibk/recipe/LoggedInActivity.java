package at.ac.uibk.recipe;

import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import at.ac.uibk.recipe.adapter.TabsPagerAdapter;

public class LoggedInActivity extends FragmentActivity implements
		ActionBar.TabListener, OnClickListener {

	private ViewPager viewPager = null;
	private TabsPagerAdapter mAdapter = null;
	private ActionBar actionBar = null;

	private String[] tabs = { "Co2 neutral", "Vegetarian", "Meat and Fish",
			"Flour-based" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		
		findViewById(R.id.home).setOnClickListener(this);
		findViewById(R.id.searchTab).setOnClickListener(this);
		findViewById(R.id.favorites).setOnClickListener(this);
		findViewById(R.id.profile).setOnClickListener(this);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logged_in, menu);
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

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// on tab selected
		// show respected fragment view
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.home) {
			
		} else if (v.getId() == R.id.searchTab) {
		

		} else if (v.getId() == R.id.favorites) {
			

		} else if (v.getId() == R.id.profile) {
			
			
			
			/**
			 * 
			 * 
			 * 	BACKGROUND IMAGE AFTER CLICK IS TO BIG
			 * SETPADDING OR SOMETIGH ELSE MAYBE WILL HELP 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 * 
			 */
			ImageButton home = (ImageButton)findViewById(R.id.home);
			home.setBackgroundResource(0);
			home.setImageResource(R.drawable.ic_home);
			
			ImageButton search = (ImageButton)findViewById(R.id.searchTab);
			search.setBackgroundResource(0);
			search.setImageResource(R.drawable.ic_action_search2);
			
			ImageButton favorites = (ImageButton)findViewById(R.id.favorites);
			favorites.setBackgroundResource(0);
			favorites.setImageResource(R.drawable.ic_action_favorite);
			
			ImageButton profile = (ImageButton)findViewById(R.id.profile);
			profile.setBackgroundResource(0);
			profile.setImageResource(R.drawable.ic_action_person_selected);
			


		}

	}

}
