package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sector {
    private static int totalSectores;
    private int id;
    private String nombre;
    private Tipo tipo;
    private ArrayList<Asiento> asientos;

    public Sector(int id, String nombre, Tipo tipo, ArrayList<Asiento> asientos) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.asientos = asientos;
    }

    public Sector(String nombre, Tipo tipo, ArrayList<Asiento> asientos) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.asientos = asientos;
    }

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

    public String asientosDisponibles() {
        StringBuilder sb = new StringBuilder();
        for (Asiento a : this.asientos) {
            if (a.isDisponible()) sb.append("[ ").append(a.getNumero()).append(" ]").append("\t");
        }
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(ArrayList<Asiento> asientos) {
        this.asientos = asientos;
    }

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
