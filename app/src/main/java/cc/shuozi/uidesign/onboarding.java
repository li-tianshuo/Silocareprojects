package cc.shuozi.uidesign;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class onboarding extends FragmentActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);
        SharedPreferences sharedPreferences = getSharedPreferences("a", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("key", true).commit();
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.ob_pg);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            SharedPreferences sharedPreferences = getSharedPreferences("a", MODE_PRIVATE);
            boolean b = sharedPreferences.getBoolean("key", false);
            if (b)
            {
                Intent intent = new Intent(onboarding.this, ongoing_main_menu.class);
                startActivity(intent);
                finish();
            }
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new ob_1();
                case 1:
                    return new ob_2();
                case 2:
                    return new ob_3();
                case 3:
                    return new ob_4();
            }
            return new ob_4();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}