package at.ac.uibk.recipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import at.ac.uibk.recipe.SearchAllFragment;
import at.ac.uibk.recipe.SearchCo2NeutralFragment;
import at.ac.uibk.recipe.SearchFlourFragment;
import at.ac.uibk.recipe.SearchMeatFragment;
import at.ac.uibk.recipe.SearchVegetarianFragment;

public class SearchTabsPagerAdapter extends FragmentPagerAdapter {

	SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

	public SearchTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new SearchAllFragment();
		case 1:
			return new SearchCo2NeutralFragment();
		case 2:
			return new SearchVegetarianFragment();
		case 3:
			return new SearchMeatFragment();
		case 4:
			return new SearchFlourFragment();

		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
