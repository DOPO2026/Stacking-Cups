package shapes;

/**
 * Clase abstracta que define el comportamiento comun de todas las figuras.
 * Refactorizacion del paquete shapes aprovechando herencia.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public abstract class ShapeBase {
    
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Constructor comun para todas las figuras.
     */
    protected ShapeBase(int xPosition, int yPosition, String color) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.color = color;
        this.isVisible = false;
    }

    /**
     * Hace visible la figura.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Hace invisible la figura.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Mueve la figura hacia la derecha.
     */
    public void moveRight() {
        moveHorizontal(20);
    }

    /**
     * Mueve la figura hacia la izquierda.
     */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /**
     * Mueve la figura hacia arriba.
     */
    public void moveUp() {
        moveVertical(-20);
    }

    /**
     * Mueve la figura hacia abajo.
     */
    public void moveDown() {
        moveVertical(20);
    }

    /**
     * Mueve la figura horizontalmente.
     * @param distance distancia en pixeles
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Mueve la figura verticalmente.
     * @param distance distancia en pixeles
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Mueve la figura horizontalmente de forma lenta.
     * @param distance distancia en pixeles
     */
    public void slowMoveHorizontal(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /**
     * Mueve la figura verticalmente de forma lenta.
     * @param distance distancia en pixeles
     */
    public void slowMoveVertical(int distance) {
        int delta;
        if (distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    /**
     * Cambia el color de la figura.
     * @param newColor colores validos: "red", "yellow", "blue", "green", "magenta", "black"
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Dibuja la figura en el canvas. Cada subclase define su forma especifica.
     */
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, getAwtShape());
            canvas.wait(10);
        }
    }

    /**
     * Borra la figura del canvas.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    /**
     * Retorna la forma java.awt.Shape correspondiente para dibujar.
     * Cada subclase implementa su geometria especifica.
     */
    protected abstract java.awt.Shape getAwtShape();
}