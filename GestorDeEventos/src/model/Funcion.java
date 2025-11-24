/**
 * Representa una sesión o pase específico de un Evento, incluyendo la hora,
 * el {@link Recinto} donde se realiza y el precio base de la entrada.
 */
package model;

import exceptions.ElementoNoEncontradoException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Funcion {

    private static int totalFunciones;
    private int id;
    private LocalDateTime fechayHora;
    private Recinto recinto;
    private double precioBase;

    /**
     * Constructor para crear una nueva Función con sus detalles principales.
     * Asigna automáticamente un ID consecutivo.
     *
     * @param hora La hora programada para la función.
     * @param recinto El {@link Recinto} donde se realizará la función.
     * @param precioBase El precio base de la entrada.
     */
    public Funcion(LocalDateTime hora, Recinto recinto, double precioBase) {
        this.id = totalFunciones++;
        this.fechayHora = hora;
        this.recinto = recinto;
        this.precioBase = precioBase;
    }

    /**
     * Constructor para crear una Función a partir de un objeto JSON,
     * útil para la deserialización.
     *
     * @param o El objeto {@code JSONObject} con los datos de la función.
     */
    public Funcion(JSONObject o){
        this.id=o.getInt("id");
        this.fechayHora=LocalDateTime.parse(o.getString("fechayHora"));
        this.recinto=new Recinto(o.getJSONObject("recinto"));
        this.precioBase=o.getDouble("precioBase");
    }

    /**
     * Convierte el objeto Funcion a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} que contiene los detalles de la función.
     */
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id",this.id);
        o.put("fechayHora",this.fechayHora);
        o.put("recinto",this.recinto.toJSON());
        o.put("precioBase",this.precioBase);
        return o;
    }

    /**
     * Obtiene el identificador único de la función.
     *
     * @return El ID de la función.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece un nuevo identificador para la función.
     *
     * @param id El nuevo ID a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    public static void setTotalFunciones(int totalFunciones) {
        Funcion.totalFunciones = totalFunciones;
    }

    /**
     * Obtiene el recinto asociado a esta función.
     *
     * @return El objeto {@link Recinto} donde se realiza la función.
     */
    public Recinto getRecinto() {
        return recinto;
    }

    /**
     * Establece un nuevo recinto para la función.
     *
     * @param recinto El nuevo objeto {@link Recinto} a asociar.
     */
    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    /**
     * Obtiene el precio base de la entrada para esta función.
     *
     * @return El precio base de la entrada.
     */
    public double getPrecioBase() {
        return precioBase;
    }

    /**
     * Establece un nuevo precio base para la función.
     *
     * @param precioBase El nuevo precio base (entero) a establecer.
     */
    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    /**
     * Genera una cadena con la información detallada de los asientos disponibles
     * en cada sector del recinto de la función.
     *
     * @return Una cadena de texto con la lista de asientos disponibles.
     */
    public String asientosDisponibles(){
        StringBuilder sb=new StringBuilder();
        int disponibilidad;
        sb.append("///////////////Disponibilidad///////////////\n");
        for (Sector s: this.recinto.getSectores()){
                sb.append("\nSector: ").append(s.getId()).append("\t").append(s.getNombre());
                disponibilidad=s.getAsientosDisponibles().size();
                sb.append("\tDisponibilidad: ").append(disponibilidad).append("/").append(s.getAsientosDisponibles().size());
            }

        return sb.toString();
    }

    public int cantidadAsientosDisponibles(){
        int disponibilidad=0;
        for (Sector s: this.recinto.getSectores()){
            disponibilidad+=s.getAsientosDisponibles().size();
        }

        return disponibilidad;
    }
    public Sector buscarSectorPorId(int id) throws ElementoNoEncontradoException{
        for(Sector s: this.recinto.getSectores()){
            if(s.getId()==id) return s;
        }
        throw new ElementoNoEncontradoException("El no se encontró el sector");
    }
    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Funcion funcion)) return false;
        return Objects.equals(fechayHora, funcion.fechayHora) && Objects.equals(recinto, funcion.recinto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechayHora, recinto);
    }

    /**
     * Devuelve una representación en cadena del objeto Funcion.
     * Sobrescribe el método {@code toString()} de la clase {@code Object}.
     *
     * @return Una cadena que contiene el ID, la hora, el recinto y el precio base.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Funcion{");
        sb.append("id=").append(id);
        sb.append(", fecha y hora='").append(fechayHora).append('\'');
        sb.append(", recinto=").append(recinto);
        sb.append(", precioBase=").append(precioBase);
        sb.append('}');
        return sb.toString();
    }
}