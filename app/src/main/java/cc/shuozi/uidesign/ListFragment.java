package cc.shuozi.uidesign;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class ListFragment extends Fragment {
    private ListView list;
    private String[][] data={{"Please wait","","","",""}};
    private FirebaseAuth mAuth;


    private String datas1[]={"Modify account information", "Set Condition information", "Set Doctor Information"};

    public ListFragment() {
        // Required empty public constructor
    }
    private void checkcondition(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("users")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                data[0][0]=("Condition 1"+doc.getString("Condition 1"));
                                data[0][1]=("Condition 2"+doc.getString("Condition 2"));
                                data[0][2]=("Condition 3"+doc.getString("Condition 3"));
                                data[0][3]=("Condition 4"+doc.getString("Condition 4"));
                                data[0][4]="";
                                Log.d("Status", "Successful get information ", task.getException());
                            }
                            oncallbackString.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.information_fragment, container, false);
        list=rootView.findViewById(R.id.tab_list);
        MyAdapter ad = new MyAdapter(getActivity(), data);
        list.setAdapter(ad);
        checkcondition(new callback() {
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
                MyAdapter ad = new MyAdapter(getActivity(), data);
                list.setAdapter(ad);
            }


        });

        return rootView;
    }

}



