package test;

import tower.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de aceptacion que evidencian lo mejor del proyecto.
 * Incluyen esperas y preguntas al usuario sobre si acepta.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class TowerATest {

    /**
     * Prueba de aceptacion 1: Demuestra los tipos especiales de tazas.
     * Crea una torre con tazas normales, opener, hierarchical y magnetic,
     * mostrando como cada tipo afecta la torre visualmente.
     */
    @Test
    public void testAcceptanceTiposDeTazas() throws InterruptedException {
        Tower tower = new Tower(300, 30);
        tower.makeVisible();
        Thread.sleep(1000);

        // Paso 1: agregar tazas normales
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        Thread.sleep(1500);

        // Paso 2: agregar tapas que seran eliminadas por opener
        tower.pushLid(1);
        tower.pushLid(2);
        Thread.sleep(1500);

        // Paso 3: opener 4 elimina tapas menores
        tower.pushCup("opener", 4);
        Thread.sleep(1500);

        // Paso 4: agregar tapa y magnetic que la atrae
        tower.pushLid(5);
        Thread.sleep(1000);
        tower.pushCup("magnetic", 5);
        Thread.sleep(1500);

        // Paso 5: hierarchical que se ancla al fondo
        tower.pushCup("hierarchical", 6);
        Thread.sleep(1500);

        // Verificaciones logicas
        assertTrue("La torre debe estar ok", tower.ok());
        int h = tower.height();
        assertTrue("La altura debe ser positiva", h > 0);

        // Pregunta al usuario
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
            "¿La torre muestra correctamente los tipos de tazas?\n" +
            "- Opener elimino las tapas menores\n" +
            "- Magnetic atrajo su tapa\n" +
            "- Hierarchical se ubico en la base\n" +
            "Altura total: " + h + " cm",
            "Prueba de Aceptacion 1", javax.swing.JOptionPane.YES_NO_OPTION);
        assertEquals("El usuario debe aceptar la prueba", 
            javax.swing.JOptionPane.YES_OPTION, respuesta);
    }

    /**
     * Prueba de aceptacion 2: Demuestra los tipos especiales de tapas
     * y la solucion del problema de la maraton.
     */
    @Test
    public void testAcceptanceTiposDeTapasYContest() throws InterruptedException {
        // Parte A: tipos de tapas
        Tower tower = new Tower(300, 30);
        tower.makeVisible();
        Thread.sleep(1000);

        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        Thread.sleep(1000);

        // Crazy lid va a la base
        tower.pushLid("crazy", 1);
        Thread.sleep(1500);

        // Fearful lid necesita su taza
        tower.pushLid("fearful", 2);
        Thread.sleep(1000);

        // Cover y verificar
        tower.cover();
        Thread.sleep(1500);

        // Intentar quitar fearful (debe fallar si esta tapando)
        tower.removeLid(2);
        boolean fearfulProtegida = !tower.ok();
        Thread.sleep(1000);

        // Parte B: resolver el problema de la maraton
        TowerContest tc = new TowerContest();
        tc.simulate(4, 9);
        Thread.sleep(2000);

        // Pregunta al usuario
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(null,
            "¿Se observa correctamente?\n" +
            "- Crazy lid se ubico en la base de la torre\n" +
            "- Fearful lid no se pudo quitar: " + fearfulProtegida + "\n" +
            "- La simulacion del problema muestra la torre de altura 9",
            "Prueba de Aceptacion 2", javax.swing.JOptionPane.YES_NO_OPTION);
        assertEquals("El usuario debe aceptar la prueba",
            javax.swing.JOptionPane.YES_OPTION, respuesta);
    }
}
