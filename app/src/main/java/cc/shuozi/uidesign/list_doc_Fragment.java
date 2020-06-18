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

public class list_doc_Fragment extends Fragment {
    private ListView list;
    private String[][] data={{"Please wait","",""}};
    private FirebaseAuth mAuth;

    private void checkPridoc(final callback oncallbackString)
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
                                data[0][0]=("Primary Doctor Name: "+doc.getString("Primary Doctor Name"));
                                data[0][1]=("Primary Doctor Email: "+doc.getString("Primary Doctor Email"));
                                data[0][2]=("Primary Doctor Phone: "+doc.getString("Primary Doctor Phone"));
                                Log.d("Status", "Successful get information ", task.getException());
                            }
                            oncallbackString.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    public list_doc_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.information_fragment, container, false);
        list=rootView.findViewById(R.id.tab_list);
        MyAdapter_doc ad = new MyAdapter_doc(getActivity(), data);
        list.setAdapter(ad);
        checkPridoc(new callback() {
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
                MyAdapter_doc ad = new MyAdapter_doc(getActivity(), data);
                list.setAdapter(ad);
            }


        });

        return rootView;
    }

}
