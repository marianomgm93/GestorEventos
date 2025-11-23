/**
 * Representa una sección o área dentro de un {@link Recinto}.
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sector {
    private static int totalSectores;
    private int id;
    private String nombre;
    private double valorExtra;
    private boolean tieneAsientos;
    private ArrayList<Asiento> asientos;

    public Sector(String nombre, double valorExtra, boolean tieneAsientos, ArrayList<Asiento> asientos) {
        this.id = totalSectores++;
        this.nombre = nombre;
        this.valorExtra = valorExtra;
        this.tieneAsientos = tieneAsientos;
        this.asientos = asientos;
    }

    public Sector(int id, String nombre, double valorExtra, boolean tieneAsientos, ArrayList<Asiento> asientos) {
        this.id = id;
        this.nombre = nombre;
        this.valorExtra = valorExtra;
        this.tieneAsientos = tieneAsientos;
        this.asientos = asientos;
        totalSectores++;
    }


    public Sector(JSONObject o) {
        this.id = o.getInt("id");
        totalSectores++;
        this.nombre = o.getString("nombre");
        this.tieneAsientos = o.getBoolean("tieneAsientos");
        this.valorExtra = o.getDouble("valorExtra");
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
    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("id", this.id);
        o.put("nombre", this.nombre);
        o.put("tieneAsientos", this.tieneAsientos);
        o.put("valorExtra", this.valorExtra);
        JSONArray jarr = new JSONArray();
        for (Asiento a : asientos) {
            jarr.put(a.toJSON());
        }
        o.put("asientos", jarr);
        return o;
    }

    /**
     * Genera una cadena con los números de todos los asientos que se encuentran
     * disponibles en este sector.
     *
     * @return Una cadena de texto formateada con los números de asientos disponibles.
     */
    public String verAsientosDisponibles() {
        StringBuilder sb = new StringBuilder();
        if (this.isTieneAsientos()) {
            sb.append("/////////// Asientos disponibles ///////////");
            for (Asiento a : this.asientos) {
                if (a.isDisponible()) sb.append("[ ").append(a.getId()).append(" ]").append("\t");
            }
            sb.append("/////////// Fin Asientos disponibles ///////////");
        }
        return sb.toString();
    }

    public void reservarPrimeroDisponible(){
        getAsientosDisponibles().getFirst().setDisponible(false);
    }

    public double getValorExtra() {
        return valorExtra;
    }

    public void setValorExtra(double valorExtra) {
        this.valorExtra = valorExtra;
    }

    public boolean isTieneAsientos() {
        return tieneAsientos;
    }

    public void setTieneAsientos(boolean tieneAsientos) {
        this.tieneAsientos = tieneAsientos;
    }

    public ArrayList<Asiento> getAsientosDisponibles() {
        ArrayList<Asiento> disponibles = new ArrayList<>();
        for (Asiento a : this.asientos) {
            if (a.isDisponible()) disponibles.add(a);
        }
        return disponibles;
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
        sb.append(", valorExtra=").append(valorExtra);
        sb.append(", tieneAsientos=").append(tieneAsientos);
        sb.append(", asientos=").append(asientos);
        sb.append('}');
        return sb.toString();
    }
}