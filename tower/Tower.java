package tower;

import shapes.Rectangle;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Clase principal que representa la torre del simulador.
 */
public class Tower {
    // Se eliminó 'private int width;' porque PMD detectó que no se utilizaba
    private int maxHeight;
    private List<StackItem> items;
    private boolean isVisible;
    private boolean ok;

    /**
     * Constructor para crear una torre dados el ancho y el alto.
     */
    public Tower(int width, int maxHeight) {
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
        this.isVisible = false;
        this.ok = true;
    }

    /**
     * Constructor del Ciclo 2: Crea una torre automáticamente con 'cups' cantidad de tazas.
     */
    public Tower(int cups) {
        this.maxHeight = 50; 
        this.items = new ArrayList<>();
        this.isVisible = false;
        this.ok = true;
        
        for (int i = 1; i <= cups; i++) {
            pushCup(i); 
        }
    }

    public void pushCup(int i) {
        pushCup("normal", i);
    }

    /**
     * Agrega una taza del tipo especificado.
     * @param type tipo de taza: "normal", "opener", "hierarchical", "magnetic"
     * @param i numero de la taza
     */
    public void pushCup(String type, int i) {
        if (!containsItem("cup", i)) {
            String[] colores = {"red", "yellow", "blue", "green", "magenta", "black"};
            String color = colores[i % colores.length];
            
            Cup nuevaTaza;
            switch (type.toLowerCase()) {
                case "opener":
                    nuevaTaza = new OpenerCup(i, color);
                    break;
                case "hierarchical":
                    nuevaTaza = new HierarchicalCup(i, color);
                    break;
                case "magnetic":
                    nuevaTaza = new MagneticCup(i, color);
                    break;
                case "normal":
                    nuevaTaza = new Cup(i, color);
                    break;
                default:
                    ok = false;
                    showError("Tipo de taza invalido: " + type);
                    return;
            }
            
            if (nuevaTaza instanceof OpenerCup) {
                applyOpenerLogic(nuevaTaza);
            }
            
            if (nuevaTaza instanceof HierarchicalCup) {
                applyHierarchicalLogic((HierarchicalCup) nuevaTaza);
            } else {
                items.add(nuevaTaza);
            }
            
            if (nuevaTaza instanceof MagneticCup) {
                applyMagneticLogic(nuevaTaza);
            }
            
            if (isVisible) nuevaTaza.makeVisible();
            ok = true;
            updateDisplay();
        } else {
            ok = false;
            showError("La taza " + i + " ya existe en la torre.");
        }
    }
    
    private void applyOpenerLogic(Cup opener) {
        List<StackItem> toRemove = new ArrayList<>();
        for (StackItem item : items) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("lid".equals(item.getType()) && item.getNumber() < opener.getNumber()) {
                item.makeInvisible();
                toRemove.add(item);
            }
        }
        items.removeAll(toRemove);
    }
    
    private void applyHierarchicalLogic(HierarchicalCup cup) {
        int insertPos = items.size(); 
        for (int j = items.size() - 1; j >= 0; j--) {
            StackItem item = items.get(j);
            if (item.getNumber() < cup.getNumber()) {
                insertPos = j; 
            } else {
                break; 
            }
        }
        items.add(insertPos, cup);
        
        if (insertPos == 0) {
            cup.anchor();
        }
    }
    
    private void applyMagneticLogic(Cup magnetic) {
        for (int j = 0; j < items.size(); j++) {
            StackItem item = items.get(j);
            // Corrección PMD: LiteralsFirstInComparisons
            if ("lid".equals(item.getType()) && item.getNumber() == magnetic.getNumber()) {
                items.remove(j);
                int newCupIndex = items.indexOf(magnetic);
                items.add(newCupIndex + 1, item);
                magnetic.cover();
                break;
            }
        }
    }
    
    public void popCup() {
        boolean found = false;
        for (int j = items.size() - 1; j >= 0; j--) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("cup".equals(items.get(j).type)) {
                if (items.get(j) instanceof HierarchicalCup 
                    && ((HierarchicalCup) items.get(j)).isAnchored()) {
                    continue;
                }
                items.get(j).makeInvisible();
                items.remove(j);
                found = true;
                break;
            }
        }
    
        if (found) {
            ok = true;
            updateDisplay();
        } else {
            ok = false;
            showError("No hay tazas removibles en la torre.");
        }
    }

    public void removeCup(int i) {
        boolean found = false;
        for (int j = 0; j < items.size(); j++) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("cup".equals(items.get(j).type) && items.get(j).number == i) {
                if (items.get(j) instanceof HierarchicalCup 
                    && ((HierarchicalCup) items.get(j)).isAnchored()) {
                    ok = false;
                    showError("La taza " + i + " esta anclada y no se puede remover.");
                    return;
                }
                items.get(j).makeInvisible();
                items.remove(j);
                found = true;
                break;
            }
        }
        ok = found;
        if (ok) {
            updateDisplay();
        } else {
            showError("No se pudo remover: La taza " + i + " no existe.");
        }
    }

    public void pushLid(int i) {
        pushLid("normal", i);
    }

    public void pushLid(String type, int i) {
        if (!containsItem("lid", i)) {
            String[] colores = {"red", "yellow", "blue", "green", "magenta", "black"};
            String color = colores[i % colores.length];

            Lid nuevaTapa;
            switch (type.toLowerCase()) {
                case "fearful":
                    nuevaTapa = new FearfulLid(i, color);
                    break;
                case "crazy":
                    nuevaTapa = new CrazyLid(i, color);
                    break;
                case "normal":
                    nuevaTapa = new Lid(i, color);
                    break;
                default:
                    ok = false;
                    showError("Tipo de tapa invalido: " + type);
                    return;
            }

            if (nuevaTapa instanceof FearfulLid) {
                if (!containsItem("cup", i)) {
                    ok = false;
                    showError("Tapa fearful " + i + ": su taza no esta en la torre.");
                    return;
                }
            }

            if (nuevaTapa instanceof CrazyLid) {
                items.add(0, nuevaTapa);
            } else {
                items.add(nuevaTapa);
            }

            if (isVisible) nuevaTapa.makeVisible();
            ok = true;
            updateDisplay();
        } else {
            ok = false;
            showError("La tapa " + i + " ya existe en la torre.");
        }
    }

    public void popLid() {
        for (int j = items.size() - 1; j >= 0; j--) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("lid".equals(items.get(j).type)) {
                if (items.get(j) instanceof FearfulLid) {
                    Cup cup = findCup(items.get(j).number);
                    if (cup != null && cup.isCovered()) {
                        continue;
                    }
                }
                items.get(j).makeInvisible();
                items.remove(j);
                ok = true;
                updateDisplay();
                return;
            }
        }
        ok = false;
        showError("No hay tapas removibles en la torre.");
    }

    public void removeLid(int i) {
        for (int j = 0; j < items.size(); j++) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("lid".equals(items.get(j).type) && items.get(j).number == i) {
                if (items.get(j) instanceof FearfulLid) {
                    Cup cup = findCup(i);
                    if (cup != null && cup.isCovered()) {
                        ok = false;
                        showError("La tapa fearful " + i + " esta tapando a su taza y no se puede remover.");
                        return;
                    }
                }
                items.get(j).makeInvisible();
                items.remove(j);
                ok = true;
                updateDisplay();
                return;
            }
        }
        ok = false;
    }

    public void orderTower() {
        items.sort((a, b) -> {
            if (a.number != b.number) {
                return Integer.compare(b.number, a.number); 
            } else {
                // Corrección PMD: LiteralsFirstInComparisons
                if ("cup".equals(a.type) && "lid".equals(b.type)) return -1;
                if ("lid".equals(a.type) && "cup".equals(b.type)) return 1;
                return 0;
            }
        });
        ok = true;
        updateDisplay();
    }

    public void reverseTower() {
        Collections.reverse(items);
        ok = true;
        updateDisplay();
    }

    public int height() {
        if (items.isEmpty()) return 0;
        
        int[] baseY = new int[items.size()];
        int maxH = 0;
        
        for (int i = 0; i < items.size(); i++) {
            StackItem current = items.get(i);
            int currentBaseY = 0;
            
            for (int j = 0; j < i; j++) {
                StackItem previous = items.get(j);
                // Corrección PMD: UnusedAssignment
                int interactionY;
                
                // Corrección PMD: LiteralsFirstInComparisons
                if ("cup".equals(current.getType()) && "cup".equals(previous.getType())) {
                    if (current.getNumber() < previous.getNumber()) {
                        interactionY = baseY[j] + 1; 
                    } else {
                        interactionY = baseY[j] + previous.getHeight();
                    }
                } else {
                    interactionY = baseY[j] + previous.getHeight();
                }
                
                if (interactionY > currentBaseY) {
                    currentBaseY = interactionY;
                }
            }
            
            baseY[i] = currentBaseY;
            int currentTopY = currentBaseY + current.getHeight();
            
            if (currentTopY > maxH) {
                maxH = currentTopY;
            }
        }
        
        ok = true;
        return maxH;
    }

    public int[] lidedCups() {
        List<Integer> lided = new ArrayList<>();
        
        for (StackItem item : items) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("cup".equals(item.getType())) {
                Cup cup = (Cup) item;
                if (cup.isCovered()) {
                    lided.add(cup.getNumber());
                }
            }
        }
        
        Collections.sort(lided); 
        ok = true;
        return lided.stream().mapToInt(i -> i).toArray();
    }

    public String[][] stackingitems() {
        String[][] result = new String[items.size()][2];
        for (int j = 0; j < items.size(); j++) {
            result[j][0] = items.get(j).type;
            result[j][1] = String.valueOf(items.get(j).number);
        }
        ok = true;
        return result;
    }

    public void makeVisible() {
        if (height() * 10 > 800) {
            ok = false;
            return; 
        }

        this.isVisible = true;
        drawHeightMarks(); 
        
        for (StackItem item : items) {
            item.makeVisible();
        }
        
        ok = true;
        updateDisplay();
    }   

    public void makelnvisible() {
        this.isVisible = false;
        ok = true;
        updateDisplay();
    }

    public void exit() {
        System.exit(0);
    }

    public boolean ok() {
        return this.ok;
    }

    private boolean containsItem(String type, int number) {
        for (StackItem item : items) {
            if (item.type.equals(type) && item.number == number) return true;
        }
        return false;
    }

    private Cup findCup(int number) {
        for (StackItem item : items) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("cup".equals(item.type) && item.number == number) {
                return (Cup) item;
            }
        }
        return null;
    }

    private void showError(String message) {
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final int SCALE_PX = 10; 
    private static final int BASE_Y = 280; 
    private static final int CENTER_X = 150; 

    private void updateDisplay() {
        if (!isVisible) return;
        
        for (StackItem item : items) {
            item.makeInvisible();
        }
        
        int[] baseYcm = new int[items.size()];
        
        for (int i = 0; i < items.size(); i++) {
            StackItem current = items.get(i);
            int currentBaseY = 0;
            
            for (int j = 0; j < i; j++) {
                StackItem previous = items.get(j);
                // Corrección PMD: UnusedAssignment
                int interactionY;
                
                // Corrección PMD: LiteralsFirstInComparisons
                if ("cup".equals(current.getType()) && "cup".equals(previous.getType())) {
                    if (current.getNumber() < previous.getNumber()) {
                        interactionY = baseYcm[j] + 1;
                    } else {
                        interactionY = baseYcm[j] + previous.getHeight();
                    }
                } else {
                    interactionY = baseYcm[j] + previous.getHeight();
                }
                
                if (interactionY > currentBaseY) {
                    currentBaseY = interactionY;
                }
            }
            
            baseYcm[i] = currentBaseY;
        }
        
        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            int heightPx = item.getHeight() * SCALE_PX;
            int widthPx = getItemWidth(item);
            
            int pixelX = CENTER_X - (widthPx / 2);
            int pixelY = BASE_Y - (baseYcm[i] * SCALE_PX) - heightPx;
            
            item.move(pixelX, pixelY);
        }
        
        Integer[] renderOrder = new Integer[items.size()];
        for (int i = 0; i < renderOrder.length; i++) renderOrder[i] = i;
        java.util.Arrays.sort(renderOrder, (a, b) -> items.get(b).getHeight() - items.get(a).getHeight());

        for (int idx : renderOrder) {
            items.get(idx).makeVisible();
        }
    }
    
    private int getItemWidth(StackItem item) {
        // Corrección PMD: LiteralsFirstInComparisons
        if ("cup".equals(item.getType())) {
            return item.getNumber() * SCALE_PX * 2; 
        } else {
            return 40;
        }
    }
    
    private void drawHeightMarks() {
        int maxMarks = Math.min(maxHeight, BASE_Y / SCALE_PX); 
        
        for (int h = 0; h <= maxMarks; h++) {
            Rectangle marca = new Rectangle();
            marca.changeSize(1, 10);
            marca.changeColor("black");
            
            int targetX = 5;
            int targetY = BASE_Y - (h * SCALE_PX);
            marca.moveHorizontal(targetX - 70);
            marca.moveVertical(targetY - 15);
            
            marca.makeVisible();
        }
    }

    /**
     * Intercambia las posiciones de dos elementos en la torre.
     */
    // Corrección PMD: UseVarargs aplicadas al último parámetro
    public void swap(String[] o1, String... o2) {
        String type1 = o1[0];
        int no1 = Integer.parseInt(o1[1]);
        String type2 = o2[0];
        int no2 = Integer.parseInt(o2[1]);
        
        int index1 = -1; 
        int index2 = -1;
        
        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            
            if (item.getType().equals(type1) && item.getNumber() == no1) {
                index1 = i;
            }
            
            if (item.getType().equals(type2) && item.getNumber() == no2) {
                index2 = i;
            }
        }
        
        if (index1 != -1 && index2 != -1) {
            Collections.swap(items, index1, index2);
            ok = true;
            updateDisplay();
        } else {
            ok = false;
            showError("No se pudieron encontrar ambos elementos para intercambiar.");
        }
    }
    
    public void cover() {
        for (StackItem item : items) {
            // Corrección PMD: LiteralsFirstInComparisons
            if ("cup".equals(item.getType())) {
                Cup cup = (Cup) item; 
                if (containsItem("lid", cup.getNumber())) {
                    cup.cover();
                }
            }
        }
        ok = true;
        updateDisplay();
    }
    
    /**
     * Sugiere un intercambio que reduzca la altura total de la torre.
     */
    // Se restauró el tipo de retorno String[][] requerido por el diseño
    public String[][] swapToReduce() {
        int currentHeight = height();
        
        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                Collections.swap(items, i, j);
                int newHeight = height();
                
                if (newHeight < currentHeight) {
                    StackItem itemI = items.get(i);
                    StackItem itemJ = items.get(j);
                    
                    Collections.swap(items, i, j);
                    
                    ok = true;
                    return new String[][]{
                        {itemJ.type, String.valueOf(itemJ.number)},
                        {itemI.type, String.valueOf(itemI.number)}
                    };
                }
                
                Collections.swap(items, i, j);
            }
        }
        
        ok = false;
        return new String[][]{}; 
    }
}