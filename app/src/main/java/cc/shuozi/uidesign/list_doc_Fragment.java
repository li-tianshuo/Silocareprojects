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

public class list_doc_Fragment extends Fragment {
    private ListView list;
    private String[][] data={{"Please wait","",""}};

    private String[][] docdata={{"","",""}};
    private String[][] pdocdata={{"","",""}};
    private FirebaseAuth mAuth;
    private FloatingActionButton add_doc;
    private int i;
    private int docnum=0;
    private int b;

    private void getdocnumber(final callback oncallbacknumber)
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
                            int num=0;
                            for(DocumentSnapshot doc : task.getResult()) {
                                for (b=0; b>=0; b++)
                                {
                                    if (doc.getString("Doctor Name "+String.valueOf(b+1))==null)
                                    {
                                        break;
                                    }
                                    if (doc.getBoolean("Doctor Status"+String.valueOf(b+1)))
                                    {
                                        num++;
                                    }
                                }
                            }
                            oncallbacknumber.onCallbacknumber(num);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
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
                                pdocdata[0][0]=("Primary Doctor Name: "+doc.getString("Primary Doctor Name"));
                                pdocdata[0][1]=("Primary Doctor Email: "+doc.getString("Primary Doctor Email"));
                                pdocdata[0][2]=("Primary Doctor Phone: "+doc.getString("Primary Doctor Phone"));
                                Log.d("Status", "Successful get information ", task.getException());
                            }
                            oncallbackString.onCallbackListstring(pdocdata);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getotherdoc(final callback oncallbackString)
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
                                int c=0;
                                for (int a=0;a>=0;a++)
                                {
                                    if (doc.getString("Doctor Name "+String.valueOf(a+1))!=null){
                                        Log.e("success", String.valueOf(doc.getBoolean("Doctor Status"+String.valueOf(a+1))));
                                        if ((doc.getBoolean("Doctor Status"+String.valueOf(a+1)))) {
                                            Log.e("success", "success");
                                            docdata[c][0] = ("Doctor Name "+ (a+1)+ ":" + doc.getString("Doctor Name " + String.valueOf(a + 1)));
                                            docdata[c][1] = ("Doctor Email "+ (a+1)+ ":" + doc.getString("Doctor Email " + String.valueOf(a + 1)));
                                            docdata[c][2] = ("Doctor Phone "+ (a+1)+ ":" + doc.getString("Doctor Phone " + String.valueOf(a + 1)));
                                            c++;
                                        }
                                    }else{
                                        break;
                                    }
                                }


                                Log.d("Status", "Successful get information ", task.getException());
                            }
                            oncallbackString.onCallbackListstring(docdata);
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
        final View rootView = inflater.inflate(R.layout.information_fragment_doc, container, false);
        list=rootView.findViewById(R.id.list_tab_doc);
        MyAdapter_doc ad = new MyAdapter_doc(getActivity(), data);
        list.setAdapter(ad);
        getdocnumber(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int b) {
                Log.e("Success", String.valueOf(b));
                docnum=b;
                docdata=new String[b][3];
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
                    public void onCallbackListstring(final String[][] pdocdata) {
                        getotherdoc(new callback() {
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
                            public void onCallbackListstring(String[][] docdata) {
                                if (docdata[0][0]=="")
                                {
                                    MyAdapter_doc ad = new MyAdapter_doc(getActivity(), pdocdata);
                                    list.setAdapter(ad);
                                }else{
                                    String[][] finaldata = new String[pdocdata.length +docdata.length][];
                                    System.arraycopy(pdocdata, 0, finaldata , 0, pdocdata.length);
                                    System.arraycopy(docdata, 0, finaldata , pdocdata.length, docdata.length);
                                    MyAdapter_doc ad = new MyAdapter_doc(getActivity(), finaldata);
                                    list.setAdapter(ad);
                                }

                            }
                        });

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




        add_doc=rootView.findViewById(R.id.add_doc_button);
        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), doctor_information.class);
                intent.putExtra("status","add_doc");
                startActivity(intent);
            }
        });
        return rootView;
    }

}
