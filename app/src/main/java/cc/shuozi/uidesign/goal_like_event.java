package cc.shuozi.uidesign;

import java.util.ArrayList;

public class goal_like_event {
    private String name;
    private String type;
    private int priority;
    private ArrayList<String> description;
    private ArrayList<String> note;
    public goal_like_event(String name,String type,int priority)
    {
        this.name=name;
        this.type=type;
        this.priority=priority;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }

    public void setType(String type)
    {
        this.type=type;
    }
    public String getType()
    {
        return type;
    }

    public void setPriority(int Priority)
    {
        this.priority=Priority;
    }
    public int getPriority()
    {
        return priority;
    }

    public void clean_note_description()
    {
        description.clear();
        note.clear();
    }
    public void update_description(String description_string)
    {
        description.add(description_string);
    }
    public void update_note(String note_string)
    {
        note.add(note_string);
    }
    public String get_description(int pos)
    {
        return description.get(pos);
    }
    public String get_note(int pos)
    {
        return note.get(pos);
    }
}
