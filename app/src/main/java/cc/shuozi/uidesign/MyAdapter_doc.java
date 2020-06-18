package cc.shuozi.uidesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter_doc extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private String[][] data;
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.itlist_item_doc, viewGroup, false);
        TextView text1 = (TextView) convertView.findViewById(R.id.list_t_d_1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_t_d_2);
        TextView text3 = (TextView) convertView.findViewById(R.id.list_t_d_3);
        text1.setText(data[position][0]);
        text2.setText(data[position][1]);
        text3.setText(data[position][2]);
        return convertView;

    }
}
