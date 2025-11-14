package model;

import Utilidades.JSONUtiles;
import org.json.JSONArray;
import org.json.JSONObject;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Boleteria {

    private Lista<Usuario> usuarios;
    private Lista<Evento> eventos;


    public Boleteria() {
        usuarios = new Lista<>();
        eventos = new Lista<>();
    }

    public Boleteria(Lista<Usuario> usuarios, Lista<Evento> eventos) {
        this.usuarios = usuarios;
        this.eventos = eventos;
    }

    public Lista<Usuario> getUsuarios() {
        return usuarios;
    }

    public Lista<Evento> getEventos() {
        return eventos;
    }

    public void fromJSON(String archivo) {
        JSONObject o = new JSONObject(JSONUtiles.downloadJSON(archivo));
        this.usuarios = new Lista<>();
        this.eventos = new Lista<>();
        JSONArray jUsuarios = o.getJSONArray("usuarios");
        JSONArray jEventos = o.getJSONArray("eventos");
        for (int i = 0; i < jUsuarios.length(); i++) {
            if (jUsuarios.getJSONObject(i).has("ticketsVendidos")) {
                this.usuarios.add(new Vendedor(jUsuarios.getJSONObject(i)));
            } else {
                this.usuarios.add(new Organizador(jUsuarios.getJSONObject(i)));
            }
        }
        for (int i = 0; i < jEventos.length(); i++) {
            this.eventos.add(new Evento(jEventos.getJSONObject(i)));
        }

    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        JSONArray jarrEventos = new JSONArray();
        for (Evento e : this.eventos.getElementos()) {
            jarrEventos.put(e.toJSON());
        }
        JSONArray jarrUsuarios = new JSONArray();
        for (Usuario u : usuarios.getElementos()) {
            if (u instanceof Vendedor) {
                jarrUsuarios.put(((Vendedor) u).toJSON());
            } else {
                jarrUsuarios.put(((Organizador) u).toJSON());
            }
        }
        o.put("eventos", jarrEventos);
        o.put("usuarios", jarrUsuarios);
        return o;
    }

    public void guardarUsuario(Usuario usuario,String archivo) {
        this.usuarios.add(usuario);
        guardarBoleteria(archivo);
    }

    public void guardarEvento(Evento evento, String archivo) {
        this.eventos.add(evento);
        guardarBoleteria(archivo);
    }

    public void guardarBoleteria(String archivo) {
        JSONUtiles.uploadJSON(this.toJSON(), archivo);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Boleteria{");
        sb.append("usuarios=").append(usuarios);
        sb.append(", eventos=").append(eventos);
        sb.append('}');
        return sb.toString();
    }
}
