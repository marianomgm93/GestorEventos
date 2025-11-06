package model;

import java.util.ArrayList;

public class Recinto {
private int Id;
private String nombre;
private String direccion;
private int capacidad;
private ArrayList<Sector> sectores;

    public Recinto(int id, String nombre, String direccion, int capacidad, ArrayList<Sector> sectores) {
        Id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.sectores = sectores;
    }

    public Recinto(int id, String nombre, String direccion, int capacidad) {
        Id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
}
