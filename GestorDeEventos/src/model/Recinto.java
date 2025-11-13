package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recinto {
    private static int totalRecintos;
    private int id;
    private String nombre;
    private String direccion;
    private int capacidad;
    private ArrayList<Sector> sectores;

    public Recinto(String nombre, String direccion, int capacidad, ArrayList<Sector> sectores) {
        this.id = totalRecintos++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.sectores = sectores;
    }

    public Recinto(String nombre, String direccion, int capacidad) {
        this.id = totalRecintos++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }

    public Recinto(JSONObject o) {
        this.id = o.getInt("id");
        totalRecintos++;
        this.nombre = o.getString("nombre");
        this.direccion = o.getString("direccion");
        this.capacidad = o.getInt("capacidad");
        JSONArray jarr = o.getJSONArray("sectores");
        this.sectores = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++) {
            sectores.add(new Sector(jarr.getJSONObject(i)));
        }
    }
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id", this.id);
        o.put("nombre", this.nombre);
        o.put("direccion", this.direccion);
        o.put("capacidad", this.capacidad);
        JSONArray jarr=new JSONArray();
        for(Sector s: sectores){
            jarr.put(s.toJSON());
        }
        o.put("sectores",jarr);
        return o;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public ArrayList<Sector> getSectores() {
        return sectores;
    }

    public void setSectores(ArrayList<Sector> sectores) {
        this.sectores = sectores;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recinto{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", direccion='").append(direccion).append('\'');
        sb.append(", capacidad=").append(capacidad);
        sb.append(", sectores=").append(sectores);
        sb.append('}');
        return sb.toString();
    }
}
