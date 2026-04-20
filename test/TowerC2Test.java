package test;

import tower.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Clase de pruebas unitarias para el Ciclo 2 de Tower.
 *
 * @author Rodriguez-Villamizar
 * @version 01/03/26
 */
public class TowerC2Test {
    @Test
    public void testConstructorAutomatico() {
        Tower tower = new Tower(3);
        assertTrue("La torre debería haberse creado correctamente", tower.ok());
    }

    @Test
    public void testSwapValido() {
        Tower tower = new Tower(3);
        tower.swap(new String[]{"cup", "1"}, new String[]{"cup", "3"});
        assertTrue("El intercambio debería ser exitoso", tower.ok());
    }

    @Test
    public void testSwapInvalido() {
        Tower tower = new Tower(2);
        tower.swap(new String[]{"cup", "1"}, new String[]{"cup", "5"});
        assertFalse("El intercambio debería fallar porque la taza 5 no existe", tower.ok());
    }

    @Test
    public void testCoverYLidedCups() {
        Tower tower = new Tower(2);
        tower.pushLid(1);
        tower.cover();
        int[] tapadas = tower.lidedCups();
        assertEquals("Debería haber 1 taza tapada", 1, tapadas.length);
        assertEquals("La taza tapada debería ser la número 1", 1, tapadas[0]);
    }
}
