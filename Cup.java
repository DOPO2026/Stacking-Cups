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
        this.isVisible=false;
        view= new Rectangle();
        view.changeColor(color);
        view.changeSize(this.height,this.width);
    }
    public int getHeight() {
        return this.height;
    }
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
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
}
