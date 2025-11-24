/**
 * Representa un Vendedor del sistema, una subclase de {@link Usuario}.
 * Un Vendedor tiene la capacidad de registrar y gestionar los tickets
 * que ha vendido.
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Vendedor extends Usuario {
    private ArrayList<Ticket> ticketsVendidos;

    /**
     * Constructor por defecto. Inicializa la lista de tickets vendidos.
     * El ID es asignado automáticamente por la superclase {@link Usuario}.
     */
    public Vendedor() {
        ticketsVendidos=new ArrayList<>();
    }

    /**
     * Constructor para crear un Vendedor con sus credenciales.
     * La inicialización de nombre, email y contraseña se realiza en la superclase.
     *
     * @param nombre El nombre del vendedor.
     * @param email El correo electrónico del vendedor.
     * @param contrasenia La contraseña del vendedor.
     */
    public Vendedor(String nombre, String email, String contrasenia) {
        super(nombre, email, contrasenia);
        this.ticketsVendidos = new ArrayList<>();
    }

    /**
     * Constructor para crear un Vendedor a partir de un objeto JSON,
     * útil para la deserialización y carga de datos.
     *
     * @param o El objeto {@code JSONObject} que contiene los datos del vendedor y sus tickets.
     */
    public Vendedor(JSONObject o) {
        // Llama al constructor de la superclase para inicializar los atributos de Usuario
        super(o.getInt("id"), o.getString("nombre"), o.getString("email"), o.getString("contrasenia"),o.getBoolean("activo"));

        this.ticketsVendidos = new ArrayList<>();
        JSONArray jarr = o.getJSONArray("ticketsVendidos");
        for (int i = 0; i < jarr.length(); i++) {
            this.ticketsVendidos.add(new Ticket(jarr.getJSONObject(i)));
        }
    }

    /**
     * Convierte el objeto Vendedor a su representación en formato JSON para
     * fines de serialización. Incluye los atributos de la superclase y la lista
     * de tickets vendidos.
     *
     * @return Un objeto {@code JSONObject} que representa el estado del vendedor.
     */
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id",super.getId());
        o.put("nombre",super.getNombre());
        o.put("email", super.getEmail());
        o.put("contrasenia",super.getContrasenia());
        o.put("activo", super.isActivo());
        JSONArray jarr=new JSONArray();
        for(Ticket t: ticketsVendidos){
            jarr.put(t.toJSON());
        }
        o.put("ticketsVendidos",jarr);
        return o;
    }

    /**
     * Devuelve una representación en cadena del objeto Vendedor,
     * extendiendo la representación de la superclase {@link Usuario}
     * con la lista de tickets vendidos.
     *
     * @return Una cadena que representa el estado del Vendedor.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vendedor{");
        sb.append(super.toString());
        sb.append("ticketsVendidos=").append(ticketsVendidos);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Obtiene la lista de tickets que han sido vendidos por este vendedor.
     *
     * @return La {@code ArrayList} de objetos {@link Ticket} vendidos.
     */
    public ArrayList<Ticket> getTicketsVendidos() {
        return ticketsVendidos;
    }


    /**
     * Este método está sobrescrito y vacío para prevenir la modificación
     * del ID de un Vendedor después de su creación, ya que el ID es manejado
     * por el sistema (la superclase {@link Usuario}).
     *
     * @param id El ID que se intenta establecer (se ignora).
     */
    @Override
    public void setId(int id) {
        // Se deja vacío para evitar la modificación del ID.
    }


}