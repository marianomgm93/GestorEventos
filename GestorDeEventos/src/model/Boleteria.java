package model;

import org.json.JSONArray;
import org.json.JSONObject;
import service.OrganizadorService;

import java.util.ArrayList;
import java.util.Scanner;

public class Boleteria {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Evento> eventos;

    public Boleteria() {
        usuarios=new ArrayList<>();
        eventos=new ArrayList<>();
    }

    public Boleteria(ArrayList<Usuario> usuarios, ArrayList<Evento> eventos) {
        this.usuarios = usuarios;
        this.eventos = eventos;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Evento> getEventos() {
        return eventos;
    }

    public Boleteria(JSONObject o) {
        this.usuarios = new ArrayList<>();
        this.eventos = new ArrayList<>();
        JSONArray jUsuarios = o.getJSONArray("usuarios");
        JSONArray jEventos = o.getJSONArray("eventos");
        for (int i = 0; i < jUsuarios.length(); i++) {
            if (jUsuarios.getJSONObject(i).getJSONArray("ticketsVendidos") == null) {
                this.usuarios.add(new Organizador(jUsuarios.getJSONObject(i)));
            } else {
                this.usuarios.add(new Vendedor(jUsuarios.getJSONObject(i)));
            }
        }
        for (int i = 0; i < jEventos.length(); i++) {
            this.eventos.add(new Evento(jEventos.getJSONObject(i)));
        }

    }
    // TODO validaciones ///
    public void nuevoEvento(Scanner sc, Organizador organizador) {
        OrganizadorService organizadorService = new OrganizadorService();
        this.eventos.add(organizadorService.nuevoEvento(sc, organizador));
        organizadorService.agregarFuncion(sc,this.eventos.getLast());
    }

}
