package cc.shuozi.uidesign;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DietFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Fragment fragment = null;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int i=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.diet_fragment, container, false);
        final FloatingActionButton add_diet_menu=rootView.findViewById(R.id.add_diet_menu);
        TabLayout tab_diet = rootView.findViewById(R.id.tab_diet);
        FrameLayout frameLayout_diet=rootView.findViewById(R.id.layout_diet);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fragment = new BreakfastFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.layout_diet, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
        tab_diet.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new BreakfastFragment();
                        break;
                    case 1:
                        fragment = new LunchFragment();
                        break;
                    case 2:
                        fragment = new SnacksFragment();
                        break;
                    case 3:
                        fragment = new DinnerFragment();
                        break;
                }
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.layout_diet, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        add_diet_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), diet_information.class);
                intent.putExtra("status",0);
                intent.putExtra("diet","");
                String mode=getFragmentManager().findFragmentById(R.id.layout_diet).toString().substring(0,1);
                Log.e("Success",mode);
                if (mode.equals("B"))
                {
                    intent.putExtra("diet","Breakfast");
                }else if (mode.equals("L"))
                {
                    intent.putExtra("diet","Lunch");
                }else if (mode.equals("S"))
                {
                    intent.putExtra("diet","Snacks");
                }else if (mode.equals("D"))
                {
                    intent.putExtra("diet","Dinner");
                }
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }

    }




