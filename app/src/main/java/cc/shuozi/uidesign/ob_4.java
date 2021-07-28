package cc.shuozi.uidesign;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ob_4 extends Fragment {
    private Button ob_4_button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.onboarding_4, container, false);
        ob_4_button=rootView.findViewById(R.id.ob_4_button);
        ob_4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MainMenu.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        return rootView;
    }
}
