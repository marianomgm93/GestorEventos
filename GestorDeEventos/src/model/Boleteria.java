package model;

import Utilidades.JSONUtiles;
import exceptions.UsuarioInvalidoException;
import org.json.JSONArray;
import org.json.JSONObject;
import service.OrganizadorService;
import service.VendedorService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Boleteria {

    private Lista<Usuario> usuarios;
    private Lista<Evento> eventos;
    private ArrayList<Ticket> vendidos;


    public Boleteria() {
        usuarios = new Lista<>();
        eventos = new Lista<>();
        vendidos = new ArrayList<>();
    }

    public Boleteria(Lista<Usuario> usuarios, Lista<Evento> eventos, ArrayList<Ticket> vendidos) {
        this.usuarios = usuarios;
        this.eventos = eventos;
        this.vendidos = vendidos;
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
        this.vendidos = new ArrayList<>();
        JSONArray jUsuarios = o.getJSONArray("usuarios");
        JSONArray jEventos = o.getJSONArray("eventos");
        JSONArray jVendidos = o.getJSONArray("vendidos");
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
        for (int i = 0; i < jVendidos.length(); i++) {
            this.vendidos.add(new Ticket(jVendidos.getJSONObject(i)));
        }
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        JSONArray jarrEventos = new JSONArray();
        for (Evento e : this.eventos.getElementos()) {
            jarrEventos.put(e.toJSON());
        }
        JSONArray jarrUsuarios = new JSONArray();
        for (Usuario u : this.usuarios.getElementos()) {
            if (u instanceof Vendedor) {
                jarrUsuarios.put(((Vendedor) u).toJSON());
            } else {
                jarrUsuarios.put(((Organizador) u).toJSON());
            }
        }
        JSONArray jarrVendidos = new JSONArray();
        for (Ticket t : this.vendidos) {
            jarrVendidos.put(t.toJSON());
        }

        o.put("eventos", jarrEventos);
        o.put("usuarios", jarrUsuarios);
        o.put("vendidos", jarrVendidos);
        return o;
    }

    public void guardarUsuario(Usuario usuario, String archivo) {
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

    public ArrayList<Ticket> getVendidos() {
        return vendidos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Boleteria{");
        sb.append("usuarios=").append(usuarios);
        sb.append(", eventos=").append(eventos);
        sb.append(", vendidos=").append(vendidos);
        sb.append('}');
        return sb.toString();
    }

    public String mostrarOrganizadores() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : this.usuarios.getElementos()) {
            if (u instanceof Organizador) {
                sb.append(u).append("\n");
            }
        }
        return sb.toString();
    }

    public String mostrarEventos() {
        StringBuilder sb = new StringBuilder();
        for (Evento e : this.eventos.getElementos()) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }

    public String mostrarVendedores() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : this.usuarios.getElementos()) {
                if (u instanceof Vendedor) {
                sb.append(u).append("\n");
            }
        }
        return sb.toString();
    }

    public Usuario buscarPorEmail(String email) throws UsuarioInvalidoException {
        boolean flag = false;
        Usuario usuario = null;
        for (Usuario u : this.usuarios.getElementos()) {
            if (u.getEmail().equals(email)) {
                flag = true;
                usuario = u;
            }
        }
        if (!flag) throw new UsuarioInvalidoException("No hay ningun usuario registrado con ese email");

        return usuario;
    }
}
