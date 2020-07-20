package cc.shuozi.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Goals_fragment extends Fragment implements goal_adapter.OnStartDragListener{
    private ItemTouchHelper mTouchHelper;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private FirebaseAuth mAuth;
    private String uid;
    private String data[][];
    private int a;
    private int i=0;
    private int num;
    private ArrayList<goal_like_event> data_array;
    private void getevent(final callback oncallbackString)
    {
        i=0;
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if ("Goal".equals(doc.getString("type"))) {
                                    data[i][0] = String.valueOf(doc.get("name"));
                                    data[i][1] = String.valueOf(doc.get("year"));
                                    data[i][2] = String.valueOf(doc.get("month"));
                                    data[i][3] = String.valueOf(doc.get("day"));
                                    data[i][4] = String.valueOf(doc.get("hour"));
                                    data[i][5] = String.valueOf(doc.get("minute"));
                                    data[i][6] = String.valueOf(doc.get("priority"));
                                    data[i][7] = doc.getString("color");
                                    data[i][8] = doc.getId();
                                    for (int b = 0;b>=0;b++) {
                                        if (doc.getString("Description " + b)==null) {
                                            Log.e("success","break");
                                            break;
                                        }else{
                                            data[i][9+b]=String.valueOf(doc.get("Description " + b));
                                            data[i][10+b]=String.valueOf(doc.get("Note " + b));
                                        }
                                    }
                                    i++;
                                }

                                Log.d("Status", "Success on get Document", task.getException());
                            }
                            oncallbackString.onCallbackListstring(data);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });
    }
    private void getnum(final callback oncallbackString)
    {
        mAuth= FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String value=user.getUid();
        db.collection("goal")
                .whereEqualTo("uid", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(DocumentSnapshot doc : task.getResult()) {
                                if ("Goal".equals(doc.getString("type"))) {
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
    public Goals_fragment()
    {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.symptoms_goals_fragment, container, false);
        final RecyclerView list = rootview.findViewById(R.id.goals_list);
        data_array=getData();
        data_array=new ArrayList<goal_like_event>();
        FloatingActionButton add_goal_fab=rootview.findViewById(R.id.add_goal_main_fab);
        add_goal_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), add_goal.class);
                intent.putExtra("status",1);
                startActivity(intent);
            }
        });
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        final goal_adapter adapter=new goal_adapter(getContext(),data_array,list);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);
        mTouchHelper = new ItemTouchHelper(new TouchCallBack(adapter));
        mTouchHelper.attachToRecyclerView(list);
        adapter.setOnStartDragListener(this);
        getnum(new callback() {
            @Override
            public void onCallback(String string) {

            }

            @Override
            public void onCallbacknumber(int i) {
                if (i==0)
                {
                    Toast.makeText(getContext(),"Nothing here",Toast.LENGTH_LONG);
                }else
                {
                    data=new String[i][99];
                    num=i;
                    getevent(new callback() {
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
                            /*
                            data[i][0] = String.valueOf(doc.get("name"));
                            data[i][1] = String.valueOf(doc.get("year"));
                            data[i][2] = String.valueOf(doc.get("month"));
                            data[i][3] = String.valueOf(doc.get("day"));
                            data[i][4] = String.valueOf(doc.get("hour"));
                            data[i][5] = String.valueOf(doc.get("minute"));
                            data[i][6] = String.valueOf(doc.get("priority"));
                            data[i][7] = String.valueOf(doc.get("color"));


                             */
                            data_array=new ArrayList<goal_like_event>();
                            for (int c=0;c<num;c++)
                            {
                                data_array.add(new goal_like_event(data[c][0],"Goal",Integer.parseInt(data[c][6]),data[c][7]));
                                data_array.get(c).setTime(Integer.parseInt(data[c][1]),Integer.parseInt(data[c][2]),Integer.parseInt(data[c][3]),Integer.parseInt(data[c][4]),Integer.parseInt(data[c][5]));
                                data_array.get(c).setDocumentid(data[c][8]);
                                int z=9;
                                while(z<data[c].length)
                                {
                                    if (data[c][z]!=null)
                                    {
                                    data_array.get(c).update_description(data[c][z]);
                                    data_array.get(c).update_note(data[c][z+1]);
                                    z=z+2;
                                    }else
                                    {
                                        break;
                                    }
                                }

                            }
                            goal_adapter adapter=new goal_adapter(getContext(),data_array,list);
                            adapter.notifyDataSetChanged();
                            list.setAdapter(adapter);
                            mTouchHelper = new ItemTouchHelper(new TouchCallBack(adapter));
                            mTouchHelper.attachToRecyclerView(list);
                            adapter.setOnStartDragListener(Goals_fragment.this);

                        }

                        @Override
                        public void onCallbacklistdiet(String[][] data, food[][] foods) {

                        }
                    });
                }
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

        return rootview;
    }

    private ArrayList<goal_like_event> getData() {
        ArrayList<goal_like_event> data_=new ArrayList<goal_like_event>();
        data_.add(new goal_like_event("Please wait","0",0,"#00000f"));
        return data_;
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder holder) {
        mTouchHelper.startDrag(holder);
    }
}
