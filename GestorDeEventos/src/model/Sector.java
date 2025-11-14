/**
 * Representa una sección o área dentro de un {@link Recinto}.
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sector {

    /**
     * Contador estático para llevar el registro del número total de sectores
     * creados.
     */
    private static int totalSectores;

    /**
     * Identificador único del sector.
     */
    private int id;

    /**
     * Nombre del sector (e.g., "Platea Baja", "Palco VIP").
     */
    private String nombre;

    /**
     * Tipo de sector que define su categoría o ubicación.
     *
     * @see Tipo
     */
    private Tipo tipo;

    /**
     * Lista de asientos que componen este sector.
     *
     * @see Asiento
     */
    private ArrayList<Asiento> asientos;

    /**
     * Constructor para crear un nuevo Sector con un ID predefinido.
     *
     * @param id El identificador único del sector.
     * @param nombre El nombre del sector.
     * @param tipo El {@link Tipo} de sector.
     * @param asientos La lista de {@link Asiento}s en este sector.
     */
    public Sector(int id, String nombre, Tipo tipo, ArrayList<Asiento> asientos) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.asientos = asientos;
        totalSectores++;
    }

    /**
     * Constructor para crear un nuevo Sector, permitiendo la asignación
     * automática del ID (basado en el contexto de donde se use).
     *
     * @param nombre El nombre del sector.
     * @param tipo El {@link Tipo} de sector.
     * @param asientos La lista de {@link Asiento}s en este sector.
     */
    public Sector(String nombre, Tipo tipo, ArrayList<Asiento> asientos) {
        this.id = totalSectores++;
        this.nombre = nombre;
        this.tipo = tipo;
        this.asientos = asientos;
    }

    /**
     * Constructor para crear un Sector a partir de un objeto JSON,
     * útil para la deserialización y carga de datos.
     *
     * @param o El objeto {@code JSONObject} que contiene todos los datos del sector.
     */
    public Sector(JSONObject o) {
        this.id = o.getInt("id");
        totalSectores++;
        this.nombre = o.getString("nombre");
        this.tipo = Tipo.valueOf(o.getString("tipo"));

        JSONArray jarr = o.getJSONArray("asientos");
        asientos = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++) {
            asientos.add(new Asiento(jarr.getJSONObject(i)));
        }
    }

    /**
     * Convierte el objeto Sector a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} que representa el estado completo del sector.
     */
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id", this.id);
        o.put("nombre",this.nombre);
        o.put("tipo",this.tipo.toString());

        JSONArray jarr=new JSONArray();
        for(Asiento a: asientos){
            jarr.put(a.toJSON());
        }
        o.put("asientos",jarr);
        return o;
    }

    /**
     * Genera una cadena con los números de todos los asientos que se encuentran
     * disponibles en este sector.
     *
     * @return Una cadena de texto formateada con los números de asientos disponibles.
     */
    public String asientosDisponibles() {
        StringBuilder sb = new StringBuilder();
        for (Asiento a : this.asientos) {
            if (a.isDisponible()) sb.append("[ ").append(a.getNumero()).append(" ]").append("\t");
        }
        return sb.toString();
    }

    /**
     * Obtiene el identificador único del sector.
     *
     * @return El ID del sector.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del sector.
     *
     * @param id El nuevo ID para el sector.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del sector.
     *
     * @return El nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el sector.
     *
     * @param nombre El nuevo nombre del sector.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tipo de sector.
     *
     * @return El {@link Tipo} de sector.
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Establece un nuevo tipo para el sector.
     *
     * @param tipo El nuevo {@link Tipo} de sector.
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la lista de asientos que componen el sector.
     *
     * @return La {@code ArrayList} de objetos {@link Asiento}.
     */
    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    /**
     * Establece una nueva lista de asientos para el sector.
     *
     * @param asientos La nueva {@code ArrayList} de {@link Asiento}s.
     */
    public void setAsientos(ArrayList<Asiento> asientos) {
        this.asientos = asientos;
    }

    /**
     * Devuelve una representación en cadena del objeto Sector,
     * incluyendo su ID, nombre, tipo y los asientos que contiene.
     *
     * @return Una cadena que representa el estado del Sector.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sector{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", tipo=").append(tipo);
        sb.append(", asientos=").append(asientos);
        sb.append('}');
        return sb.toString();
    }
}