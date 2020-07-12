package cc.shuozi.uidesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Symptoms_fragment extends Fragment {
    public Symptoms_fragment()
    {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.symptoms_goals_fragment, container, false);
        final RecyclerView list = rootview.findViewById(R.id.goals_list);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootview;
    }
}
