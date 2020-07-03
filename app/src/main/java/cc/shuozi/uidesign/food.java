package cc.shuozi.uidesign;

public class food {
    private String name;
    private int weight;
    public food()
    {
        super();
    }
    public food(String name, int weight)
    {
        super();
        this.name=name;
        this.weight=weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight =weight;
    }

}
