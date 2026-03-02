/**
 * Write a description of class Lid here.
 *
 * @Rodriguez-Villamizar
 * @1 (15/02/2026)
 */
public class Lid {

    private int number;
    private Rectangle figure;

    public Lid(int number, String color) {
        this.number = number;
        this.figure = new Rectangle();
        this.figure.changeSize(10, 40);
        this.figure.changeColor(color);
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
