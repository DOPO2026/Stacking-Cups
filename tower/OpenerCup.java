package tower;

import shapes.Rectangle;

/**
 * Taza tipo Opener: al entrar a la torre, elimina todas las tapas
 * que le impiden el paso (tapas de tazas mas pequenas que ella).
 * Visualmente se distingue con una franja blanca interior.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class OpenerCup extends Cup {

    private Rectangle mark;

    public OpenerCup(int nCup, String color) {
        super(nCup, color);
        mark = new Rectangle();
        mark.changeColor("white");
        mark.changeSize(4, width - 10);
    }

    @Override
    public String getSubType() {
        return "opener";
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        if (mark != null) {
            int markX = newX + 5;
            int markY = newY + height - 8;
            int dx = markX - markLastX;
            int dy = markY - markLastY;
            markLastX = markX;
            markLastY = markY;
            mark.moveHorizontal(dx);
            mark.moveVertical(dy);
        }
    }

    private int markLastX = 75; // default Rectangle x + 5
    private int markLastY = 15; // default

    @Override
    public void makeVisible() {
        super.makeVisible();
        mark.makeVisible();
    }

    @Override
    public void makeInvisible() {
        super.makeInvisible();
        mark.makeInvisible();
    }
}
