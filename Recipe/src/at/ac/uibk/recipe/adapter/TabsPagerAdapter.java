package at.ac.uibk.recipe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import at.ac.uibk.recipe.Co2NeutralFragment;
import at.ac.uibk.recipe.FlourFragment;
import at.ac.uibk.recipe.MeatFragment;
import at.ac.uibk.recipe.VegetarianFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{

	public TabsPagerAdapter(FragmentManager fm){
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		switch(index){
		case 0:
			return new Co2NeutralFragment();
		case 1:
			return new VegetarianFragment();
		case 2:
			return new MeatFragment();
		case 3: 
			return new FlourFragment();
					
		}
		return null;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

}

