package model;

import org.json.JSONObject;

public class Administrador extends Usuario{
    public Administrador(int id, String nombre, String email, String contrasenia) {
        super(id, nombre, email, contrasenia);
    }
    public Administrador(JSONObject o) {
        // Llama al constructor de la superclase para inicializar los atributos de Usuario
        super(o.getInt("id"), o.getString("nombre"), o.getString("email"), o.getString("contrasenia"));
    }
        public JSONObject toJSON(){
        JSONObject o=new JSONObject();
        o.put("id",super.getId());
        o.put("nombre",super.getNombre());
        o.put("email", super.getEmail());
        o.put("contrasenia",super.getContrasenia());
        return o;
    }
}
