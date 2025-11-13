package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Vendedor extends Usuario {
    private ArrayList<Ticket> ticketsVendidos;

    public Vendedor() {
        ticketsVendidos=new ArrayList<>();
    }

    public Vendedor(String nombre, String email, String contrasenia) {
        super(nombre, email, contrasenia);
        this.ticketsVendidos = new ArrayList<>();
    }

    public Vendedor(JSONObject o) {
        super(o.getInt("id"), o.getString("nombre"), o.getString("email"), o.getString("contrasenia"));
        this.ticketsVendidos = new ArrayList<>();
        JSONArray jarr = o.getJSONArray("ticketsVendidos");
        for (int i = 0; i < jarr.length(); i++) {
            this.ticketsVendidos.add(new Ticket(jarr.getJSONObject(i)));
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vendedor{");
        sb.append(super.toString());
        sb.append("ticketsVendidos=").append(ticketsVendidos);
        sb.append('}');
        return sb.toString();
    }

    public ArrayList<Ticket> getTicketsVendidos() {
        return ticketsVendidos;
    }


    @Override
    public void setId(int id) {

    }
}
