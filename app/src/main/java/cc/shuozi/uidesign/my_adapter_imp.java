package cc.shuozi.uidesign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class my_adapter_imp extends BaseAdapter {
    private Context context;
    private String[][] data;
    private String documentid;
    private FirebaseAuth mAuth;

    public my_adapter_imp (Context context, String[][] data)
    {
        this.context= context;
        this.data = data;
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
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.implementation_list_view, parent, false);
        TextView text1 = (TextView) convertView.findViewById(R.id.text_imp_title);
        TextView text2 = (TextView) convertView.findViewById(R.id.context_imp);
        Button view=(Button)convertView.findViewById(R.id.imp_button);
        FloatingActionButton add=(FloatingActionButton)convertView.findViewById(R.id.imp_float_button);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);



        return convertView;
    }
}
