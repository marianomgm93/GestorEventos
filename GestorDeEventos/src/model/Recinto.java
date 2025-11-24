/**
 * Representa un recinto físico o lugar donde se lleva a cabo un evento.
 * Contiene información sobre su ubicación, capacidad y la distribución
 * interna por {@link Sector}es.
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Recinto {

    /**
     * Contador estático para llevar el registro del número total de recintos
     * creados y asignar un ID único.
     */
    private static int totalRecintos;

    /**
     * Identificador único del recinto.
     */
    private int id;

    /**
     * Nombre del recinto.
     */
    private String nombre;

    /**
     * Dirección física del recinto.
     */
    private String direccion;

    /**
     * Capacidad máxima total de personas que puede albergar el recinto.
     */
    private int capacidad;

    /**
     * Lista de los diferentes sectores o áreas dentro del recinto.
     *
     * @see Sector
     */
    private ArrayList<Sector> sectores;

    /**
     * Constructor completo para crear un nuevo Recinto. Asigna un ID automático.
     *
     * @param nombre El nombre del recinto.
     * @param direccion La dirección del recinto.
     * @param capacidad La capacidad máxima de personas.
     * @param sectores La lista de {@link Sector}es que componen el recinto.
     */
    public Recinto(String nombre, String direccion, int capacidad, ArrayList<Sector> sectores) {
        this.id = totalRecintos++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.sectores = sectores;
    }

    /**
     * Constructor para crear un Recinto sin especificar inicialmente los sectores. Asigna un ID automático.
     *
     * @param nombre El nombre del recinto.
     * @param direccion La dirección del recinto.
     * @param capacidad La capacidad máxima de personas.
     */
    public Recinto(String nombre, String direccion, int capacidad) {
        this.id = totalRecintos++;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.sectores = new ArrayList<>(); // Inicializa la lista de sectores vacía.
    }

    /**
     * Constructor para crear un Recinto a partir de un objeto JSON,
     * útil para la deserialización y carga de datos.
     *
     * @param o El objeto {@code JSONObject} que contiene todos los datos del recinto.
     */
    public Recinto(JSONObject o) {
        this.id = o.getInt("id");
        this.nombre = o.getString("nombre");
        this.direccion = o.getString("direccion");
        this.capacidad = o.getInt("capacidad");

        JSONArray jarr = o.getJSONArray("sectores");
        this.sectores = new ArrayList<>();
        for (int i = 0; i < jarr.length(); i++) {
            sectores.add(new Sector(jarr.getJSONObject(i)));
        }
    }

    public static void setTotalRecintos(int totalRecintos) {
        Recinto.totalRecintos = totalRecintos;
    }

    /**
     * Convierte el objeto Recinto a su representación en formato JSON.
     *
     * @return Un objeto {@code JSONObject} que representa el estado completo del recinto.
     */
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

    /**
     * Obtiene el identificador único del recinto.
     *
     * @return El ID del recinto.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del recinto.
     *
     * @return El nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el recinto.
     *
     * @param nombre El nuevo nombre del recinto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dirección del recinto.
     *
     * @return La dirección.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece una nueva dirección para el recinto.
     *
     * @param direccion La nueva dirección del recinto.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene la capacidad máxima del recinto.
     *
     * @return La capacidad máxima.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece una nueva capacidad máxima para el recinto.
     *
     * @param capacidad La nueva capacidad máxima.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Obtiene la lista de sectores dentro del recinto.
     *
     * @return La {@code ArrayList} de objetos {@link Sector}.
     */
    public ArrayList<Sector> getSectores() {
        return sectores;
    }

    /**
     * Establece una nueva lista de sectores para el recinto.
     *
     * @param sectores La nueva {@code ArrayList} de {@link Sector}es.
     */
    public void setSectores(ArrayList<Sector> sectores) {
        this.sectores = sectores;
    }

    /**
     * Devuelve una representación en cadena del objeto Recinto,
     * incluyendo todos sus atributos y los sectores que lo componen.
     *
     * @return Una cadena que representa el estado del Recinto.
     */
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