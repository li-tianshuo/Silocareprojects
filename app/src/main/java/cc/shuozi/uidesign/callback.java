package cc.shuozi.uidesign;

import com.google.firebase.database.core.view.Event;

import java.util.ArrayList;
import java.util.List;

public interface callback {
    void onCallback(String string);
    void onCallbackList(ArrayList<String> list);
}
