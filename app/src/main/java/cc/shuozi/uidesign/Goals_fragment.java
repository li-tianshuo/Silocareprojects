package cc.shuozi.uidesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Goals_fragment extends Fragment {
    public Goals_fragment()
    {
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.symptoms_goals_fragment, container, false);
        final RecyclerView list = rootview.findViewById(R.id.goals_list);
        return rootview;
    }
}