package tower;

import shapes.Rectangle;

/**
 * Write a description of class Cup here.
 *
 * @author Rodriguez-Villamizar
 * @version 1 (15/02/2026)
 */
public class Cup extends StackItem {
    private int nCup;
    private Rectangle view;
    private int height;
    private int width;
    private int x; 
    private int y;
    private String color;
    private boolean isVisible;
    private static final int SCALE=20;
    
    private boolean isCovered; //ciclo 2

    /**
     * Constructor for objects of class Cup
     */
    public Cup(int nCup, String color){
        // Llamamos al constructor de StakItem 
        super("cup", nCup, color);

        this.nCup = nCup;
        this.height = (2*nCup-1) *SCALE;
        this.width = (nCup*10)+30;
        this.color = color;
        this.isVisible = false;
        this.x = 70;  // default de Rectangle
        this.y = 15;  // default de Rectangle
        
        this.isCovered = false;
        
        view= new Rectangle();
        view.changeColor(color);
        view.changeSize(this.height,this.width);
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
        
        if (view != null) {
            view.moveHorizontal(dx);
            view.moveVertical(dy);
        }
    }
    
    /**
     * Make the cup visible. If it was already visible, do nothing.
     */
    public void makeVisible() {
        isVisible = true;
        view.makeVisible();
    }
    /**
     * Make the cup invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible() {
        isVisible = false;
        view.makeInvisible();
    }
    
    /**
     * Retorna si la taza está tapada o no.
     */
    public boolean isCovered() {
        return this.isCovered;
    }

    /**
     * Tapa la taza y cambia su color visual para cumplir con el requisito de usabilidad.
     */
    public void cover() {
        this.isCovered = true;
        if (view != null) {
            view.changeColor("gray"); 
        }
    }

    /**
     * Destapa la taza y restaura su color original.
     */
    public void uncover() {
        this.isCovered = false;
        if (view != null) {
            view.changeColor(this.color);
        }
    }
}