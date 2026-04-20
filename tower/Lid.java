package tower;

import shapes.Rectangle;

/**
 * Write a description of class Lid here.
 *
 * @Rodriguez-Villamizar
 * @1 (15/02/2026)
 */
public class Lid extends StackItem {

    private Rectangle figure;
    private int x;
    private int y;

    public Lid(int number, String color) {
        super("lid", number, color);
        this.x = 70;  // default de Rectangle
        this.y = 15;  // default de Rectangle
        this.figure = new Rectangle();
        this.figure.changeSize(10, 40);
        this.figure.changeColor(color);
    }

    @Override
    public int getHeight() {
        return 1; 
    }

    /**
     * Mueve la tapa a una posicion absoluta en pixeles.
     */
    @Override
    public void move(int newX, int newY) {
        int dx = newX - this.x;
        int dy = newY - this.y;
        this.x = newX;
        this.y = newY;
        
        if (figure != null) {
            figure.moveHorizontal(dx);
            figure.moveVertical(dy);
        }
    }

    public void makeVisible() {
        figure.makeVisible();
    }

    public void makeInvisible() {
        figure.makeInvisible();
    }
}