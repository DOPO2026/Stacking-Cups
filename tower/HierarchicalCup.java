package tower;

import shapes.Rectangle;

/**
 * Taza tipo Hierarchical: al entrar a la torre, va desplazando todos
 * los objetos de menor tamano. Si logra llegar al fondo, no se deja quitar.
 * Visualmente se distingue con una franja negra gruesa en la base.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class HierarchicalCup extends Cup {

    private boolean anchored;
    private Rectangle mark;
    private int markLastX = 70;
    private int markLastY = 15;

    public HierarchicalCup(int nCup, String color) {
        super(nCup, color);
        this.anchored = false;
        mark = new Rectangle();
        mark.changeColor("black");
        mark.changeSize(6, width);
    }

    @Override
    public String getSubType() {
        return "hierarchical";
    }

    public boolean isAnchored() {
        return anchored;
    }

    public void anchor() {
        this.anchored = true;
    }

    @Override
    public void move(int newX, int newY) {
        super.move(newX, newY);
        if (mark != null) {
            int targetX = newX;
            int targetY = newY + height - 6;
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
