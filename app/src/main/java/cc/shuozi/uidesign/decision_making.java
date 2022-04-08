package cc.shuozi.uidesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class decision_making extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    private ImageView  avator_decision_making_9;
    private void initdrawer() {
        drawerLayout=findViewById(R.id.drawerlayout_decision_making);
        toolbar=findViewById(R.id.toolbar_decision_making);



        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }


            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        drawerToggle.syncState();

        drawerLayout.setDrawerListener(drawerToggle);


        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_making);
        NavigationView navigationView=findViewById(R.id.main_side);

        avator_decision_making_9= findViewById(R.id.avatar_decision_making_1);
        if (avatorExists())
        {
            Uri imgUri=Uri.parse("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            avator_decision_making_9.setImageURI(imgUri);
        }


        viewPager=findViewById(R.id.decison_viewpage);
        bottomNavigationView=findViewById(R.id.decision_making_bottom_menu);
        bottomNavigationView.setItemIconTintList(null);
        initdrawer();
        navigationView.setNavigationItemSelectedListener(this);
        decision_ViewPagerAdapter decision_viewPagerAdapter=new decision_ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());

        decision_viewPagerAdapter.addFragment(new PrescriptionFragment());
        decision_viewPagerAdapter.addFragment(new DietFragment());
        decision_viewPagerAdapter.addFragment(new PhysicalFragment());
        decision_viewPagerAdapter.addFragment(new MentalFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(decision_viewPagerAdapter);

        int id = getIntent().getIntExtra("mode", 0);
        if (id == 1) {
            viewPager.setCurrentItem(0);
            bottomNavigationView.getMenu().getItem(0).setChecked(true);
        }else if (id == 2) {
            viewPager.setCurrentItem(1);
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
        }else if (id == 3) {
            viewPager.setCurrentItem(2);
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
        }else if (id == 4) {
            viewPager.setCurrentItem(3);
            bottomNavigationView.getMenu().getItem(3).setChecked(true);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.menu_decision_prescription:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_decision_diet:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_decision_pa:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu_decision_ma:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                super.onPageSelected(position);
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.side_mainmenu:
                Intent main1=new Intent(decision_making.this, MainMenu.class);
                startActivity(main1);
                finish();
                return true;
            case R.id.side_setting:
                Intent main2=new Intent(decision_making.this, Setting.class);
                startActivity(main2);
                finish();
                return true;
            case R.id.side_implementation:
                Intent main3=new Intent(decision_making.this, symptoms_goal.class);
                startActivity(main3);
                finish();
                return true;
            case R.id.side_decision:
                Intent main4=new Intent(decision_making.this, decision_making.class);
                startActivity(main4);
                finish();
                return true;
                /*
            case R.id.goal_menu:
                Intent main5=new Intent(decision_making.this, symptoms_goal.class);
                startActivity(main5);
                finish();
                return true;

                 */
            case R.id.information_menu:
                Intent main6=new Intent(decision_making.this, information.class);
                startActivity(main6);
                finish();
                return true;
        }
        return false;
    }
    public boolean avatorExists()
    {
        try
        {
            File f=new File("file:///data/data/cc.shuozi.uidesign/avator.jpg");
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return true;
        }

        return false;
    }

}