package cc.shuozi.uidesign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BreakfastFragment extends Fragment {
    private FirebaseAuth mAuth;
    private int i=0;
    private food[][] foods_breakfast;
    private String[][] information_breakfast;
    private ArrayList<food> food_data_breakfast=new ArrayList<food>();
    private int a=0;

    private void getnum(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("diet")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if ("Breakfast".equals(doc.getString("Diet type"))) {
                                    a++;
                                }
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacknumber(a);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    public void getbreakfast(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("diet")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                /*
                                 * information[][0]->time uid
                                 * information[][1]->year
                                 * information[][2]->month
                                 * information[][3]->day
                                 * */
                                if ("Breakfast".equals(doc.getString("Diet type"))) {
                                    information_breakfast[i][0] = String.valueOf(doc.get("Time id"));
                                    information_breakfast[i][1] = String.valueOf(doc.get("Diet year"));
                                    information_breakfast[i][2] = String.valueOf(doc.get("Diet month"));
                                    information_breakfast[i][3] = String.valueOf(doc.get("Diet day"));
                                    information_breakfast[i][4] = doc.getString("Diet type");

                                   for (int b = 0;b>=0;b++) {
                                       if (doc.getString("Food name " + b)==null) {
                                           Log.e("success","break");
                                           break;
                                       }else{
                                           food cd=new food(doc.getString("Food name " + String.valueOf(b)), Integer.parseInt(String.valueOf(doc.get("Food Weight " + String.valueOf(b)))));
                                           foods_breakfast[i][b]=cd;
                                       }
                                    }

                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacklistdiet(information_breakfast,foods_breakfast);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    public BreakfastFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View breakfastView = inflater.inflate(R.layout.list_meal, container, false);
        final ListView list = breakfastView.findViewById(R.id.tab_meal);
        getnum(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int i) {
                information_breakfast=new String[i][5];
                foods_breakfast=new food[i][40];
                getbreakfast(new callback() {
                    @Override
                    public void onCallback(String string) {

                    }

                    @Override
                    public void onCallbacknumber(int i) {

                    }

                    @Override
                    public void onCallbackList(ArrayList<String> list) {

                    }

                    @Override
                    public void onCallbackListstring(String[][] data) {

                    }

                    @Override
                    public void onCallbacklistdiet(String[][] data, food[][] foods) {
                        my_adapter_diet sc=new my_adapter_diet(getContext(),foods,data);
                        list.setAdapter(sc);
                    }


                });
            }

            @Override
            public void onCallbackList(ArrayList<String> list) {

            }

            @Override
            public void onCallbackListstring(String[][] data) {

            }

            @Override
            public void onCallbacklistdiet(String[][] data, food[][] foods) {

            }


        });

        return breakfastView;
    }
}