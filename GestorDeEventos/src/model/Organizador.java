package model;

import exceptions.ElementoNoEncontradoException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Organizador extends Usuario {
    private ArrayList<Evento> eventosCreados;

    public Organizador() {
        this.eventosCreados=new ArrayList<>();
    }

    public Organizador(JSONObject o) {
        super(o.getInt("id"), o.getString("nombre"), o.getString("email"), o.getString("contrasenia"),o.getBoolean("activo"));
        this.eventosCreados = new ArrayList<>();
        JSONArray jarr = o.getJSONArray("eventosCreados");
        for (int i = 0; i < jarr.length(); i++) {
            this.eventosCreados.add(new Evento(jarr.getJSONObject(i)));
        }
    }

    public Organizador(String nombre, String email, String contrasenia) {
        super(nombre, email, contrasenia);
        this.eventosCreados = new ArrayList<>();
    }
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("id",super.getId());
        o.put("nombre",super.getNombre());
        o.put("email", super.getEmail());
        o.put("contrasenia",super.getContrasenia());
        o.put("activo", super.isActivo());
        JSONArray jarr=new JSONArray();
        for(Evento e: eventosCreados){
            jarr.put(e.toJSON());
        }
        o.put("eventosCreados",jarr);
        return o;
    }

    public ArrayList<Evento> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(ArrayList<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Organizador{");
        sb.append("eventosCreados=").append(eventosCreados);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Organizador that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(eventosCreados, that.eventosCreados);
    }
    public Evento buscarEvento(int id) throws ElementoNoEncontradoException{
        for(Evento e:this.eventosCreados){
            if(e.getId()==id) return e;
        }
        throw new ElementoNoEncontradoException("No tenes un evento con ese id");
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eventosCreados);
    }

    @Override
    public void setId(int id) {

    }
}
