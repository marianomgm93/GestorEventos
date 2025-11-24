/**
 * Representa un asiento individual en un evento (aunque tambien se usa como forma de controlar la disponibilidad en lugares sin asiento)
 * Contiene información sobre el número de asiento y su disponibilidad.
 */
package model;

import org.json.JSONObject;

public class Asiento {
    private static int totalAsientos;
    private int id;
    private int numero;

    private boolean disponible;

    public Asiento() {
        this.disponible = true;
    }

    /**
     * Constructor que permite inicializar un asiento con un número específico.
     * Inicializa el asiento como disponible.
     *
     * @param numero El número de asiento a asignar.
     */
    public Asiento(int numero) {
        this.id=totalAsientos++;
        this.numero = numero;
        this.disponible = true;
    }

    /**
     * Constructor para inicializar un asiento a partir de un objeto JSON,
     *
     * @param o El objeto {@code JSONObject} que contiene los datos del asiento.
     */
    public Asiento(JSONObject o) {
        this.id= o.getInt("id");
        this.numero = o.getInt("numero");
        this.disponible = o.getBoolean("disponible");
    }

    /**
     * Convierte el objeto Asiento a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} con los campos "número" y "disponible".
     */
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id",this.id);
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

    public static void setTotalAsientos(int totalAsientos) {
        Asiento.totalAsientos = totalAsientos;
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

    public int getId() {
        return id;
    }

    /**
     * Devuelve una representación en cadena del objeto Asiento.
     * Sobrescribe el método {@code toString()} de la clase {@code Object}.
     *
     * @return Una cadena que contiene el número y la disponibilidad del asiento.
     */

    @Override
    public String toString() {
        return "Asiento{" +
                "id=" + id +
                ", numero=" + numero +
                ", disponible=" + disponible +
                '}';
    }
}