package com.enigma.dif.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.enigma.dif.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements GridCalendarFragment.onGridFragmentInteraction {

    // Obtain reference to fragments in view pager for communication
    GridCalendarFragment gridFragment;
    ListCalendarFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up view pager
        ViewPager viewPager = findViewById(R.id.view_pager);
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        gridFragment = new GridCalendarFragment();
        listFragment = new ListCalendarFragment();

        adapter.addFragments(listFragment);
        adapter.addFragments(gridFragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void updateListOnChange(Calendar calendar) {
        listFragment.updateListFragmentOnDateChange(calendar);
    }
}
