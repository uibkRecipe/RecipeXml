package at.ac.uibk.recipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import at.ac.uibk.recipe.FavoritesAllFragment;
import at.ac.uibk.recipe.FavoritesCo2NeutralFragment;
import at.ac.uibk.recipe.FavoritesFlourFragment;
import at.ac.uibk.recipe.FavoritesMeatFragment;
import at.ac.uibk.recipe.FavoritesVegetarianFragment;


public class FavoritesTabsPagerAdapter extends FragmentPagerAdapter {

	public FavoritesTabsPagerAdapter(android.support.v4.app.FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			return new FavoritesAllFragment();
		case 1:
			return new FavoritesCo2NeutralFragment();
		case 2:
			return new FavoritesVegetarianFragment();
		case 3:
			return new FavoritesMeatFragment();
		case 4:
			return new FavoritesFlourFragment();

		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

}
