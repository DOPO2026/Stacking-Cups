package tower;

import shapes.Rectangle;

/**
 * Taza tipo Magnetic: al entrar a la torre, si su tapa companera
 * ya esta, la atrae justo encima y la tapa automaticamente.
 * Visualmente se distingue con un cuadro azul en el centro.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class MagneticCup extends Cup {

    private Rectangle mark;
    private int markLastX = 70;
    private int markLastY = 15;

    public MagneticCup(int nCup, String color) {
        super(nCup, color);
        mark = new Rectangle();
        mark.changeColor("blue");
        mark.changeSize(8, 8);
    }

    @Override
    public String getSubType() {
        return "magnetic";
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        if (mark != null) {
            int targetX = newX + (width / 2) - 4;
            int targetY = newY + (height / 2) - 4;
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
