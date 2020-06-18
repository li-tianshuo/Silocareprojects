package cc.shuozi.uidesign;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends BaseAdapter implements View.OnClickListener  {
    private Context context;
    private String[][] data;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.itlist_item, viewGroup, false);
        TextView text1 = (TextView) convertView.findViewById(R.id.list_t_1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_t_2);
        TextView text3 = (TextView) convertView.findViewById(R.id.list_t_3);
        TextView text4 = (TextView) convertView.findViewById(R.id.list_t_4);
        TextView text5 = (TextView) convertView.findViewById(R.id.list_t_5);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);
        text3.setText(data[position][2]);
        text4.setText(data[position][3]);
        text5.setText(data[position][4]);
        return convertView;

    }

}
