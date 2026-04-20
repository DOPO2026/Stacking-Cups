package shapes;

import java.awt.*;

/**
 * Un triangulo que se dibuja en el canvas.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version Ciclo 4 - Refactorizado con herencia
 */
public class Triangle extends ShapeBase {

    public static int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Crea un triangulo con posicion y color por defecto.
     */
    public Triangle() {
        super(140, 15, "green");
        height = 30;
        width = 40;
    }

    /**
     * Cambia el tamano del triangulo.
     * @param newHeight nueva altura en pixeles
     * @param newWidth nuevo ancho en pixeles
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    @Override
    protected java.awt.Shape getAwtShape() {
        int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
        int[] ypoints = { yPosition, yPosition + height, yPosition + height };
        return new Polygon(xpoints, ypoints, 3);
    }
}