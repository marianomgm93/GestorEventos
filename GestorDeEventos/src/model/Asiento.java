/**
 * Representa un asiento individual en un evento (aunque tambien se usa como forma de controlar la disponibilidad en lugares sin asiento)
 * Contiene información sobre el número de asiento y su disponibilidad.
 */
package model;

import org.json.JSONObject;

public class Asiento {

    /**
     * Contador estático para llevar el control del número total de instancias de Asiento
     */
    private static int totalAsientos;

    /**
     * El número de identificación único del asiento.
     */
    private int numero;

    /**
     * Estado de disponibilidad del asiento (true si está libre, false si está ocupado).
     */
    private boolean disponible;

    /**
     * Constructor por defecto.
     * Asigna automáticamente el siguiente número consecutivo de asiento
     * e inicializa el asiento como disponible.
     */
    public Asiento() {
        this.numero = totalAsientos++;
        this.disponible = true;
    }

    /**
     * Constructor que permite inicializar un asiento con un número específico.
     * Inicializa el asiento como disponible.
     *
     * @param numero El número de asiento a asignar.
     */
    public Asiento(int numero) {
        this.numero = numero;
        totalAsientos++;
        this.disponible = true;
    }

    /**
     * Constructor para inicializar un asiento a partir de un objeto JSON,
     *
     * @param o El objeto {@code JSONObject} que contiene los datos del asiento.
     */
    public Asiento(JSONObject o) {
        this.numero = o.getInt("numero");
        totalAsientos++;
        this.disponible = o.getBoolean("disponible");
    }

    /**
     * Convierte el objeto Asiento a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} con los campos "número" y "disponible".
     */
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("numero", this.numero);
        o.put("disponible",this.disponible);
        return o;
    }

    /**
     * Obtiene el número de identificación del asiento.
     *
     * @return El número de asiento.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece un nuevo número de identificación para el asiento.
     *
     * @param numero El nuevo número de asiento a establecer.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Verifica el estado de disponibilidad del asiento.
     *
     * @return {@code true} si el asiento está disponible, {@code false} si está ocupado.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece el estado de disponibilidad del asiento.
     *
     * @param disponible El nuevo estado de disponibilidad ({@code true} o {@code false}).
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Devuelve una representación en cadena del objeto Asiento.
     * Sobrescribe el método {@code toString()} de la clase {@code Object}.
     *
     * @return Una cadena que contiene el número y la disponibilidad del asiento.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Asiento{");
        sb.append("numero=").append(numero);
        sb.append(", disponible=").append(disponible);
        sb.append('}');
        return sb.toString();
    }
}