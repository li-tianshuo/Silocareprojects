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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends BaseAdapter implements View.OnClickListener  {
    private Context context;
    private String[][] data;
    private String documentid;
    private FirebaseAuth mAuth;
    public MyAdapter (Context context, String[][] data)
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
        convertView = inflater.inflate(R.layout.itlist_item, viewGroup, false);
        TextView text1 = (TextView) convertView.findViewById(R.id.list_t_1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_t_2);
        TextView text3 = (TextView) convertView.findViewById(R.id.list_t_3);
        TextView text4 = (TextView) convertView.findViewById(R.id.list_t_4);
        TextView text5 = (TextView) convertView.findViewById(R.id.list_t_5);
        Button update=(Button)convertView.findViewById(R.id.list_b_1);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);
        text3.setText(data[position][2]);
        text4.setText(data[position][3]);
        text5.setText(data[position][4]);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseFirestore db = FirebaseFirestore.getInstance();
                //DocumentReference updateref = db.collection("users").document(documentid);
                Intent intent=new Intent(viewGroup.getContext(), pre_information.class);
                //intent.putExtra("status","add_major");
                viewGroup.getContext().startActivity(intent);
            }
        });

        return convertView;

    }

}
