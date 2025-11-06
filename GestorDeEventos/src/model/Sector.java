package model;

import java.util.ArrayList;

public class Sector {
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
}
