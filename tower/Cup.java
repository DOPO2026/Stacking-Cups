package tower;

import shapes.Rectangle;

/**
 * Representa una taza cilindrica del simulador.
 * Visualmente: rectangulo solido de color + rectangulo blanco interior (hueco).
 * La taza i mide 2i-1 cm de alto, con base de 1 cm de grosor.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class Cup extends StackItem {
    protected int nCup;
    protected Rectangle body;    // rectangulo solido de color
    protected Rectangle hole;    // rectangulo blanco (hueco interior)
    protected int height;        // altura visual en pixeles
    protected int width;         // ancho visual en pixeles
    protected int wallSize;      // grosor de pared lateral en px
    protected int baseSize;      // grosor de base en px
    protected int x; 
    protected int y;
    protected String color;
    protected boolean isVisible;
    protected static final int SCALE = 10;  // 1 cm = 10 pixeles (igual que Tower.SCALE_PX)
    
    private boolean isCovered;

    /**
     * Constructor de Cup.
     * @param nCup numero de la taza
     * @param color color de la taza
     */
    public Cup(int nCup, String color) {
        super("cup", nCup, color);

        this.nCup = nCup;
        this.height = (2 * nCup - 1) * SCALE;
        this.width = nCup * SCALE * 2;   // cup1=20, cup2=40, cup3=60, cup4=80
        this.color = color;
        this.isVisible = false;
        this.x = 70;
        this.y = 15;
        this.isCovered = false;
        
        // Pared = 10px fijo = mitad de la diferencia entre tazas consecutivas
        this.wallSize = SCALE;
        // Base: 1 cm = SCALE pixeles
        this.baseSize = SCALE;
        
        // Cuerpo solido: ocupa todo el espacio de la taza
        body = new Rectangle();
        body.changeColor(color);
        body.changeSize(this.height, this.width);
        
        // Hueco interior blanco
        int holeW = this.width - 2 * wallSize;
        int holeH = this.height - baseSize;
        
        if (holeW > 0 && holeH > 0) {
            hole = new Rectangle();
            hole.changeColor("white");
            hole.changeSize(holeH, holeW);
            hole.moveHorizontal(wallSize);
        }
    }

    public int getHeight() {
        return 2 * nCup - 1;
    }
    
    /**
     * Mueve la taza a una posicion absoluta en pixeles.
     */
    public void move(int newX, int newY) {
        int dx = newX - this.x;
        int dy = newY - this.y;
        this.x = newX;
        this.y = newY;
        
        body.moveHorizontal(dx);
        body.moveVertical(dy);
        if (hole != null) {
            hole.moveHorizontal(dx);
            hole.moveVertical(dy);
        }
    }
    
    /**
     * Hace visible la taza. Primero el cuerpo, luego el hueco encima.
     */
    public void makeVisible() {
        isVisible = true;
        body.makeVisible();
        if (hole != null) {
            hole.makeVisible();
        }
    }
    
    /**
     * Muestra solo el cuerpo solido.
     */
    public void showBody() {
        body.makeVisible();
    }
    
    /**
     * Muestra solo el hueco blanco.
     */
    public void showHole() {
        if (hole != null) {
            hole.makeVisible();
        }
    }

    /**
     * Hace invisible la taza.
     */
    public void makeInvisible() {
        isVisible = false;
        if (hole != null) {
            hole.makeInvisible();
        }
        body.makeInvisible();
    }
    
    /**
     * Retorna si la taza esta tapada.
     */
    public boolean isCovered() {
        return this.isCovered;
    }

    /**
     * Tapa la taza y cambia su color visual.
     */
    public void cover() {
        this.isCovered = true;
        body.changeColor("gray");
    }

    /**
     * Destapa la taza y restaura su color original.
     */
    public void uncover() {
        this.isCovered = false;
        body.changeColor(this.color);
    }
}
