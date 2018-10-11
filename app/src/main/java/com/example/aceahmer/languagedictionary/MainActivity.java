package com.example.aceahmer.languagedictionary;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;


public class MainActivity extends AppCompatActivity {

  private ViewPager fragment_viewpager;
    PagerAdapter pagerAdapter;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_viewpager=findViewById(R.id.Fragment_ViewPager);
        PagerAdapter pagerAdapter=new PagerAdapter(this.getSupportFragmentManager());
        pagerAdapter.addFragment(new Family());
        pagerAdapter.addFragment(new Colors());
        pagerAdapter.addFragment(new Phrases());
        pagerAdapter.addFragment(new Numbers());

        fragment_viewpager.setAdapter(pagerAdapter);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(fragment_viewpager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment_viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}
