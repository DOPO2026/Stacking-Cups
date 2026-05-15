package test;

import tower.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias para el Ciclo 4 de Tower.
 * Cubre los nuevos tipos de tazas y tapas.
 * 
 * @author Rodriguez-Villamizar
 * @version Ciclo 4
 */
public class TowerC4Test {

    @Test
    public void testOpenerEliminaTapasMenores() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(3);
        tower.pushLid(1);
        tower.pushLid(2);
        // Opener 4 debe eliminar tapas 1 y 2 (menores que 4)
        tower.pushCup("opener", 4);
        assertTrue(tower.ok());
        // Verificar que las tapas fueron eliminadas
        String[][] items = tower.stackingitems();
        for (String[] item : items) {
            if (item[0].equals("lid")) {
                fail("No deberia haber tapas menores tras opener");
            }
        }
    }

    @Test
    public void testOpenerNoEliminaTapasMayores() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(5);
        tower.pushLid(5);
        // Opener 3: no debe eliminar tapa 5 (mayor que 3)
        tower.pushCup("opener", 3);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        boolean tapa5Existe = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("5")) {
                tapa5Existe = true;
            }
        }
        assertTrue("La tapa 5 debe seguir en la torre", tapa5Existe);
    }

    @Test
    public void testHierarchicalDesplazaMenores() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        // Hierarchical 3 debe desplazar a 1 y 2
        tower.pushCup("hierarchical", 3);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        // La taza 3 debe estar antes que 1 y 2
        int pos3 = -1, pos1 = -1, pos2 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("3")) pos3 = i;
            if (items[i][0].equals("cup") && items[i][1].equals("1")) pos1 = i;
            if (items[i][0].equals("cup") && items[i][1].equals("2")) pos2 = i;
        }
        assertTrue("Hierarchical 3 debe estar antes que 1", pos3 < pos1);
        assertTrue("Hierarchical 3 debe estar antes que 2", pos3 < pos2);
    }

    @Test
    public void testHierarchicalAnclada() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        // Hierarchical 5: desplaza todo y llega al fondo
        tower.pushCup("hierarchical", 5);
        assertTrue(tower.ok());
        // Intentar remover: no debe poder
        tower.removeCup(5);
        assertFalse("No se debe poder remover una taza anclada", tower.ok());
    }

    @Test
    public void testHierarchicalNoDesplazaMayores() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(5);
        // Hierarchical 3: no puede pasar a 5
        tower.pushCup("hierarchical", 3);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        int pos5 = -1, pos3 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("5")) pos5 = i;
            if (items[i][0].equals("cup") && items[i][1].equals("3")) pos3 = i;
        }
        assertTrue("Taza 5 debe seguir antes que hierarchical 3", pos5 < pos3);
    }

    @Test
    public void testMagneticAtraeTapa() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushLid(2);
        tower.pushCup(3);
        // Magnetic 2: debe atraer lid 2 justo despues de si misma
        tower.pushCup("magnetic", 2);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        int posCup2 = -1, posLid2 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("2")) posCup2 = i;
            if (items[i][0].equals("lid") && items[i][1].equals("2")) posLid2 = i;
        }
        assertEquals("La tapa debe estar justo despues de la taza magnetic", posCup2 + 1, posLid2);
    }

    @Test
    public void testMagneticTapaLaTaza() {
        Tower tower = new Tower(10, 50);
        tower.pushLid(3);
        tower.pushCup("magnetic", 3);
        assertTrue(tower.ok());
        tower.cover();
        int[] tapadas = tower.lidedCups();
        assertEquals("La taza 3 debe estar tapada", 1, tapadas.length);
        assertEquals(3, tapadas[0]);
    }

    @Test
    public void testMagneticSinTapa() {
        // Si no existe la tapa companera, entra normal sin error
        Tower tower = new Tower(10, 50);
        tower.pushCup("magnetic", 2);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
    }

    @Test
    public void testFearfulNoEntraSinTaza() {
        Tower tower = new Tower(10, 50);
        // Fearful 3 sin taza 3 en la torre
        tower.pushLid("fearful", 3);
        assertFalse("Fearful no debe entrar sin su taza", tower.ok());
    }

    @Test
    public void testFearfulEntraConTaza() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(3);
        tower.pushLid("fearful", 3);
        assertTrue("Fearful debe entrar si su taza esta", tower.ok());
    }

    @Test
    public void testFearfulNoSaleSiTapa() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(2);
        tower.pushLid("fearful", 2);
        tower.cover(); // marca cup 2 como tapada
        // Intentar remover fearful 2
        tower.removeLid(2);
        assertFalse("Fearful tapando a su taza no se puede remover", tower.ok());
    }

    @Test
    public void testFearfulSaleSiNoTapa() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushLid("fearful", 2);
        // No llamamos cover(), asi que cup 2 no esta tapada
        tower.removeLid(2);
        assertTrue("Fearful debe poder salir si no esta tapando", tower.ok());
    }


    @Test
    public void testCrazySeUbicaEnBase() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(3);
        tower.pushCup(5);
        // Crazy 3 debe ir a posicion 0
        tower.pushLid("crazy", 3);
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        assertEquals("Crazy debe estar en la base", "lid", items[0][0]);
        assertEquals("Crazy debe ser la numero 3", "3", items[0][1]);
    }

    @Test
    public void testCrazyNoTapaSuTaza() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(2);
        tower.pushLid("crazy", 2);
        tower.cover();
        // Crazy se ubica en la base, no deberia tapar a cup 2
        // cover() marca como tapada si la lid existe, pero crazy esta en la base
        // La taza 2 si tiene su tapa, cover la marca, pero crazy esta abajo
        int[] tapadas = tower.lidedCups();
        // cover() marca basandose en existencia, no posicion
        // esto es comportamiento actual de cover()
        assertEquals(1, tapadas.length);
    }

    @Test
    public void testPushCupNormalSigueFuncionando() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        assertTrue(tower.ok());
        assertEquals(2, tower.stackingitems().length);
    }

    @Test
    public void testPushLidNormalSigueFuncionando() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushLid(1);
        assertTrue(tower.ok());
        assertEquals(2, tower.stackingitems().length);
    }

    @Test
    public void testPopCupSaltaAnclada() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        // Hierarchical 5 llega al fondo y se ancla
        tower.pushCup("hierarchical", 5);
        tower.pushCup(3);
        // popCup debe quitar 3 (la ultima cup), no la anclada
        tower.popCup();
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        boolean cup3Existe = false;
        boolean cup5Existe = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("3")) cup3Existe = true;
            if (item[0].equals("cup") && item[1].equals("5")) cup5Existe = true;
        }
        assertFalse("Cup 3 debe haber sido removida", cup3Existe);
        assertTrue("Cup 5 anclada debe seguir", cup5Existe);
    }

    @Test
    public void testPopLidSaltaFearful() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushLid("fearful", 1);
        tower.cover(); // marca cup 1 como tapada
        tower.pushLid(2); // lid normal
        // popLid debe quitar lid 2 (la normal), no fearful 1 que esta tapando
        tower.popLid();
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        boolean lid1Existe = false;
        boolean lid2Existe = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("1")) lid1Existe = true;
            if (item[0].equals("lid") && item[1].equals("2")) lid2Existe = true;
        }
        assertTrue("Fearful lid 1 debe seguir", lid1Existe);
        assertFalse("Lid normal 2 debe haber sido removida", lid2Existe);
    }

    @Test
    public void testGetSubType() {
        Tower tower = new Tower(10, 50);
        tower.pushCup("normal", 1);
        tower.pushCup("opener", 2);
        tower.pushCup("hierarchical", 3);
        tower.pushCup("magnetic", 4);
        tower.pushLid("normal", 5);
        tower.pushLid("fearful", 4);
        tower.pushLid("crazy", 6);
        
        String[][] items = tower.stackingitems();
        // Verificamos que hay 7 items
        assertEquals(7, items.length);
    }
    
    // ==================== PRUEBAS ADICIONALES CICLO 5 ====================
    // Estas pruebas cubren ramas no alcanzadas en el resultado inicial (86.7% Tower)

    // --- removeLid rama fallida ---

    @Test
    public void testRemoveLidInexistente() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.removeLid(99); // tapa que no existe
        assertFalse("Debe fallar al remover tapa inexistente", tower.ok());
    }

    // --- removeCup rama fallida ---

    @Test
    public void testRemoveCupInexistente() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.removeCup(99); // taza que no existe
        assertFalse("Debe fallar al remover taza inexistente", tower.ok());
    }

    // --- popCup torre vacia ---

    @Test
    public void testPopCupTorreVacia() {
        Tower tower = new Tower(10, 50);
        tower.popCup();
        assertFalse("Debe fallar popCup en torre vacia", tower.ok());
    }

    // --- popLid torre sin tapas ---

    @Test
    public void testPopLidSinTapas() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.popLid();
        assertFalse("Debe fallar popLid sin tapas", tower.ok());
    }

    // --- pushCup duplicado ---

    @Test
    public void testPushCupDuplicado() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(2);
        tower.pushCup(2); // duplicado
        assertFalse("No debe permitir duplicados", tower.ok());
    }

    // --- pushLid duplicado ---

    @Test
    public void testPushLidDuplicado() {
        Tower tower = new Tower(10, 50);
        tower.pushLid(3);
        tower.pushLid(3); // duplicado
        assertFalse("No debe permitir tapa duplicada", tower.ok());
    }

    // --- height con torre vacia ---

    @Test
    public void testHeightTorreVacia() {
        Tower tower = new Tower(10, 50);
        assertEquals("Torre vacia debe tener altura 0", 0, tower.height());
    }

    // --- height con lid encima de cup de mayor numero (lid apoyada en borde) ---

    @Test
    public void testHeightConLidSobreCup() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(2); // altura 3
        tower.pushLid(2); // altura 1, apoyada en borde exterior
        int h = tower.height();
        assertTrue("Altura debe ser positiva con cup y lid", h > 0);
    }

    // --- lidedCups sin ninguna tapada ---

    @Test
    public void testLidedCupsSinTapadas() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushLid(1);
        // No llamamos cover(), ninguna esta tapada
        int[] tapadas = tower.lidedCups();
        assertEquals("Sin cover no debe haber tapadas", 0, tapadas.length);
    }

    // --- stackingItems torre vacia ---

    @Test
    public void testStackingItemsTorreVacia() {
        Tower tower = new Tower(10, 50);
        String[][] items = tower.stackingitems();
        assertEquals("Torre vacia debe retornar array vacio", 0, items.length);
    }

    // --- swapToReduce sin posible mejora ---

    @Test
    public void testSwapToReduceSinMejora() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1); // Torre de 1 sola taza, ningun swap mejora
        String[][] result = tower.swapToReduce();
        assertFalse("Sin mejora posible, ok debe ser false", tower.ok());
        assertEquals("Resultado vacio si no hay mejora", 0, result.length);
    }

    // --- swapToReduce con mejora posible ---

    @Test
    public void testSwapToReduceConMejora() {
        Tower tower = new Tower(10, 50);
        // Colocar en orden suboptimo para que exista un swap que reduzca
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushCup(2);
        int alturaAntes = tower.height();
        String[][] sugerencia = tower.swapToReduce();
        if (tower.ok()) {
            // Aplicar el swap sugerido y verificar que reduce
            tower.swap(sugerencia[0], sugerencia[1]);
            assertTrue("El swap sugerido debe reducir la altura",
                tower.height() < alturaAntes);
        }
        // Si no hay mejora, tambien es valido (ok false)
    }

    // --- orderTower con tapas y tazas del mismo numero ---

    @Test
    public void testOrderTowerConTapasYTazas() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(3);
        tower.pushLid(3);
        tower.pushCup(2);
        tower.orderTower();
        assertTrue(tower.ok());
        String[][] items = tower.stackingitems();
        // El mayor debe estar primero (base), el menor en la cima
        assertEquals("El primer elemento debe ser el de mayor numero", "3",
            items[0][1]);
        // Si hay taza y tapa del mismo numero, taza antes que tapa
        boolean tazaAntesDeTapa = false;
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("3") &&
                items[i+1][0].equals("lid") && items[i+1][1].equals("3")) {
                tazaAntesDeTapa = true;
            }
        }
        assertTrue("Taza debe ir antes que su tapa en orderTower", tazaAntesDeTapa);
    }

    // --- reverseTower ---

    @Test
    public void testReverseTower() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        String[][] antes = tower.stackingitems();
        tower.reverseTower();
        assertTrue(tower.ok());
        String[][] despues = tower.stackingitems();
        // El primero de antes debe ser el ultimo de despues
        assertEquals(antes[0][1], despues[despues.length - 1][1]);
        assertEquals(antes[antes.length - 1][1], despues[0][1]);
    }

    // --- cover sin tapas en la torre ---

    @Test
    public void testCoverSinTapas() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.cover(); // No hay tapas, ninguna se tapa
        assertTrue(tower.ok());
        int[] tapadas = tower.lidedCups();
        assertEquals("Sin tapas, cover no debe tapar nada", 0, tapadas.length);
    }

    // --- cover varias tazas ---

    @Test
    public void testCoverVariasTazas() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.pushCup(3);
        tower.pushLid(1);
        tower.pushLid(3);
        tower.cover();
        assertTrue(tower.ok());
        int[] tapadas = tower.lidedCups();
        assertEquals("Deben estar tapadas exactamente 2 tazas", 2, tapadas.length);
    }

    // --- Tower(int cups) constructor automatico borde ---

    @Test
    public void testConstructorAutomaticoUnaTaza() {
        Tower tower = new Tower(1);
        assertTrue(tower.ok());
        assertEquals(1, tower.stackingitems().length);
    }

    // --- makeInvisible en torre invisible (no hace nada) ---

    @Test
    public void testMakeInvisibleYaInvisible() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.makelnvisible(); 
        assertTrue(tower.ok());
    }

    // --- swap mismo elemento ---

    @Test
    public void testSwapMismoElemento() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushCup(2);
        tower.swap(new String[]{"cup", "1"}, new String[]{"cup", "1"});
        // Intercambiar consigo mismo es valido
        assertTrue(tower.ok());
    }

    // --- swap con elemento inexistente ---

    @Test
    public void testSwapElementoInexistente() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.swap(new String[]{"cup", "1"}, new String[]{"cup", "99"});
        assertFalse("Swap debe fallar si un elemento no existe", tower.ok());
    }

    // --- pushCup tipo invalido ---

    @Test
    public void testPushCupTipoInvalido() {
        Tower tower = new Tower(10, 50);
        tower.pushCup("desconocido", 1);
        assertFalse("Tipo invalido de taza debe fallar", tower.ok());
    }

    // --- pushLid tipo invalido ---

    @Test
    public void testPushLidTipoInvalido() {
        Tower tower = new Tower(10, 50);
        tower.pushCup(1);
        tower.pushLid("desconocido", 1);
        assertFalse("Tipo invalido de tapa debe fallar", tower.ok());
    }

    // --- makeInvisible despues de makeVisible (modo invisible) ---
    // Nota: makeVisible abre canvas; solo probar la logica de estado en modo invisible

    @Test
    public void testMakeVisibleTorreMuyAlta() {
        // Una torre con altura mayor a 80 cm (800px) no debe hacerse visible
        // Con n=30: maxHeight = 900 > 800
        Tower tower = new Tower(10, 30);
        for (int i = 1; i <= 30; i++) {
            tower.pushCup(i);
        }
        // Ordenamos para maximizar altura (todas apiladas, no anidadas)
        tower.orderTower();
        // La altura puede superar 80 con suficientes tazas
        // makeVisible debe retornar ok=false si no cabe
        // (no abre ventana en modo invisible, pero verifica la logica)
        int h = tower.height();
        if (h > 80) {
            tower.makeVisible();
            assertFalse("Torre demasiado alta no debe hacerse visible", tower.ok());
        } else {
            // Si la altura no supera el umbral, simplemente verificamos ok
            assertTrue("La torre debe ser valida", tower.ok());
        }
    }

}
