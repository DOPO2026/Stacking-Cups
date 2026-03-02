/**
 * Write a description of class Lid here.
 *
 * @Rodriguez-Villamizar
 * @1 (15/02/2026)
 */
public class Lid extends StackItem {

    private int number;
    private Rectangle figure;

    public Lid(int number, String color) {
        super("lid", number, color);
        this.number = number;
        this.figure = new Rectangle();
        this.figure.changeSize(10, 40);
        this.figure.changeColor(color);
    }

    // StackItem nos exige tener este método
    @Override
    public int getHeight() {
        return 1; 
    }

    public int getNumber() {
        return number;
    }

    public void makeVisible() {
        figure.makeVisible();
    }

    public void makeInvisible() {
        figure.makeInvisible();
    }
}
