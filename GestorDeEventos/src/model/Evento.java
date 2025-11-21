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

    public Evento() {
        this.id = totalEventos++;
    }

    /**
     * Constructor para crear un nuevo evento con los detalles esenciales.
     * Asigna un ID automático e inicializa la lista de funciones.
     *
     * @param nombre      El nombre del evento.
     * @param descripcion La descripción detallada del evento.
     * @param categoria   La {@link Categoria} a la que pertenece el evento.
     */
    public Evento(String nombre, String descripcion, Categoria categoria) {
        this.id = totalEventos++;
        this.funciones = new ArrayList<>();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    /**
     * Constructor para crear un evento a partir de un objeto JSON,
     *
     * @param o El objeto {@code JSONObject} que contiene todos los datos del evento.
     */
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
        totalEventos++;
    }

    /**
     * Convierte el objeto Evento a su representación en formato JSON
     *
     * @return Un objeto {@code JSONObject} que representa el estado completo del evento.
     */
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
        return o;
    }
    public Funcion buscarFuncionPorId(int id) throws ElementoNoEncontradoException{
        for(Funcion f:funciones) {
            if (f.getId() == id) {
                return f;
            }
        }
        throw new ElementoNoEncontradoException("No existe una funcion con ese id");
    }
    /**
     * Obtiene el número total de eventos que han sido instanciados.
     *
     * @return El contador estático de eventos.
     */
    public static int getTotalEventos() {
        return totalEventos;
    }

    /**
     * Obtiene el identificador único del evento.
     *
     * @return El ID del evento.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del evento.
     *
     * @param id El nuevo ID para el evento.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene la lista de funciones (horarios/sesiones) asociadas a este evento.
     *
     * @return La lista de objetos {@link Funcion}.
     */
    public ArrayList<Funcion> getFunciones() {
        return funciones;
    }

    /**
     * Establece una nueva lista de funciones para el evento.
     *
     * @param funciones La nueva lista de {@link Funcion}s.
     */
    public void setFunciones(ArrayList<Funcion> funciones) {
        this.funciones = funciones;
    }

    /**
     * Obtiene el nombre del evento.
     *
     * @return El nombre del evento.
     */
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