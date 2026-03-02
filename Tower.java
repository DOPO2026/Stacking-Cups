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

    public void pushCup(int i) {
        if (!containsItem("cup", i)) {
            String[] colores = {"red", "yellow", "blue", "green", "magenta", "black"};
            String color = colores[i % colores.length];
            
            Cup nuevaTaza = new Cup(i, color);
            if (isVisible) nuevaTaza.makeVisible();

            ok = true;
        } else {
            ok = false;
            showError("La taza " + i + " ya existe en la torre.");
        }
    }
    
    public void popCup() {
        boolean found = false;
        for (int j = items.size() - 1; j >= 0; j--) {
            if (items.get(j).type.equals("cup")) {
                items.remove(j);
                found = true;
                break;
            }
        }
    
            if (found) {
                ok = true;
                // updateDisplay();
            } else {
                ok = false;
            if (isVisible) {
                javax.swing.JOptionPane.showMessageDialog(null, 
                    "Error: No hay tazas para eliminar de la torre.");
            }
        }
    }


    public void removeCup(int i) {
        ok = items.removeIf(item -> item.type.equals("cup") && item.number == i);
        if (ok) {
            updateDisplay();
        } else {
            showError("No se pudo remover: La taza " + i + " no existe."); 
        }
    }

    public void pushLid(int i) {
        if (!containsItem("lid", i)) {
            String[] colores = {"red", "yellow", "blue", "green", "magenta", "black"};
            String color = colores[i % colores.length];

            // Ahora creamos una Lid (que es un StackItem)
            Lid nuevaTapa = new Lid(i, color);
            if (isVisible) nuevaTapa.makeVisible();


            ok = true;
        } else {
            ok = false;
            showError("La tapa " + i + " ya existe en la torre.");
        }
    }

    public void popLid() {
        for (int j = items.size() - 1; j >= 0; j--) {
            if (items.get(j).type.equals("lid")) {
                items.remove(j);
                ok = true;
                updateDisplay();
                return;
            }
        }
        ok = false;
    }

    public void removeLid(int i) {
        ok = items.removeIf(item -> item.type.equals("lid") && item.number == i);
        if (ok) updateDisplay();
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

    public int height() {
        int totalHeight = 0;
        // La lógica de anidación (Problem J) debe aplicarse aquí si se requiere físicamente.
        // Por ahora suma las alturas individuales: tazas (2i-1) y tapas (1)
        for (StackItem item : items) {
            if (item.type.equals("cup")) {
                totalHeight += (2 * item.number - 1);
            } else if (item.type.equals("lid")) {
                totalHeight += 1;
            }
        }
        ok = true;
        return totalHeight;
    }

    public int[] lidedCups() {
        List<Integer> lided = new ArrayList<>();
        // Revisa si una tapa está inmediatamente después de su taza correspondiente
        for (int j = 0; j < items.size() - 1; j++) {
            StackItem current = items.get(j);
            StackItem next = items.get(j + 1);
            if (current.type.equals("cup") && next.type.equals("lid") && current.number == next.number) {
                lided.add(current.number);
            }
        }
        Collections.sort(lided); // Ordenados de menor a mayor [cite: 107]
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
        ok = true;
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

    private void showError(String message) {
        // Usar JOptionPane sólo si el simulador está visible [cite: 116, 117]
        if (isVisible) {
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDisplay() {
        if (!isVisible) return;
     
    }
    
    private void drawHeightMarks() {
        int escala = 10; // 1 cm = 10 píxeles
        int base = 500;  // Eje Y de la base
        
        for (int h = 0; h <= maxHeight; h++) {
            Rectangle marca = new Rectangle();
            marca.changeSize(1, 10); // Línea de 1px de alto y 10px de ancho 
            marca.changeColor("black");
            
            // Ajustar posición: El default es (70, 15). 
            // Lo movemos a la posición deseada restando/sumando la diferencia.
            marca.moveHorizontal(10 - 70); // Queremos que X sea 10
            marca.moveVertical((base - (h * escala)) - 15);
            
            marca.makeVisible();
        }
    }
    

}