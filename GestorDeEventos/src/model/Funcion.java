/**
 * Representa una sesión o pase específico de un Evento, incluyendo la hora,
 * el {@link Recinto} donde se realiza y el precio base de la entrada.
 */
package model;

import org.json.JSONObject;

public class Funcion {

    /**
     * Contador estático para llevar el registro del número total de funciones
     * creadas y asignar un ID único.
     */
    private static int totalFunciones;

    /**
     * Identificador único de la función.
     */
    private int id;

    /**
     * La hora o fecha y hora programada para la función (representada como String).
     */
    private String hora;

    /**
     * El recinto o lugar físico donde se llevará a cabo la función.
     *
     * @see Recinto
     */
    private Recinto recinto;

    /**
     * El precio base de la entrada para esta función.
     */
    private double precioBase;

    /**
     * Constructor para crear una nueva Función con sus detalles principales.
     * Asigna automáticamente un ID consecutivo.
     *
     * @param hora La hora programada para la función.
     * @param recinto El {@link Recinto} donde se realizará la función.
     * @param precioBase El precio base de la entrada.
     */
    public Funcion(String hora, Recinto recinto, double precioBase) {
        this.id = totalFunciones++;
        this.hora = hora;
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
        totalFunciones++;
        this.hora=o.getString("hora");
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
        o.put("hora",this.hora);
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

    /**
     * Obtiene la hora programada de la función.
     *
     * @return La hora de la función como String.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Establece una nueva hora para la función.
     *
     * @param hora La nueva hora (String) para la función.
     */
    public void setHora(String hora) {
        this.hora = hora;
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
        for (Sector s: this.recinto.getSectores()){
            for(Asiento a: s.getAsientos()){
                sb.append("Sector: ").append(s.getId()).append(" ").append(s.getNombre());
                sb.append("Tipo: ").append(s.getTipo()).append("\n");
                if (a.isDisponible()) {
                    sb.append("--------------------------\n");
                    sb.append(a).append("\n");
                }
            }
        }
        return sb.toString();
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
        sb.append(", fecha y hora='").append(hora).append('\'');
        sb.append(", recinto=").append(recinto);
        sb.append(", precioBase=").append(precioBase);
        sb.append('}');
        return sb.toString();
    }
}