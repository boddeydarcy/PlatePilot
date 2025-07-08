package uk.edu.le.co2124.frontend_app.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import uk.edu.le.co2124.frontend_app.ItemListFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.DessertsFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.DrinksFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.MainsFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.SidesFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.StartersFragment;

public class CategoryPagerAdapter extends FragmentStateAdapter {
    public CategoryPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new StartersFragment();
            case 1: return new MainsFragment();
            case 2: return new DessertsFragment();
            case 3: return new SidesFragment();
            case 4: return new DrinksFragment();
            default: return new StartersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}