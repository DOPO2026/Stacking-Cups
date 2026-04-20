package tower;

import shapes.Rectangle;

public class Lid extends StackItem {

    protected Rectangle figure;
    protected int x;
    protected int y;

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
