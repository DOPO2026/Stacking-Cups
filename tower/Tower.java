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
    private int width;
    private int maxHeight;
    private List<StackItem> items;
    private boolean isVisible;
    private boolean ok;

    /**
     * Constructor para crear una torre dados el ancho y el alto.
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.items = new ArrayList<>();
        this.isVisible = false;
        this.ok = true;
    }

    /**
     * Constructor del Ciclo 2: Crea una torre automáticamente con 'cups' cantidad de tazas.
     */
    public Tower(int cups) {
        // Valores por defecto para ancho y alto máxima
        this.width = 100; 
        this.maxHeight = 50; 
        this.items = new ArrayList<>();
        this.isVisible = false;
        this.ok = true;
        
        // Creamos las tazas automáticamente desde 1 hasta la cantidad deseada
        for (int i = 1; i <= cups; i++) {
            pushCup(i); // Reutilizamos el método que ya tienes para crear tazas
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
                default:
                    nuevaTaza = new Cup(i, color);
                    break;
            }
            
            // Logica especial segun tipo
            if (nuevaTaza instanceof OpenerCup) {
                applyOpenerLogic(nuevaTaza);
            }
            
            if (nuevaTaza instanceof HierarchicalCup) {
                applyHierarchicalLogic((HierarchicalCup) nuevaTaza);
            } else {
                items.add(nuevaTaza);
            }
            
            // Magnetic: atrae su tapa companera justo encima
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
    
    /**
     * Opener: elimina todas las tapas de tazas mas pequenas que le impiden el paso.
     */
    private void applyOpenerLogic(Cup opener) {
        List<StackItem> toRemove = new ArrayList<>();
        for (StackItem item : items) {
            if (item.getType().equals("lid") && item.getNumber() < opener.getNumber()) {
                item.makeInvisible();
                toRemove.add(item);
            }
        }
        items.removeAll(toRemove);
    }
    
    /**
     * Hierarchical: desplaza objetos de menor tamano hacia arriba.
     * Si logra llegar al fondo (posicion 0), se ancla.
     */
    private void applyHierarchicalLogic(HierarchicalCup cup) {
        // Buscar posicion: desplaza todos los menores
        int insertPos = items.size(); // por defecto al final
        for (int j = items.size() - 1; j >= 0; j--) {
            StackItem item = items.get(j);
            if (item.getNumber() < cup.getNumber()) {
                insertPos = j; // puede pasar este item
            } else {
                break; // no puede pasar un item de mayor o igual tamano
            }
        }
        items.add(insertPos, cup);
        
        if (insertPos == 0) {
            cup.anchor();
        }
    }
    
    /**
     * Magnetic: si la tapa companera esta en la torre, la mueve justo
     * despues de esta taza y la marca como tapada.
     */
    private void applyMagneticLogic(Cup magnetic) {
        int cupIndex = items.indexOf(magnetic);
        // Buscar tapa companera
        for (int j = 0; j < items.size(); j++) {
            StackItem item = items.get(j);
            if (item.getType().equals("lid") && item.getNumber() == magnetic.getNumber()) {
                // Remover la tapa de su posicion actual
                items.remove(j);
                // Ajustar indice de la taza si se removio antes
                int newCupIndex = items.indexOf(magnetic);
                // Insertar justo despues de la taza
                items.add(newCupIndex + 1, item);
                // Tapar la taza
                magnetic.cover();
                break;
            }
        }
    }
    
    public void popCup() {
        boolean found = false;
        for (int j = items.size() - 1; j >= 0; j--) {
            if (items.get(j).type.equals("cup")) {
                // Hierarchical anclada no se puede quitar
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
            if (items.get(j).type.equals("cup") && items.get(j).number == i) {
                // Hierarchical anclada no se puede quitar
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

    /**
     * Agrega una tapa del tipo especificado.
     * @param type tipo de tapa: "normal", "fearful", "crazy"
     * @param i numero de la tapa
     */
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
                default:
                    nuevaTapa = new Lid(i, color);
                    break;
            }

            // Fearful: no entra si su taza companera no esta
            if (nuevaTapa instanceof FearfulLid) {
                if (!containsItem("cup", i)) {
                    ok = false;
                    showError("Tapa fearful " + i + ": su taza no esta en la torre.");
                    return;
                }
            }

            // Crazy: se inserta en la base (posicion 0)
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
            if (items.get(j).type.equals("lid")) {
                // Fearful tapando a su taza no se puede quitar
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
            if (items.get(j).type.equals("lid") && items.get(j).number == i) {
                // Fearful tapando a su taza no sale
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
        // Ordena de mayor a menor; el número menor queda en la cima[cite: 105].
        // Si la taza y la tapa del mismo número están, la tapa va sobre la taza[cite: 106].
        items.sort((a, b) -> {
            if (a.number != b.number) {
                return Integer.compare(b.number, a.number); // Mayor a menor
            } else {
                // Mismo número: la taza va primero (abajo), la tapa después (arriba)
                if (a.type.equals("cup") && b.type.equals("lid")) return -1;
                if (a.type.equals("lid") && b.type.equals("cup")) return 1;
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

    /**
     * Calcula la altura total de la torre en centimetros usando la formula del Problema J.
     */
    public int height() {
        if (items.isEmpty()) return 0;
        
        int[] baseY = new int[items.size()];
        int maxH = 0;
        
        for (int i = 0; i < items.size(); i++) {
            StackItem current = items.get(i);
            int currentBaseY = 0;
            
            for (int j = 0; j < i; j++) {
                StackItem previous = items.get(j);
                int interactionY = 0;
                
                if (current.getType().equals("cup") && previous.getType().equals("cup")) {
                    if (current.getNumber() < previous.getNumber()) {
                        // Cabe dentro: se apoya en el fondo interior (1 cm)
                        interactionY = baseY[j] + 1; 
                    } else {
                        // No cabe: se apoya en el borde superior
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
        
        // Revisamos cuáles tazas tienen el estado de tapadas
        for (StackItem item : items) {
            if (item.getType().equals("cup")) {
                Cup cup = (Cup) item;
                if (cup.isCovered()) {
                    lided.add(cup.getNumber());
                }
            }
        }
        
        Collections.sort(lided); // Ordenados de menor a mayor
        ok = true;
        return lided.stream().mapToInt(i -> i).toArray();
    }

    public String[][] stackingitems() {
        // Ordenados de base a cima en minúsculas [cite: 108]
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

    public void makelnvisible() { // Se mantiene el nombre exacto del requerimiento [cite: 101]
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

    /**
     * Busca y retorna una taza por su numero, o null si no existe.
     */
    private Cup findCup(int number) {
        for (StackItem item : items) {
            if (item.type.equals("cup") && item.number == number) {
                return (Cup) item;
            }
        }
        return null;
    }

    private void showError(String message) {
        // Usar JOptionPane sólo si el simulador está visible [cite: 116, 117]
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static final int SCALE_PX = 10; // 1 cm = 10 pixeles
    private static final int BASE_Y = 280; // base de la torre en pixeles (cerca del fondo del canvas 300)
    private static final int CENTER_X = 150; // centro horizontal del canvas 300

    private void updateDisplay() {
        if (!isVisible) return;
        
        // Primero ocultamos todo para redibujar limpio
        for (StackItem item : items) {
            item.makeInvisible();
        }
        
        // Calculamos posiciones usando la misma logica de height()
        int[] baseYcm = new int[items.size()];
        
        for (int i = 0; i < items.size(); i++) {
            StackItem current = items.get(i);
            int currentBaseY = 0;
            
            for (int j = 0; j < i; j++) {
                StackItem previous = items.get(j);
                int interactionY = 0;
                
                if (current.getType().equals("cup") && previous.getType().equals("cup")) {
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
        
        // Posicionar todos los items
        for (int i = 0; i < items.size(); i++) {
            StackItem item = items.get(i);
            int heightPx = item.getHeight() * SCALE_PX;
            int widthPx = getItemWidth(item);
            
            int pixelX = CENTER_X - (widthPx / 2);
            int pixelY = BASE_Y - (baseYcm[i] * SCALE_PX) - heightPx;
            
            item.move(pixelX, pixelY);
        }
        
        // Renderizar de mayor a menor: las grandes primero (body+hole),
        // luego las pequenas se dibujan dentro de los huecos
        Integer[] renderOrder = new Integer[items.size()];
        for (int i = 0; i < renderOrder.length; i++) renderOrder[i] = i;
        java.util.Arrays.sort(renderOrder, (a, b) -> items.get(b).getHeight() - items.get(a).getHeight());

        for (int idx : renderOrder) {
            items.get(idx).makeVisible();
        }
    }
    
    /**
     * Retorna el ancho en pixeles de un item para centrarlo.
     */
    private int getItemWidth(StackItem item) {
        if (item.getType().equals("cup")) {
            return item.getNumber() * SCALE_PX * 2; // misma formula que Cup
        } else {
            return 40;
        }
    }
    
    private void drawHeightMarks() {
        int maxMarks = Math.min(maxHeight, BASE_Y / SCALE_PX); // no exceder el canvas
        
        for (int h = 0; h <= maxMarks; h++) {
            Rectangle marca = new Rectangle();
            marca.changeSize(1, 10);
            marca.changeColor("black");
            
            // Mover desde default (70,15) a posicion deseada
            int targetX = 5;
            int targetY = BASE_Y - (h * SCALE_PX);
            marca.moveHorizontal(targetX - 70);
            marca.moveVertical(targetY - 15);
            
            marca.makeVisible();
        }
    }

    /**
     * Intercambia las posiciones de dos elementos en la torre.
     * @param o1 arreglo {tipo, numero} del primer elemento. Ej: {"cup","4"}
     * @param o2 arreglo {tipo, numero} del segundo elemento. Ej: {"lid","4"}
     */
    public void swap(String[] o1, String[] o2) {
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
    
    /**
     * Busca qué tazas tienen su respectiva tapa en la torre y las marca como tapadas.
     */
    public void cover() {
        for (StackItem item : items) {
            if (item.getType().equals("cup")) {
                Cup cup = (Cup) item; // Hacemos un cast (conversión) a Cup
                // Verificamos si existe una tapa con el mismo número en la torre
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
     * Retorna un arreglo {{tipo1, numero1}, {tipo2, numero2}}.
     */
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