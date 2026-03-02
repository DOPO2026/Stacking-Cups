public abstract class StackItem {
    protected String type;
    protected int number;
    protected String color;

    public StackItem(String type, int number, String color) {
        this.type = type;
        this.number = number;
        this.color = color;
    }

    public abstract int getHeight();
    public abstract void makeVisible();
    public abstract void makeInvisible();
    
    public String getType() { return type; }
    public int getNumber() { return number; }
}