package shapes;

import java.awt.geom.*;

/**
 * Un circulo que se dibuja en el canvas.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version Ciclo 4 - Refactorizado con herencia
 */
public class Circle extends ShapeBase {

    public static final double PI = 3.1416;

    private int diameter;

    /**
     * Crea un circulo con posicion y color por defecto.
     */
    public Circle() {
        super(20, 15, "blue");
        diameter = 30;
    }

    /**
     * Cambia el tamano del circulo.
     * @param newDiameter nuevo diametro en pixeles
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    @Override
    protected java.awt.Shape getAwtShape() {
        return new Ellipse2D.Double(xPosition, yPosition, diameter, diameter);
    }
}