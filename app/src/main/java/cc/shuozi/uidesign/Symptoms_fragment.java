package cc.shuozi.uidesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Symptoms_fragment extends Fragment implements goal_adapter.OnStartDragListener{
    private ItemTouchHelper mTouchHelper;
    public Symptoms_fragment()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.symptoms_goals_fragment, container, false);
        final RecyclerView list = rootview.findViewById(R.id.goals_list);
        ArrayList<goal_like_event> data= getData();
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        goal_adapter adapter=new goal_adapter(getContext(),data,list);
        adapter.notifyDataSetChanged();

        list.setAdapter(adapter);
        mTouchHelper = new ItemTouchHelper(new TouchCallBack(adapter));
        mTouchHelper.attachToRecyclerView(list);
        adapter.setOnStartDragListener(this);
        return rootview;
    }
    private ArrayList<goal_like_event> getData() {
        ArrayList<goal_like_event> data=new ArrayList<goal_like_event>();
        data.add(new goal_like_event("name1","0",2,0xFFFFFF));
        data.add(new goal_like_event("name2","0",3,0x000000));
        data.add(new goal_like_event("name3","0",1,0xFFFFFF));
        data.get(0).update_description("whatever");
        return data;
    }

    @Override
    public void startDrag(RecyclerView.ViewHolder holder) {
        mTouchHelper.startDrag(holder);
    }
}
