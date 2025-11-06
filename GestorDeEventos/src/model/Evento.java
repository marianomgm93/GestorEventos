package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Evento {
    private static int totalEventos;
    private int id;
    private ArrayList<Funcion> funciones;
    private String nombre;
    private String descripcion;
    private Categoria categoria;

    public Evento() {
        this.id=totalEventos++;
    }

    public Evento(String nombre, String descripcion, Categoria categoria) {
        this.id=totalEventos++;
        this.funciones = new ArrayList<>();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }
    public Evento (JSONObject o){
        this.id=o.getInt("id");
        JSONArray jarr=o.getJSONArray("funciones");
        this.funciones=new ArrayList<>();
        for (int i = 0; i <jarr.length() ; i++) {
            this.funciones.add(new Funcion(jarr.getJSONObject(i)));
        }
        this.nombre=o.getString("nombre");
        this.descripcion=o.getString("descripcion");
        this.categoria=Categoria.valueOf(o.getString("categoria"));
    }
    public static int getTotalEventos() {
        return totalEventos;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Funcion> getFuncion() {
        return funciones;
    }

    public void setFuncion(ArrayList<Funcion> funcion) {
        funcion = funcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
