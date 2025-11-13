package model;

import org.json.JSONArray;
import org.json.JSONObject;
import service.OrganizadorService;

import java.util.Scanner;

public class Boleteria {
    private Lista<Usuario> usuarios;
    private Lista<Evento> eventos;

    public Boleteria() {
        usuarios=new Lista<>();
        eventos=new Lista<>();
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

    public Boleteria(JSONObject o) {
        this.usuarios = new Lista<>();
        this.eventos = new Lista<>();
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
        organizadorService.agregarFuncion(sc,this.eventos.getElementos().getLast());
    }

}
