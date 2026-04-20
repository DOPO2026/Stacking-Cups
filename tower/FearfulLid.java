package tower;

import shapes.Rectangle;

/**
 * Tapa tipo Fearful: no entra si su taza companera no esta en la torre.
 * Si esta tapando a su taza, no se puede quitar.
 * Visualmente se distingue con un borde amarillo.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class FearfulLid extends Lid {

    private Rectangle mark;
    private int markLastX = 70;
    private int markLastY = 15;

    public FearfulLid(int number, String color) {
        super(number, color);
        mark = new Rectangle();
        mark.changeColor("yellow");
        mark.changeSize(10, 6);
    }

    @Override
    public String getSubType() {
        return "fearful";
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        if (mark != null) {
            int targetX = newX - 6;
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
