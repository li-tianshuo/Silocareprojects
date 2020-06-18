package cc.shuozi.uidesign;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter_doc extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private String[][] data;
    private String documentid;
    private FirebaseAuth mAuth;
    private void getid(final callback oncallback)
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
                                documentid=doc.getId();
                            }
                            oncallback.onCallback(documentid);
                        } else {
                            Log.d("Status", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }

    public MyAdapter_doc (Context context, String[][] data)
    {
        this.context= context;
        this.data = data;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position][0];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.itlist_item_doc, viewGroup, false);
        final TextView text1 = (TextView) convertView.findViewById(R.id.list_t_d_1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_t_d_2);
        TextView text3 = (TextView) convertView.findViewById(R.id.list_t_d_3);
        final Button update=(Button)convertView.findViewById(R.id.list_b_d_1);
        final Button delete=(Button)convertView.findViewById(R.id.list_b_d_2);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);
        text3.setText(data[position][2]);
        if (text1.getText().toString().substring(0,1).equals("P"))
        {
            delete.setVisibility(View.GONE);
        }
        update.setClickable(false);
        update.setEnabled(false);
        delete.setClickable(false);
        delete.setEnabled(false);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(viewGroup.getContext(), doctor_information.class);
                intent.putExtra("status","update");
                if (text1.getText().toString().substring(0,1).equals("P"))
                {
                    intent.putExtra("status","add_major");
                }else {
                    intent.putExtra("status","update");
                    intent.putExtra("list",text1.getText().toString().substring(12,13));
                    Log.e("Status",text1.getText().toString().substring(12,13));
                }

                viewGroup.getContext().startActivity(intent);
            }
        });
        getid(new callback() {
            @Override
            public void onCallback(String string) {
                update.setClickable(true);
                update.setEnabled(true);
                delete.setClickable(true);
                delete.setEnabled(true);
                documentid=string;
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
        });
        delete.setVisibility(View.GONE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference updateref = db.collection("users").document(documentid);
                updateref
                        .update("Doctor Status"+text1.getText().toString().substring(12,13), false)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Status", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Status", "Error updating document", e);
                            }
                        });
            }
        });
        return convertView;

    }
}
