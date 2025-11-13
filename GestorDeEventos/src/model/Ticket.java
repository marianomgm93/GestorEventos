package model;

import org.json.JSONObject;

import java.util.Objects;

public class Ticket {
    private static int totalTickets;
    private int id;
    private String direccionRecinto;
    private int asiento;
    private String nombreEvento;
    private String fechaYHora;
    private Tipo tipo;
    private double precio;

    public Ticket() {
        this.id=totalTickets++;
    }

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

    public Ticket(String direccionRecinto, int asiento, String nombreEvento, String fechaYHora, Tipo tipo, double precio) {
        this.id=totalTickets++;
        this.direccionRecinto = direccionRecinto;
        this.asiento = asiento;
        this.nombreEvento = nombreEvento;
        this.fechaYHora = fechaYHora;
        this.tipo = tipo;
        this.precio = precio;
    }

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

    public static int getTotalTickets() {
        return totalTickets;
    }

    public int getId() {
        return id;
    }

    public String getDireccionRecinto() {
        return direccionRecinto;
    }

    public void setDireccionRecinto(String direccionRecinto) {
        this.direccionRecinto = direccionRecinto;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(String fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && asiento == ticket.asiento && Double.compare(precio, ticket.precio) == 0 && Objects.equals(direccionRecinto, ticket.direccionRecinto) && Objects.equals(nombreEvento, ticket.nombreEvento) && Objects.equals(fechaYHora, ticket.fechaYHora) && tipo == ticket.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, direccionRecinto, asiento, nombreEvento, fechaYHora, tipo, precio);
    }

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

