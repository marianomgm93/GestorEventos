/**
 * Define el tipo o nivel de un sector dentro de un recinto,
 * indicando probablemente su ubicación o jerarquía (e.g., visibilidad, costo).
 */
package model;

public enum Tipo {
    /**
     * Nivel o sector de máxima prioridad/visibilidad (ej: Platea, VIP).
     */
    PRINCIPAL,

    /**
     * Nivel o sector intermedio.
     */
    SECUNDARIO,

    /**
     * Nivel o sector de menor prioridad/visibilidad (ej: Galería, fondo).
     */
    TERCIARIO,

}