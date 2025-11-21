/**
 * Representa un ticket o entrada para un evento específico.
 * Contiene toda la información necesaria para identificar el evento, la función
 * y el asiento al que da derecho.
 */
package model;

import org.json.JSONObject;

import java.util.Objects;

public class Ticket {

    private static int totalTickets;
    private int id;
    private String direccionRecinto;
    private int asiento;
    private int eventoId;
    private int funcionId;
    private int sectorId;
    private String nombreEvento;
    private String fechaYHora;
    private double precio;

    public Ticket() {
        this.id=totalTickets++;
    }

    public Ticket(int id, String direccionRecinto, int asiento,int eventoId, int funcionId, int sectorId, String nombreEvento, String fechaYHora, double precio) {
        this.id=id;
        this.direccionRecinto = direccionRecinto;
        this.asiento = asiento;
        this.eventoId=eventoId;
        this.funcionId=funcionId;
        this.sectorId=sectorId;
        this.nombreEvento = nombreEvento;
        this.fechaYHora = fechaYHora;
        this.precio = precio;
        totalTickets++;
    }

    public Ticket(String direccionRecinto, int asiento,int eventoId,int funcionId,int sectorId, String nombreEvento, String fechaYHora, double precio) {
        this.id=totalTickets++;
        this.direccionRecinto = direccionRecinto;
        this.asiento = asiento;
        this.eventoId=eventoId;
        this.funcionId=funcionId;
        this.sectorId=sectorId;
        this.nombreEvento = nombreEvento;
        this.fechaYHora = fechaYHora;
        this.precio = precio;
    }


    public Ticket(JSONObject o) {
        this.id=o.getInt("id");
        totalTickets++;
        this.direccionRecinto = o.getString("direccionRecinto");
        this.asiento = o.getInt("asiento");
        this.eventoId=o.getInt("eventoId");
        this.nombreEvento = o.getString("nombreEvento");
        this.fechaYHora = o.getString("fechaYHora");
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
        o.put("eventoId", this.eventoId);
        o.put("funcionId",this.funcionId);
        o.put("sectorId",this.sectorId);
        o.put("nombreEvento", this.nombreEvento);
        o.put("fechaYHora", this.fechaYHora);
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
     * Obtiene el precio del ticket.
     *
     * @return El precio del ticket.
     */
    public double getPrecio() {
        return precio;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ticket ticket)) return false;
        return id == ticket.id && asiento == ticket.asiento && eventoId == ticket.eventoId && funcionId == ticket.funcionId && sectorId == ticket.sectorId && Double.compare(precio, ticket.precio) == 0 && Objects.equals(direccionRecinto, ticket.direccionRecinto) && Objects.equals(nombreEvento, ticket.nombreEvento) && Objects.equals(fechaYHora, ticket.fechaYHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, direccionRecinto, asiento, eventoId, funcionId, sectorId, nombreEvento, fechaYHora, precio);
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public int getFuncionId() {
        return funcionId;
    }

    public void setFuncionId(int funcionId) {
        this.funcionId = funcionId;
    }

    public int getSectorId() {
        return sectorId;
    }

    public void setSectorId(int sectorId) {
        this.sectorId = sectorId;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
        sb.append(", precio=").append(precio);
        sb.append('}');
        return sb.toString();
    }
}