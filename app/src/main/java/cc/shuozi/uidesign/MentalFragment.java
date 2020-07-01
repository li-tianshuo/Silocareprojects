package cc.shuozi.uidesign;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MentalFragment extends Fragment {
    private String[][] data={{"0","Please wait","0","0","0","0","0","0","0","0","0","0","0"}};
    private FirebaseAuth mAuth;
    private int i=0;
    private int a=0;
    private void getnum(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("mentalactivity")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                a++;
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbacknumber(a);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    private void checkma(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("mentalactivity")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                data[i][0]= String.valueOf(doc.get("Time id"));
                                data[i][1]=doc.getString("Event");
                                data[i][2]= String.valueOf(doc.get("Event start year"));
                                data[i][3]= String.valueOf(doc.get("Event start month"));
                                data[i][4]= String.valueOf(doc.get("Event start day"));
                                data[i][5]= String.valueOf(doc.get("Event start hour"));
                                data[i][6]= String.valueOf(doc.get("Event start minute"));
                                data[i][7]= String.valueOf(doc.get("Event end year"));
                                data[i][8]= String.valueOf(doc.get("Event end month"));
                                data[i][9]= String.valueOf(doc.get("Event end day"));
                                data[i][10]= String.valueOf(doc.get("Event end hour"));
                                data[i][11]= String.valueOf(doc.get("Event end minute"));
                                data[i][12]="1";
                                i++;
                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.mental_activities_fragment, container, false);
        FloatingActionButton fab_pa=rootView.findViewById(R.id.ma_fab);
        final ListView list=rootView.findViewById(R.id.list_ma);
        fab_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), event_selection.class);
                intent.putExtra("status",0);
                startActivity(intent);
            }
        });
        my_adapter_activity sc = new my_adapter_activity(getContext(), data);
        list.setAdapter(sc);
        getnum(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int c) {
                data=new String[c][13];
                checkma(new callback() {
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
                        my_adapter_activity sc = new my_adapter_activity(getContext(), data);
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
        });
        return rootView;
    }
}
