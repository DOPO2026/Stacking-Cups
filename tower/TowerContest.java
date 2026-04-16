package tower;

import java.util.ArrayList;
import java.util.List;

/**
 * Resuelve y simula el Problem J (Stacking Cups) de ICPC 2025.
 * Esta clase NO usa Tower para resolver, solo para simular.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 3
 */
public class TowerContest {

    /**
     * Resuelve el problema de la maraton.
     * Dado n tazas (alturas 1, 3, 5, ..., 2n-1) y una altura objetivo h,
     * retorna el orden de alturas que produce exactamente h, o "impossible".
     * 
     * @param n numero de tazas (1 <= n <= 200000)
     * @param h altura deseada
     * @return String con las alturas separadas por espacio, o "impossible"
     */
    public String solve(int n, long h) {
        if (n == 1) {
            return (h == 1) ? "1" : "impossible";
        }
        
        long hMin = 2L * n - 1;
        long hMax = (long) n * n;
        
        if (h < hMin || h > hMax) return "impossible";
        
        // Intentar con k capas externas (cups n, n-1, ..., n-k+1 anidadas al inicio)
        // Cada capa agrega 1 cm al sub-problema interior
        for (int k = 0; k < n; k++) {
            int m = n - k;                    // tazas restantes: {1, ..., m}
            long targetRemaining = h - k;     // altura objetivo para el sub-problema
            long minRemaining = 2L * m - 1;
            long maxRemaining = (long) m * m;
            
            if (targetRemaining < minRemaining) continue;
            if (targetRemaining > maxRemaining) return "impossible";
            
            long pedestalTarget = targetRemaining - minRemaining;
            
            if (pedestalTarget == 0) {
                return buildSolution(n, k, m, new boolean[m]);
            }
            
            // Greedy: seleccionar tazas para pedestal de {1,...,m-1}
            boolean[] inPedestal = new boolean[m];
            long remaining = pedestalTarget;
            
            for (int j = m - 1; j >= 1; j--) {
                long cupH = 2L * j - 1;
                if (cupH <= remaining) {
                    inPedestal[j - 1] = true;
                    remaining -= cupH;
                }
            }
            
            if (remaining == 0) {
                return buildSolution(n, k, m, inPedestal);
            }
        }
        
        return "impossible";
    }
    
    /**
     * Construye el string de solucion.
     * Orden: [capas externas desc] + [pedestal asc] + [techo + anidadas desc]
     */
    private String buildSolution(int n, int k, int m, boolean[] inPedestal) {
        StringBuilder sb = new StringBuilder();
        
        // 1. Capas externas descendentes: cup n, n-1, ..., n-k+1
        for (int i = n; i >= n - k + 1; i--) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(2L * i - 1);
        }
        
        // 2. Pedestal ascendente: tazas seleccionadas de 1..m-1
        for (int j = 1; j <= m - 1; j++) {
            if (inPedestal[j - 1]) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(2L * j - 1);
            }
        }
        
        // 3. Taza techo (cup m) + no-pedestal descendentes
        if (sb.length() > 0) sb.append(" ");
        sb.append(2L * m - 1);
        
        for (int j = m - 1; j >= 1; j--) {
            if (!inPedestal[j - 1]) {
                sb.append(" ");
                sb.append(2L * j - 1);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Simula la solucion graficamente usando la clase Tower.
     * @param n numero de tazas
     * @param h altura deseada
     */
    public void simulate(int n, long h) {
        String solution = solve(n, h);
        
        if (solution.equals("impossible")) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "impossible\nNo es posible construir una torre de altura " + h + " con " + n + " tazas.",
                "Stacking Cups", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String[] heights = solution.split(" ");
        Tower tower = new Tower(300, (int) Math.min(h, 100));
        
        for (String heightStr : heights) {
            long cupHeight = Long.parseLong(heightStr);
            int cupIndex = (int) ((cupHeight + 1) / 2);
            tower.pushCup(cupIndex);
        }
        
        tower.makeVisible();
        
        if (!tower.ok()) {
            javax.swing.JOptionPane.showMessageDialog(null, 
                "La solucion existe pero no cabe en pantalla.\nSolucion: " + solution,
                "Stacking Cups", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
}