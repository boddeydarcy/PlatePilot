package uk.edu.le.co2124.frontend_app.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import uk.edu.le.co2124.frontend_app.ui.adapters.CategoryPagerAdapter;
import uk.edu.le.co2124.frontend_app.R;
import uk.edu.le.co2124.frontend_app.ui.fragments.MainsFragment;
import uk.edu.le.co2124.frontend_app.ui.fragments.StartersFragment;

public class NewOrderActivity extends AppCompatActivity {

    private TabLayout topTabs;
    private ViewPager2 pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        topTabs = findViewById(R.id.topTabs);
        pager = findViewById(R.id.viewPager);

        pager.setAdapter(new CategoryPagerAdapter(this));

        new TabLayoutMediator(topTabs, pager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Starters"); break;
                case 1: tab.setText("Mains"); break;
                case 2: tab.setText("Desserts"); break;
                case 3: tab.setText("Sides"); break;
                case 4: tab.setText("Drinks"); break;
            }
        }).attach();
    }
}
