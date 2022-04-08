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
        final TextView text1 = (TextView) convertView.findViewById(R.id.text_imp_title);
        TextView text2 = (TextView) convertView.findViewById(R.id.context_imp);
        Button view=(Button)convertView.findViewById(R.id.imp_button);
        FloatingActionButton add=(FloatingActionButton)convertView.findViewById(R.id.imp_float_button);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Mental Activity".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),decision_making.class);
                    intent.putExtra("mode",4);
                    parent.getContext().startActivity(intent);
                }else if("Diet".equals(text1.getText())){
                    Intent intent = new Intent(parent.getContext(),decision_making.class);
                    intent.putExtra("mode",2);
                    parent.getContext().startActivity(intent);
                }else if ("Px".equals(text1.getText())){
                    Intent intent = new Intent(parent.getContext(),decision_making.class);
                    intent.putExtra("mode",1);
                    parent.getContext().startActivity(intent);
                }else if ("Physical Activity".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),decision_making.class);
                    intent.putExtra("mode",3);
                    parent.getContext().startActivity(intent);
                }else if("Health Feed".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),rss_main.class);
                    parent.getContext().startActivity(intent);
                }else if("Health Video".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),health_video.class);
                    parent.getContext().startActivity(intent);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Mental Activity".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),event_selection.class);
                    intent.putExtra("status",0);
                    parent.getContext().startActivity(intent);
                }else if("Diet".equals(text1.getText())){
                    Intent intent = new Intent(parent.getContext(),diet_information.class);
                    intent.putExtra("status",0);
                    parent.getContext().startActivity(intent);
                }else if ("Px".equals(text1.getText())){
                    Intent intent = new Intent(parent.getContext(),alarm_information.class);
                    intent.putExtra("status",1);
                    parent.getContext().startActivity(intent);
                }else if ("Physical Activity".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),event_selection.class);
                    intent.putExtra("status",2);
                    parent.getContext().startActivity(intent);
                }else if("Health Feed".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),rss_main.class);
                    parent.getContext().startActivity(intent);
                }else if("Health Video".equals(text1.getText())) {
                    Intent intent = new Intent(parent.getContext(),health_video.class);
                    parent.getContext().startActivity(intent);
                }
            }
        });


        return convertView;
    }
}
