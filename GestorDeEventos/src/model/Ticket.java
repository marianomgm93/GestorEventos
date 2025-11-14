/**
 * Representa un ticket o entrada para un evento específico.
 * Contiene toda la información necesaria para identificar el evento, la función
 * y el asiento al que da derecho.
 */
package model;

import org.json.JSONObject;

import java.util.Objects;

public class Ticket {

    /**
     * Contador estático que lleva el registro del número total de tickets creados.
     */
    private static int totalTickets;

    /**
     * Identificador único del ticket.
     */
    private int id;

    /**
     * La dirección del recinto donde se llevará a cabo el evento.
     */
    private String direccionRecinto;

    /**
     * El número de asiento asignado en el recinto.
     */
    private int asiento;

    /**
     * Nombre del evento al que corresponde el ticket.
     */
    private String nombreEvento;

    /**
     * Fecha y hora programada de la función (representada como String).
     */
    private String fechaYHora;

    /**
     * Tipo de asiento o sector al que corresponde el ticket.
     *
     * @see Tipo
     */
    private Tipo tipo;

    /**
     * Precio final pagado por el ticket.
     */
    private double precio;

    /**
     * Constructor por defecto. Asigna automáticamente un ID consecutivo.
     */
    public Ticket() {
        this.id=totalTickets++;
    }

    /**
     * Constructor completo que permite inicializar todos los campos, incluyendo un ID predefinido.
     *
     * @param id El identificador único del ticket.
     * @param direccionRecinto La dirección del recinto.
     * @param asiento El número de asiento.
     * @param nombreEvento El nombre del evento.
     * @param fechaYHora La fecha y hora de la función.
     * @param tipo El {@link Tipo} de asiento.
     * @param precio El precio del ticket.
     */
    public Ticket(int id, String direccionRecinto, int asiento, String nombreEvento, String fechaYHora, Tipo tipo, double precio) {
        this.id=id;
        this.direccionRecinto = direccionRecinto;
        this.asiento = asiento;
        this.nombreEvento = nombreEvento;
        this.fechaYHora = fechaYHora;
        this.tipo = tipo;
        this.precio = precio;
        totalTickets++;
    }

    /**
     * Constructor para crear un ticket con ID asignado automáticamente.
     *
     * @param direccionRecinto La dirección del recinto.
     * @param asiento El número de asiento.
     * @param nombreEvento El nombre del evento.
     * @param fechaYHora La fecha y hora de la función.
     * @param tipo El {@link Tipo} de asiento.
     * @param precio El precio del ticket.
     */
    public Ticket(String direccionRecinto, int asiento, String nombreEvento, String fechaYHora, Tipo tipo, double precio) {
        this.id=totalTickets++;
        this.direccionRecinto = direccionRecinto;
        this.asiento = asiento;
        this.nombreEvento = nombreEvento;
        this.fechaYHora = fechaYHora;
        this.tipo = tipo;
        this.precio = precio;
    }

    /**
     * Constructor para crear un Ticket a partir de un objeto JSON,
     * útil para la deserialización.
     *
     * @param o El objeto {@code JSONObject} con los datos del ticket.
     */
    public Ticket(JSONObject o) {
        this.id=o.getInt("id");
        totalTickets++;
        this.direccionRecinto = o.getString("direccionRecinto");
        this.asiento = o.getInt("asiento");
        this.nombreEvento = o.getString("nombreEvento");
        this.fechaYHora = o.getString("fechaYHora");
        this.tipo = Tipo.valueOf(o.getString("tipo"));
        this.precio = o.getDouble("precio");
    }

    /**
     * Convierte el objeto Ticket a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} que contiene todos los detalles del ticket.
     */
    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("id", this.id);
        o.put("direccionRecinto", this.direccionRecinto);
        o.put("asiento", this.asiento);
        o.put("nombreEvento", this.nombreEvento);
        o.put("fechaYHora", this.fechaYHora);
        o.put("tipo", this.tipo.toString());
        o.put("precio", this.precio);
        return o;
    }

    /**
     * Obtiene el número total de tickets que han sido instanciados en el sistema.
     *
     * @return El contador estático de tickets.
     */
    public static int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Obtiene el identificador único del ticket.
     *
     * @return El ID del ticket.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene la dirección del recinto.
     *
     * @return La dirección del recinto.
     */
    public String getDireccionRecinto() {
        return direccionRecinto;
    }

    /**
     * Establece una nueva dirección para el recinto del evento.
     *
     * @param direccionRecinto La nueva dirección.
     */
    public void setDireccionRecinto(String direccionRecinto) {
        this.direccionRecinto = direccionRecinto;
    }

    /**
     * Obtiene el número de asiento asignado.
     *
     * @return El número de asiento.
     */
    public int getAsiento() {
        return asiento;
    }

    /**
     * Establece un nuevo número de asiento para el ticket.
     *
     * @param asiento El nuevo número de asiento.
     */
    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    /**
     * Obtiene el nombre del evento.
     *
     * @return El nombre del evento.
     */
    public String getNombreEvento() {
        return nombreEvento;
    }

    /**
     * Establece un nuevo nombre para el evento.
     *
     * @param nombreEvento El nuevo nombre del evento.
     */
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    /**
     * Obtiene la fecha y hora de la función.
     *
     * @return La fecha y hora como String.
     */
    public String getFechaYHora() {
        return fechaYHora;
    }

    /**
     * Establece una nueva fecha y hora para la función.
     *
     * @param fechaYHora La nueva fecha y hora (String).
     */
    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    /**
     * Obtiene el tipo de asiento/sector del ticket.
     *
     * @return El {@link Tipo} de asiento.
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece un nuevo tipo de asiento/sector para el ticket.
     *
     * @param tipo El nuevo {@link Tipo}.
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el precio del ticket.
     *
     * @return El precio del ticket.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece un nuevo precio para el ticket.
     *
     * @param precio El nuevo precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Compara este Ticket con otro objeto para verificar si son iguales.
     * La igualdad se basa en todos los atributos del ticket.
     *
     * @param o El objeto con el que se va a comparar.
     * @return {@code true} si los tickets son idénticos, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && asiento == ticket.asiento && Double.compare(precio, ticket.precio) == 0 && Objects.equals(direccionRecinto, ticket.direccionRecinto) && Objects.equals(nombreEvento, ticket.nombreEvento) && Objects.equals(fechaYHora, ticket.fechaYHora) && tipo == ticket.tipo;
    }

    /**
     * Genera un valor hash para el objeto basado en todos sus atributos.
     *
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, direccionRecinto, asiento, nombreEvento, fechaYHora, tipo, precio);
    }

    /**
     * Devuelve una representación en cadena del objeto Ticket,
     * mostrando todos sus atributos.
     *
     * @return Una cadena que representa el estado del Ticket.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("id=").append(id);
        sb.append(", direccionRecinto='").append(direccionRecinto).append('\'');
        sb.append(", asiento=").append(asiento);
        sb.append(", nombreEvento='").append(nombreEvento).append('\'');
        sb.append(", fechaYHora='").append(fechaYHora).append('\'');
        sb.append(", tipo=").append(tipo);
        sb.append(", precio=").append(precio);
        sb.append('}');
        return sb.toString();
    }
}