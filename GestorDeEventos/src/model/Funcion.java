package model;

import org.json.JSONObject;

public class Funcion {
    private int id;
    private String hora; //cambiar tipo de variable
    private Recinto recinto;
    private double precioBase;

    public Funcion(int id, String hora, Recinto recinto, int precioBase) {
        this.id = id;
        this.hora = hora;
        this.recinto = recinto;
        this.precioBase = precioBase;
    }
    /// FALTA CREAR FROM JSON
    public Funcion(JSONObject o){
        this.id=o.getInt("id");
        this.hora=o.getString("hora");
        this.recinto=new Recinto(o.getJSONObject("recinto"));
        this.precioBase=o.getDouble("precioBase");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }
}
