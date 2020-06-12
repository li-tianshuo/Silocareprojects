package cc.shuozi.uidesign;

import com.google.firebase.database.core.view.Event;

import java.util.List;

public interface callback {
    void onCallback(List<Event> eventList);
}
