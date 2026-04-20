package test;

import tower.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Clase de prueba colectiva para el Ciclo 4.
 * Los nombres de los casos incluyen la identificacion de los autores.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class TowerCC4Test {

    @Test
    public void accordingRVShouldOpenerRemoveMultipleLids() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(5);
        tower.pushLid(1);
        tower.pushLid(2);
        tower.pushLid(3);
        tower.pushLid(4);
        tower.pushCup("opener", 5);
        // Opener 5 no elimina nada (ya existe cup 5)
        // Necesitamos un opener nuevo
        Tower t2 = new Tower(10, 50);
        t2.pushCup(1);
        t2.pushLid(1);
        t2.pushLid(2);
        t2.pushLid(3);
        t2.pushCup("opener", 4);
        assertTrue(t2.ok());
        String[][] items = t2.stackingitems();
        // Solo deben quedar: cup1, cup4 (opener), y lids que no sean menores que 4
        // Lids 1,2,3 son menores que 4, deben ser eliminadas
        for (String[] item : items) {
            if (item[0].equals("lid")) {
                int num = Integer.parseInt(item[1]);
                assertTrue("Solo lids >= 4 deben quedar", num >= 4);
            }
        }
    }

    @Test
    public void accordingRVShouldHierarchicalNotAnchorIfBlocked() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(5); // mayor que hierarchical 3
        tower.pushCup(1);
        tower.pushCup("hierarchical", 3);
        assertTrue(tower.ok());
        // Hierarchical 3 no puede pasar a cup 5, no llega al fondo
        tower.removeCup(3);
        assertTrue("Hierarchical no anclada debe poder removerse", tower.ok());
    }

    @Test
    public void accordingRVShouldCrazyLidAffectHeight() {
        Tower tower = new Tower(10, 50);
        tower.pushLid("crazy", 1); // va a la base
        tower.pushCup(3);
        int h = tower.height();
        // La crazy lid en la base agrega 1 cm debajo de todo
        assertTrue("La altura debe ser mayor que solo la taza", h > 5);
    }

    @Test
    public void accordingRVShouldMagneticWorkWithMultipleCups() {
        Tower tower = new Tower(10, 50);
        tower.pushLid(1);
        tower.pushLid(2);
        tower.pushCup("magnetic", 1); // atrae lid 1
        tower.pushCup("magnetic", 2); // atrae lid 2
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        // Verificar que cada lid esta justo despues de su cup
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i][0].equals("cup")) {
                String cupNum = items[i][1];
                if (i + 1 < items.length && items[i + 1][0].equals("lid")) {
                    assertEquals("Lid debe tener mismo numero que cup magnetic",
                        cupNum, items[i + 1][1]);
                }
            }
        }
    }

    @Test
    public void accordingRVShouldFearfulPopSkipAllFearfuls() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid("fearful", 1);
        tower.pushLid("fearful", 2);
        tower.cover();
        // Ambas fearful estan tapando, popLid no debe poder quitar ninguna
        tower.popLid();
        assertFalse("No debe haber tapas removibles", tower.ok());
    }
}
