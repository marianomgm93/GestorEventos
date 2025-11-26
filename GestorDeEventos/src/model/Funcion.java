/**
 * Representa una sesión o pase específico de un Evento, incluyendo la hora,
 * el {@link Recinto} donde se realiza y el precio base de la entrada.
 */
package model;

import exceptions.ElementoNoEncontradoException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Funcion {

    private static int totalFunciones;
    private int id;
    private LocalDateTime fechayHora;
    private Recinto recinto;
    private double precioBase;


    public Funcion(LocalDateTime hora, Recinto recinto, double precioBase) {
        this.id = totalFunciones++;
        this.fechayHora = hora;
        this.recinto = recinto;
        this.precioBase = precioBase;
    }


    public Funcion(JSONObject o){
        this.id=o.getInt("id");
        this.fechayHora=LocalDateTime.parse(o.getString("fechayHora"));
        this.recinto=new Recinto(o.getJSONObject("recinto"));
        this.precioBase=o.getDouble("precioBase");
    }


    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id",this.id);
        o.put("fechayHora",this.fechayHora);
        o.put("recinto",this.recinto.toJSON());
        o.put("precioBase",this.precioBase);
        return o;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setTotalFunciones(int totalFunciones) {
        Funcion.totalFunciones = totalFunciones;
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

    public String asientosDisponibles(){
        StringBuilder sb=new StringBuilder();
        int disponibilidad;
        sb.append("/////////////// Disponibilidad ///////////////\n");
        for (Sector s: this.recinto.getSectores()){
                sb.append("\nSector: ").append(s.getId()).append("\t").append(s.getNombre());
                disponibilidad=s.getAsientosDisponibles().size();
                sb.append("\tDisponibilidad: ").append(disponibilidad).append("/").append(s.getAsientos().size());
            }
        sb.append("\n/////////////// Fin disponibilidad ///////////////\n");

        return sb.toString();
    }

    public int cantidadAsientosDisponibles(){
        int disponibilidad=0;
        for (Sector s: this.recinto.getSectores()){
            disponibilidad+=s.getAsientosDisponibles().size();
        }

        return disponibilidad;
    }
    public Sector buscarSectorPorId(int id) throws ElementoNoEncontradoException{
        for(Sector s: this.recinto.getSectores()){
            if(s.getId()==id) return s;
        }
        throw new ElementoNoEncontradoException("El no se encontró el sector");
    }
    public LocalDateTime getFechayHora() {
        return fechayHora;
    }

    public void setFechayHora(LocalDateTime fechayHora) {
        this.fechayHora = fechayHora;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Funcion funcion)) return false;
        return Objects.equals(fechayHora, funcion.fechayHora) && Objects.equals(recinto, funcion.recinto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechayHora, recinto);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Funcion{");
        sb.append("id=").append(id);
        sb.append(", fecha y hora='").append(fechayHora).append('\'');
        sb.append(", recinto=").append(recinto);
        sb.append(", precioBase=").append(precioBase);
        sb.append('}');
        return sb.toString();
    }
}