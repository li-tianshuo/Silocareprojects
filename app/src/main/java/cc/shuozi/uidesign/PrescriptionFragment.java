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
import java.util.List;

import javax.annotation.Nullable;

public class PrescriptionFragment extends Fragment {
    private String[][] data={{"0","Please wait","0","0","0","0","0","0"}};
    private FirebaseAuth mAuth;
    private int i=0;
    private int a=0;
    private void getnum(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("px")
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

    private void checkalarm(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("px")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                data[i][0]= String.valueOf(doc.get("Time id"));
                                data[i][1]=String.valueOf(doc.get("Event month"))+"/"+String.valueOf(doc.get("Event day"))+"/"+String.valueOf(doc.get("Event year"))
                                        +" "+String.valueOf(doc.get("Event hour"))+":"+String.valueOf(doc.get("Event minute"));
                                data[i][2]=doc.getString("Event note");
                                data[i][3]= String.valueOf(doc.get("Event year"));
                                data[i][4]= String.valueOf(doc.get("Event month"));
                                data[i][5]= String.valueOf(doc.get("Event day"));
                                data[i][6]= String.valueOf(doc.get("Event hour"));
                                data[i][7]= String.valueOf(doc.get("Event minute"));
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
        final View rootView = inflater.inflate(R.layout.prescription_fragment, container, false);
        FloatingActionButton fab_px=rootView.findViewById(R.id.fab_prescription);
        final ListView list=rootView.findViewById(R.id.list_px_f);
        fab_px.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), alarm_information.class));
            }
        });
        my_adapter_px sc = new my_adapter_px(getContext(), data);
        list.setAdapter(sc);
        getnum(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int c) {
                data=new String[c][8];
                checkalarm(new callback() {
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
                        my_adapter_px sc = new my_adapter_px(getContext(), data);
                        list.setAdapter(sc);
                    }

                    @Override
                    public void onCallbacklistdiet(String[][] data, food[][] foods) {

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

        return rootView;
    }
}
