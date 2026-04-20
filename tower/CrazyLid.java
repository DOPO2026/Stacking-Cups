package tower;

import shapes.Rectangle;

/**
 * Tapa tipo Crazy: en lugar de tapar a su taza,
 * se ubica en la base de la torre (posicion 0).
 * Visualmente se distingue con un borde verde.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class CrazyLid extends Lid {

    private Rectangle mark;
    private int markLastX = 70;
    private int markLastY = 15;

    public CrazyLid(int number, String color) {
        super(number, color);
        mark = new Rectangle();
        mark.changeColor("green");
        mark.changeSize(10, 6);
    }

    @Override
    public String getSubType() {
        return "crazy";
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        if (mark != null) {
            int targetX = newX + 40;
            int targetY = newY;
            int dx = targetX - markLastX;
            int dy = targetY - markLastY;
            markLastX = targetX;
            markLastY = targetY;
            mark.moveHorizontal(dx);
            mark.moveVertical(dy);
        }
    }

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
