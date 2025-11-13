package model;

import org.json.JSONArray;
import org.json.JSONObject;
import service.OrganizadorService;
import service.VendedorService;

import java.util.ArrayList;
import java.util.Scanner;

public class Boleteria {
    private ArrayList<Usuario> usuarios;
    private ArrayList<Evento> eventos;

    public Boleteria() {
        usuarios = new ArrayList<>();
        eventos = new ArrayList<>();
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

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        JSONArray jarrEventos = new JSONArray();
        for (Evento e : this.eventos) {
            jarrEventos.put(e.toJSON());
        }
        JSONArray jarrUsuarios = new JSONArray();
        for (Usuario u : usuarios) {
            if(u instanceof Vendedor){
                jarrUsuarios.put(((Vendedor) u).toJSON());
            }else{
                jarrUsuarios.put(((Organizador) u).toJSON());
            }
        }
        o.put("eventos", jarrEventos);
        o.put("usuarios", jarrUsuarios);
        return o;
    }
    public void nuevoOrganizador(Scanner sc){
        OrganizadorService o=new OrganizadorService();
        this.usuarios.add(o.crearOrganizador(sc));
    }
    public void nuevoVendedor(Scanner sc){
        VendedorService v= new VendedorService();
        this.usuarios.add(v.crearVendedor(sc));
    }
    // TODO validaciones ///
    public void nuevoEvento(Scanner sc, Organizador organizador) {
        OrganizadorService organizadorService = new OrganizadorService();
        this.eventos.add(organizadorService.nuevoEvento(sc, organizador));
    }
    public void guardarBoleteria(){

    }

}
