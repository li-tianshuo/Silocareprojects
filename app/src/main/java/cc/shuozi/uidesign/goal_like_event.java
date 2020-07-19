package cc.shuozi.uidesign;

import java.util.ArrayList;

public class goal_like_event {
    private String name;
    private String type;
    private int priority;
    private ArrayList<String> description=new ArrayList<>();
    private ArrayList<String> note=new ArrayList<>();
    private int color;
    public goal_like_event(String name,String type,int priority, int color)
    {
        this.name=name;
        this.type=type;
        this.priority=priority;
        this.color=color;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
    public void setColor(int color)
    {
        this.color=color;
    }
    public int getColor()
    {
        return color;
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
    public ArrayList<String> get_description_array()
    {
        return description;
    }
    public ArrayList<String> get_note_array()
    {
        return note;
    }
}
