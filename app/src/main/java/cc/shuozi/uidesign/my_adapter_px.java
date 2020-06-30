package cc.shuozi.uidesign;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.Comparator;

public class my_adapter_px extends BaseAdapter {
    private Context context;
    private String[][] data;
    private String documentid;
    private FirebaseAuth mAuth;

    public my_adapter_px(Context context, String[][] data)
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
        Arrays.sort(data, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return Integer.parseInt(o2[0])-Integer.parseInt(o1[0]);
            }
        });

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.prescription_list, parent, false);
        final TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView note = (TextView) convertView.findViewById(R.id.note);
        Button update=(Button)convertView.findViewById(R.id.update_px);
        time.setTag(data[position][0]);
        time.setText(data[position][1]);
        note.setText(data[position][2]);
        Calendar c=Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(data[position][3]));
        c.set(Calendar.MONTH,Integer.parseInt(data[position][4]));
        c.set(Calendar.DAY_OF_MONTH,Integer.parseInt(data[position][5]));
        c.set(Calendar.HOUR_OF_DAY,Integer.parseInt(data[position][6]));
        c.set(Calendar.MINUTE,Integer.parseInt(data[position][7]));
        c.set(Calendar.SECOND,0);
        if (c.getTimeInMillis()<System.currentTimeMillis())
        {
            update.setVisibility(View.GONE);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), alarm_information.class);
                intent.putExtra("status",0);
                intent.putExtra("time_id",Integer.parseInt(time.getTag().toString()));
                parent.getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
