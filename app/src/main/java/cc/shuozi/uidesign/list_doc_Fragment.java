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
    private String[][] data={{"Please wait","","",""}};
    private String primaryid;
    private String[][] docdata={{"","","",""}};
    private String[][] pdocdata={{"","","",""}};
    private FirebaseAuth mAuth;
    private FloatingActionButton add_doc;
    private int i;
    private int docnum=0;
    private int b;
    private ArrayList docuidlist=new ArrayList();

    private void primaryid(final callback oncallback)
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
                                primaryid=doc.getString("Primary Doctor uid");
                            }
                            oncallback.onCallback(primaryid);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getotherdocuid(final callback oncallbacklist)
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
                                    if (doc.getString("Doctor uid "+String.valueOf(b+1))==null)
                                    {
                                        break;
                                    }else
                                    {
                                        docuidlist.add(doc.getString("Doctor uid "+String.valueOf(b+1)));
                                    }

                                }
                            }
                            oncallbacklist.onCallbackList(docuidlist);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
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
                                    if (doc.getString("Doctor uid "+String.valueOf(b+1))==null)
                                    {
                                        break;
                                    }else
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
        db.collection("doctors")
                .whereEqualTo("uid", primaryid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                    pdocdata[0][0] = ("Primary Doctor Name: " + doc.getString("Doctor Name"));
                                    pdocdata[0][1] = ("Primary Doctor Email: " + doc.getString("Doctor Email"));
                                    pdocdata[0][2] = ("Primary Doctor Phone: " + doc.getString("Doctor Phone"));
                                    pdocdata[0][3] = doc.getString("uid");
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
        for (int i=0;i<docnum;i++)
        {
            final int finalI = i;
            db.collection("doctors")
                    .whereEqualTo("uid", docuidlist.get(i))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for(DocumentSnapshot doc : task.getResult()) {
                                    docdata[finalI][0] = ("Doctor Name "+ (finalI+1)+ ":" + doc.getString("Doctor Name"));
                                    docdata[finalI][1] = ("Doctor Email "+ (finalI+1)+ ":" + doc.getString("Doctor Email"));
                                    docdata[finalI][2] = ("Doctor Phone "+ (finalI+1)+ ":" + doc.getString("Doctor Phone"));
                                    docdata[finalI][3] = doc.getString("uid");
                                    Log.d("Status", "Successful get information ", task.getException());
                                }

                            } else {
                                Log.d("Status", "Error getting documents: ", task.getException());
                            }
                        }

                    });
        }
        oncallbackString.onCallbackListstring(docdata);
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
                docnum=b;
                docdata=new String[b][4];
                getotherdocuid(new callback() {
                    @Override
                    public void onCallback(String string) {

                    }

                    @Override
                    public void onCallbacknumber(int i) {

                    }

                    @Override
                    public void onCallbackList(ArrayList<String> alist) {
                        docuidlist= (ArrayList) alist.clone();
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
                            public void onCallbackListstring(String[][] data) {
                                docdata=data.clone();
                                primaryid(new callback() {
                                    @Override
                                    public void onCallback(String string) {
                                        primaryid = string;
                                        if (primaryid == null) {
                                            pdocdata[0][0] = "Primary Doctor Not setup";
                                            pdocdata[0][1] = "";
                                            pdocdata[0][2] = "";
                                            pdocdata[0][3] = mAuth.getUid();
                                            MyAdapter_doc ad = new MyAdapter_doc(getActivity(), pdocdata);
                                            list.setAdapter(ad);
                                        } else {
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
                                                public void onCallbackListstring(String[][] pdata) {
                                                    pdocdata = pdata.clone();
                                                    if (docdata.length == 0) {
                                                        MyAdapter_doc ad = new MyAdapter_doc(getActivity(), pdocdata);
                                                        list.setAdapter(ad);
                                                    } else {
                                                        String[][] finaldata = new String[pdocdata.length + docdata.length][];
                                                        System.arraycopy(pdocdata, 0, finaldata, 0, pdocdata.length);
                                                        System.arraycopy(docdata, 0, finaldata, pdocdata.length, docdata.length);
                                                        MyAdapter_doc ad = new MyAdapter_doc(getActivity(), finaldata);
                                                        list.setAdapter(ad);
                                                    }
                                                }

                                                @Override
                                                public void onCallbacklistdiet(String[][] data, food[][] foods) {

                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onCallbacknumber(int i) {

                                    }

                                    @Override
                                    public void onCallbackList(ArrayList<String> alist) {

                                    }

                                    @Override
                                    public void onCallbackListstring(String[][] data) {

                                    }

                                    @Override
                                    public void onCallbacklistdiet(String[][] data, food[][] foods) {

                                    }
                                });
                            }

                            @Override
                            public void onCallbacklistdiet(String[][] data, food[][] foods) {

                            }
                        });
                    }

                    @Override
                    public void onCallbackListstring(String[][] data) {

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




        add_doc=rootView.findViewById(R.id.add_doc_button);
        add_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), doctor_information.class);
                intent.putExtra("status","add_doc");
                startActivity(intent);
                getActivity().finish();
            }
        });
        return rootView;
    }

}
