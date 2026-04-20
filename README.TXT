# Stacking Cups - DOPO

Este proyecto es un simulador interactivo desarrollado en Java para el curso de Desarrollo Orientado por Objetos en la Escuela Colombiana de Ingeniería Julio Garavito. 

El simulador está inspirado en el Problem J de la ICPC World Finals 2025, pero extiende la complejidad original añadiendo la gestión de tapas (lids) con comportamientos específicos para interactuar con las tazas (cups).

## Características Principales:

El simulador permite al usuario realizar las siguientes acciones sobre una torre de tazas y tapas:
- Gestión de la Torre: Crear la torre y consultar información (altura, elementos apilados, etc.).
- Gestión de Tazas y Tapas: Operaciones de apilar (push), desapilar (pop) y remover (remove).
- Reorganización: Funciones para ordenar (order), invertir (reverse), intercambiar (swap) y cubrir (cover).
- Resolución (Solver): Funcionalidades para simular y encontrar la configuración exacta que cumpla con una altura objetivo, basándose en el problema de la maratón.
- Interfaz Gráfica: Representación visual de la torre mediante la clase Canvas.

## Tecnologías y Herramientas Utilizadas:

- Lenguaje: Java
- IDE: BlueJ, Eclipse
- Pruebas Unitarias y de Aceptación: JUnit 4
- Análisis Dinámico (Cobertura de Código): IDEeclipse
- Análisis Estático (Calidad de Código): PMD
- Modelado y Diseño: Astah

## Análisis General y Decisiones de Refactoring

Durante el ciclo de refactoring y migración a Eclipse, se llevaron a cabo los siguientes procesos:

- Análisis Dinámico: Se logró un porcentaje de cobertura total final de 94.2% frente al 90.4% inicial, superando la meta establecida del 75%. Para lograr este incremento, se diseñaron e implementaron nuevas pruebas unitarias enfocadas en evaluar rutas de código que inicialmente no eran alcanzadas. Esto permitió elevar la cobertura de la clase principal Tower del 86.7% al 97.8%.
  
- Análisis Estático:

## Estructura de Entregables

En este repositorio se encuentran adjuntos los entregables correspondientes al cierre del proyecto:
1. Código fuente completo.
2. Informe del análisis dinámico (Inicial y Final).
3. Informe del análisis estático (Inicial y Final).
4. Diseño en Astah.

# Retrospectiva del Proyecto:

---

### 1. ¿Cuáles fueron los mini-ciclos definidos? Justifíquenlos.
- **Migración y Configuración de Entorno:** Paso de BlueJ a Eclipse para habilitar herramientas profesionales de análisis y control de versiones.
- **Análisis Dinámico e Incremento de Cobertura:** Uso de JaCoCo para asegurar que la lógica de las tazas y la torre estuviera probada por encima del 75%.
- **Análisis Estático y Refactorización:** Ejecución de PMD para identificar y corregir malas prácticas de programación y mejorar la legibilidad.
- **Documentación y Gestión de Configuración:** Finalización de diagramas en Astah y estructuración del repositorio en GitHub.

### 2. ¿Cuál es el estado actual del proyecto en términos de mini-ciclos? ¿por qué?
El proyecto está terminado y completo. Porque se han completado todos los ciclos definidos: el código compila sin errores en Eclipse, la cobertura de dominio alcanzó un 96.5% (superando la meta), se resolvieron las advertencias de PMD de prioridad alta y la documentación en el repositorio está completa.

### 3. ¿Cuál fue el tiempo total invertido por cada uno de ustedes? (Horas/Hombre)
- **Natalia Andrea Rodríguez Torres:** 9 horas.
- **Daniel José Villamizar Castellanos:** 9 horas.

### 4. ¿Cuál consideran fue el mayor logro? ¿Por qué?
El mayor logro fue alcanzar un **97.8% de cobertura en la clase Tower**. Esto es significativo porque garantiza que casi la totalidad de la lógica central del problema de la maratón (apilamiento, validaciones y cálculos de altura) ha sido verificada mediante pruebas automatizadas, minimizando errores en la simulación.

### 5. ¿Cuál consideran que fue el mayor problema técnico? ¿Qué hicieron para resolverlo?
El mayor problema fue la **incompatibilidad de visibilidad de los paquetes de la interfaz gráfica** (AWT/Swing) al migrar a Eclipse, lo que generó más de 200 errores de compilación. Se resolvió eliminando el archivo `module-info.java` para quitar las restricciones del sistema de módulos de Java y configurando manualmente el Build Path para integrar las librerías de JUnit 4.

### 6. ¿Qué hicieron bien como equipo? ¿Qué se comprometen a hacer para mejorar los resultados?
Logramos una eficiente distribución de tareas para completar la totalidad del proyecto. Como compromiso de mejora, realizaremos todo con más antelación.

### 7. Considerando las prácticas XP incluidas en los laboratorios. ¿cuál fue la más útil? ¿por qué?
La **Refactorización** fue la práctica más útil. Gracias a que contábamos con un conjunto sólido de pruebas unitarias, pudimos realizar cambios estructurales para limpiar el código.

### 8. ¿Qué referencias usaron? ¿Cuál fue la más útil?
- ICPC Foundation. (2025). *Problem J: Stacking Cups*. The 2025 ICPC World Finals Baku. Recuperado de https://icpc.foundation
- PMD Open Source Project. (2026). *Java Rulesets*. Recuperado de https://pmd.github.io
- Eclipse Foundation. (2026). *Eclipse IDE Documentation*. Recuperado de https://help.eclipse.org
- Oracle. (2026). *Java Platform, Standard Edition API Specification*. Recuperado de https://docs.oracle.com/en/java/javase/

Autores:
- Natalia Andrea Rodríguez Torres
- Daniel José Villamizar Castellanos
