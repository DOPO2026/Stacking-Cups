package shapes;

/**
 * Un rectangulo que se dibuja en el canvas.
 * 
 * @author Michael Kolling and David J. Barnes (Modified)
 * @version Ciclo 4 - Refactorizado con herencia
 */
public class Rectangle extends ShapeBase {

    public static int EDGES = 4;

    private int height;
    private int width;

    /**
     * Crea un rectangulo con posicion y color por defecto.
     */
    public Rectangle() {
        super(70, 15, "magenta");
        height = 30;
        width = 40;
    }

    /**
     * Cambia el tamano del rectangulo.
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
        return new java.awt.Rectangle(xPosition, yPosition, width, height);
    }
}