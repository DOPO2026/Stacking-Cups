/**
 * Write a description of class Lid here.
 *
 * @Rodriguez-Villamizar
 * @1 (15/02/2026)
 */
public class Lid {
<<<<<<< HEAD
    public static final int SCALE = 20;
    private Rectangle lid;
    private int nCup; 

    public Lid(int nCup, int size, String color) {
        this.nCup = nCup;
        int height = 1 * SCALE;
        int width = (nCup * 10) + 40;        
        lid = new Rectangle();
        lid.changeColor(color);
        lid.changeSize(height, width);
        }
        
    /**
     * Make the lid visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        lid.makeVisible();
        }
    
    public void makeInvisible() {
        lid.makeInvisible();
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
 origin/main
    }
}
