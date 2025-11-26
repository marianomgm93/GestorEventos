/**
 * Representa un Evento genérico, el cual puede ser clasificado por una
 * {@link Categoria} y agrupa una colección de {@link Funcion}s (horarios o
 * sesiones específicas). Implementa la interfaz generica ID para manejo de identificadores.
 */
package model;

import exceptions.ElementoNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Evento implements ID {
    private static int totalEventos;
    private int id;
    private ArrayList<Funcion> funciones;
    private String nombre;
    private String descripcion;
    private Categoria categoria;
    private boolean activo = true;

    public Evento() {
        this.id = totalEventos++;
    }

    public Evento(String nombre, String descripcion, Categoria categoria) {
        this.id = totalEventos++;
        this.funciones = new ArrayList<>();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public Evento(JSONObject o) {
        this.id = o.getInt("id");
        JSONArray jarr = o.getJSONArray("funciones");
        this.funciones = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++) {
            this.funciones.add(new Funcion(jarr.getJSONObject(i)));
        }
        this.nombre = o.getString("nombre");
        this.descripcion = o.getString("descripcion");
        this.categoria = Categoria.valueOf(o.getString("categoria"));
        this.activo = o.getBoolean("activo");
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        o.put("id", this.id);
        JSONArray jarr = new JSONArray();
        for (Funcion f : funciones) {
            jarr.put(f.toJSON());
        }
        o.put("funciones", jarr);
        o.put("nombre", this.nombre);
        o.put("descripcion", this.descripcion);
        o.put("categoria", this.categoria.toString());
        o.put("activo", this.activo);
        return o;
    }

    public Funcion buscarFuncionPorId(int id) throws ElementoNoEncontradoException {
        for (Funcion f : funciones) {
            if (f.getId() == id) {
                return f;
            }
        }
        throw new ElementoNoEncontradoException("No existe una funcion con ese id");
    }


    public static int getTotalEventos() {
        return totalEventos;
    }
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getId() {
        return id;
    }

    public static void setTotalEventos(int totalEventos) {
        Evento.totalEventos = totalEventos;
    }


    @Override
    public void setId(int id) {
        this.id = id;
    }


    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    public ArrayList<Funcion> getFuncionesDisponibles() {
        ArrayList<Funcion> funcionesDisponibles = new ArrayList<>();
        if (!funciones.isEmpty()) {
            for (Funcion f : funciones) {
                if (f.cantidadAsientosDisponibles() > 0) funcionesDisponibles.add(f);
            }
        }
        return funcionesDisponibles;
    }


    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }


    public String getNombre() {
        return this.nombre;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Evento evento)) return false;
        return id == evento.id && Objects.equals(funciones, evento.funciones) && Objects.equals(nombre, evento.nombre) && Objects.equals(descripcion, evento.descripcion) && categoria == evento.categoria;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, funciones, nombre, descripcion, categoria);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Evento{");
        sb.append("id=").append(id);
        sb.append(", funciones=").append(funciones);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", descripcion='").append(descripcion).append('\'');
        sb.append(", categoria=").append(categoria);
        sb.append('}');
        return sb.toString();
    }
}