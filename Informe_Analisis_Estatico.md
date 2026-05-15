# Informe de Análisis Estático
Proyecto: Stacking Cups
Autores: Rodríguez Villamizar
Herramienta utilizada: PMD 7.23.0 (Ruleset: Java Best Practices)

## 1. Diagnóstico Inicial del Código Source
Tras la ejecución inicial de la herramienta de análisis estático PMD sobre el código fuente y el directorio de pruebas, se identificó un volumen significativo de oportunidades de mejora asociadas a buenas prácticas de programación en Java.

A partir de los hallazgos, el estado técnico del proyecto refleja las siguientes áreas críticas a intervenir:
   ### Prevención de Errores (NullPointerException): 
   La advertencia más recurrente en el proyecto (LiteralsFirstInComparisons) se encuentra distribuida masivamente en clases como Canvas.java y Tower.java. Las comparaciones de cadenas (String) están invocando el método .equals() desde la variable en lugar del literal, exponiendo el código a excepciones si la variable llega a ser nula.
   ### Acoplamiento y Diseño:
   Se detectó la regla LooseCoupling en Canvas.java, indicando que se están utilizando implementaciones concretas (como HashMap) en lugar de sus respectivas interfaces (Map) para las declaraciones. Esto reduce la flexibilidad de la arquitectura.
   ### Deuda Técnica en Pruebas Unitarias:
   Múltiples clases del paquete test (TowerATest, TowerC4Test, etc.) violan el principio de aserción única (UnitTestContainsTooManyAsserts). Tener múltiples aserciones en un solo método de prueba dificulta la trazabilidad y el diagnóstico cuando una prueba falla.
   ### Limpieza y Optimización de Memoria:
   La clase Tower.java presenta variables locales, campos privados y asignaciones que jamás son leídas o utilizadas (UnusedLocalVariable, UnusedPrivateField, UnusedAssignment).
   ### Estilo y Legibilidad:
   Se encontraron oportunidades menores de refactorización, como bucles tradicionales que pueden modernizarse a bucles foreach (ForLoopCanBeForeach en Canvas.java) y la mala práctica de reasignar valores a los parámetros de entrada de un método (AvoidReassigningParameters en ShapeBase.java).

## 2. Acciones de Mitigación y Refactorización
Para solventar los hallazgos del análisis inicial, se aplicó un plan de refactorización sistemático dividido en tres pilares fundamentales:
### A. Robustez y Programación Defensiva (Correctness)
   #### Mitigación de NullPointerExceptions:
   Se implementó la técnica de Literals First en las clases Tower, Canvas y TowerContest. Al posicionar los literales antes de las variables en las comparaciones .equals(), se aseguró la estabilidad del sistema frente a entradas nulas.
   #### Integridad de Parámetros:
   En ShapeBase, se eliminó la reasignación de parámetros en los métodos slowMove, sustituyéndolos por variables locales. Esto garantiza que el estado original de los datos se mantenga íntegro durante la ejecución del método.
   #### Estructuras de Control:
   Se estandarizó el uso de llaves {} en TowerContest, eliminando ambigüedades lógicas en las estructuras if y for.

### B. Optimización del Diseño y Desacoplamiento (Design)
   #### Inyección de Interfaces (Loose Coupling):
   En Canvas.java, se migró la declaración de HashMap hacia la interfaz genérica Map. Este cambio reduce el acoplamiento y facilita el intercambio de implementaciones de colecciones en el futuro.
   #### Modernización de Firmas:
   Se actualizó el método swap en Tower para utilizar argumentos variables (varargs), simplificando la interacción con otros módulos del sistema.
   #### Consistencia de Herencia:
   Se incorporaron anotaciones @Override faltantes, permitiendo que el compilador valide la jerarquía de clases y detecte errores de firma en tiempo de desarrollo.

### C. Eficiencia y Limpieza (Clean Code)
   #### Eliminación de Deuda Técnica:
   Se realizó una purga de código muerto, eliminando campos privados redundantes (como width en Tower), variables locales sin uso e importaciones innecesarias en TowerContest.
   #### Iteración Eficiente:
   Se transformaron los ciclos for manuales en Canvas.java por estructuras for-each, mejorando la legibilidad y eliminando el riesgo de errores por manejo de índices.

## 3. Resultados del Análisis Final
Tras las intervenciones, se ejecutó un segundo escaneo de validación (informe_final_pmd.txt). Los resultados arrojan las siguientes métricas de éxito:
   ### Cumplimiento de Prioridad Alta:
   Se alcanzó un 100% de éxito en la mitigación de reglas críticas (Rojo) en todo el código de dominio del proyecto.
   ### Estabilidad Lógica:
   Las refactorizaciones mantuvieron la integridad de las firmas requeridas (ej. swapToReduce), asegurando que la mejora estética y estructural no alterara el comportamiento esperado del simulador.
   ### Deuda Técnica Residual:
   Las advertencias remanentes se localizan exclusivamente en el paquete test. Se consideran aceptables bajo el criterio de que el código de pruebas prioriza la legibilidad de las aserciones sobre las reglas estrictas de diseño de PMD (como UnitTestContainsTooManyAsserts).

## 4. Conclusión Final
El proceso de análisis estático y refactorización ha transformado el proyecto de una implementación funcional a una solución robusta alineada con los estándares de la industria. La eliminación de riesgos de punteros nulos y el desacoplamiento de clases aseguran que el software sea escalable y fácil de mantener para futuras iteraciones.

**Anexos:**
* [Reporte Inicial PMD](./recursos_estatico/informe_inicial_pmd.txt)
* [Reporte Final de Verificación](./recursos_estatico/informe_final_pmd.txt)