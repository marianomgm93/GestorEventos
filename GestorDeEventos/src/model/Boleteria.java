package model;

import java.util.ArrayList;

public class Boleteria {
    private ArrayList<Usuario> Usuarios;
    private ArrayList<Evento> Eventos;

    public Boleteria(ArrayList<Usuario> usuarios, ArrayList<Evento> eventos) {
        Usuarios = usuarios;
        Eventos = eventos;
    }

    public Boleteria(ArrayList<Usuario> usuarios) {
        Usuarios = usuarios;
    }

    public ArrayList<Usuario> getUsuarios() {
        return Usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        Usuarios = usuarios;
    }

    public ArrayList<Evento> getEventos() {
        return Eventos;
    }

    public void setEventos(ArrayList<Evento> eventos) {
        Eventos = eventos;
    }
}
