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
5. Documento de Retrospectiva.

---
Autores: 
- Natalia Andrea Rodríguez Torres
- Daniel José Villamizar Castellanos
