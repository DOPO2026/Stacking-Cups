package tower;

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
    public abstract void move(int x, int y);
    
    public String getType() { return type; }
    public int getNumber() { return number; }
    public String getColor() { return color; }
    
    /**
     * Retorna el subtipo del elemento ("normal" por defecto).
     * Las subclases especializadas sobreescriben este metodo.
     */
    public String getSubType() { return "normal"; }
}