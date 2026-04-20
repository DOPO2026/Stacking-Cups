package test;

import tower.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias para TowerContest (Ciclo 3).
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 3
 */
public class TowerContestTest {

    @Test
    public void testSampleInput1() {
        TowerContest tc = new TowerContest();
        String result = tc.solve(4, 9);
        assertNotEquals("impossible", result);
        assertEquals("La altura debe ser 9", 9, calcHeight(result));
        assertEquals("Debe usar 4 tazas", 4, result.split(" ").length);
    }
    
    @Test
    public void testSampleInput2() {
        TowerContest tc = new TowerContest();
        assertEquals("impossible", tc.solve(4, 100));
    }

    @Test
    public void testMinHeight() {
        TowerContest tc = new TowerContest();
        for (int n = 1; n <= 6; n++) {
            long hMin = 2L * n - 1;
            String r = tc.solve(n, hMin);
            assertNotEquals("n=" + n + " hMin debe ser alcanzable", "impossible", r);
            assertEquals(hMin, calcHeight(r));
        }
    }
    
    @Test
    public void testMaxHeight() {
        TowerContest tc = new TowerContest();
        for (int n = 1; n <= 6; n++) {
            long hMax = (long) n * n;
            String r = tc.solve(n, hMax);
            assertNotEquals("n=" + n + " hMax debe ser alcanzable", "impossible", r);
            assertEquals(hMax, calcHeight(r));
        }
    }
    
    @Test
    public void testBelowMin() {
        TowerContest tc = new TowerContest();
        assertEquals("impossible", tc.solve(4, 6));
        assertEquals("impossible", tc.solve(3, 4));
    }
    
    @Test
    public void testAboveMax() {
        TowerContest tc = new TowerContest();
        assertEquals("impossible", tc.solve(4, 17));
        assertEquals("impossible", tc.solve(3, 10));
    }
    
    @Test
    public void testSingleCup() {
        TowerContest tc = new TowerContest();
        assertEquals("1", tc.solve(1, 1));
        assertEquals("impossible", tc.solve(1, 2));
    }
    
    @Test
    public void testTwoCups() {
        TowerContest tc = new TowerContest();
        String r3 = tc.solve(2, 3);
        assertNotEquals("impossible", r3);
        assertEquals(3, calcHeight(r3));
        
        String r4 = tc.solve(2, 4);
        assertNotEquals("impossible", r4);
        assertEquals(4, calcHeight(r4));
    }
    
    @Test
    public void testImpossibleN3() {
        TowerContest tc = new TowerContest();
        assertEquals("impossible", tc.solve(3, 7));
    }
    
    @Test
    public void testAllReachableN4() {
        TowerContest tc = new TowerContest();
        for (long h = 7; h <= 16; h++) {
            String result = tc.solve(4, h);
            if (!result.equals("impossible")) {
                assertEquals("Altura incorrecta para h=" + h, h, calcHeight(result));
                assertEquals("Debe usar 4 tazas", 4, result.split(" ").length);
            }
        }
    }
    
    @Test
    public void testLargeNMinHeight() {
        TowerContest tc = new TowerContest();
        String result = tc.solve(10000, 2L * 10000 - 1);
        assertNotEquals("impossible", result);
    }
    
    @Test
    public void testUsesAllCups() {
        TowerContest tc = new TowerContest();
        for (int n = 2; n <= 5; n++) {
            String r = tc.solve(n, 2L * n - 1 + 1);
            if (!r.equals("impossible")) {
                assertEquals(n, r.split(" ").length);
            }
        }
    }

    private long calcHeight(String sequence) {
        String[] parts = sequence.split(" ");
        int n = parts.length;
        long[] heights = new long[n];
        int[] cupIdx = new int[n];
        for (int i = 0; i < n; i++) {
            heights[i] = Long.parseLong(parts[i].trim());
            cupIdx[i] = (int) ((heights[i] + 1) / 2);
        }
        long[] baseY = new long[n];
        long maxH = 0;
        for (int i = 0; i < n; i++) {
            long currentBase = 0;
            for (int j = 0; j < i; j++) {
                long interaction = (cupIdx[i] < cupIdx[j]) ? baseY[j] + 1 : baseY[j] + heights[j];
                if (interaction > currentBase) currentBase = interaction;
            }
            baseY[i] = currentBase;
            long top = currentBase + heights[i];
            if (top > maxH) maxH = top;
        }
        return maxH;
    }
}
