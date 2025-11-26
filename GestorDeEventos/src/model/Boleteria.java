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
            } else if (jUsuarios.getJSONObject(i).has("eventosCreados")) {
                this.usuarios.add(new Organizador(jUsuarios.getJSONObject(i)));
            } else {
                this.usuarios.add(new Administrador(jUsuarios.getJSONObject(i)));
            }
        }
        for (int i = 0; i < jEventos.length(); i++) {
            this.eventos.add(new Evento(jEventos.getJSONObject(i)));
        }
        for (int i = 0; i < jVendidos.length(); i++) {
            this.vendidos.add(new Ticket(jVendidos.getJSONObject(i)));
        }
        actualizarContadores();
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
            } else if (u instanceof Organizador) {
                jarrUsuarios.put(((Organizador) u).toJSON());
            } else {
                jarrUsuarios.put(((Administrador) u).toJSON());
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
    public String calcularRecaudacion() {
        String datos;
        if (this.getVendidos().isEmpty()) {
           datos=("No hay datos de tickets vendidos.");

        } else {
            double recaudacion = 0;
            for (Ticket t : this.getVendidos()) {
                if (t != null) {
                    recaudacion += t.getPrecio();
                }
            }
            datos=("Su recaudación total es de: $%.2f%n" + recaudacion);
        }
        return datos;
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
        sb.append("/////////////////////// Eventos ///////////////////////\n");
        sb.append("Total de eventos: ").append(eventos.getElementos().size());
        for (Evento e : this.eventos.getElementos()) {
            sb.append("\nId:").append(e.getId()).append("\tNombre: ").append(e.getNombre())
                    .append("\tFunciones disponibles: ").append(e.getFunciones().size());
        }
        sb.append("\n/////////////////////// Fin eventos ///////////////////////\n");
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

    public ArrayList<Usuario> getUsuariosActivos() {
        ArrayList<Usuario> usuariosActivos = new ArrayList<>();
        for (Usuario u : usuarios.getElementos()) {
            if (u.isActivo()) usuariosActivos.add(u);
        }
        return usuariosActivos;
    }

    public ArrayList<Usuario> getUsuariosInactivos() {
        ArrayList<Usuario> usuariosInactivos = new ArrayList<>();
        for (Usuario u : usuarios.getElementos()) {
            if (!u.isActivo()) usuariosInactivos.add(u);
        }
        return usuariosInactivos;
    }

    public String mostrarUsuariosActivos() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : getUsuariosActivos()) {
            if (u.isActivo()) {
                sb.append("ID: ").append(u.getId()).append("\tNombre: ").append(u.getNombre())
                        .append("\tEmail: ").append(u.getEmail()).append("\tTipo: ").append(u.getClass().getSimpleName())
                        .append("\tActivo: ").append((u.isActivo()) ? "Si" : "No").append("\n");
            }
        }

        return sb.toString();
    }

    public String mostrarUsuariosInactivos() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : getUsuariosInactivos()) {
            if (!u.isActivo()) {
                sb.append("ID: ").append(u.getId()).append("\tNombre: ").append(u.getNombre())
                        .append("\tEmail: ").append(u.getEmail()).append("\tTipo: ").append(u.getClass().getSimpleName())
                        .append("\tActivo: ").append(u.isActivo()).append("\n");
                ;
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

    private void actualizarContadores() {
        int maxEventoId = -1;
        int maxFuncionId = -1;
        int maxUsuarioId = -1;
        int maxRecintoId = -1;
        int maxSectorId = -1;
        int maxAsientoId = -1;

        for (Usuario u : usuarios.getElementos()) {
            if (u.getId() > maxUsuarioId) maxUsuarioId = u.getId();
        }

        for (Evento e : eventos.getElementos()) {
            if (e.getId() > maxEventoId) maxEventoId = e.getId();
            for (Funcion f : e.getFunciones()) {
                if (f.getId() > maxFuncionId) maxFuncionId = f.getId();
                if (f.getRecinto().getId() > maxRecintoId) maxRecintoId = f.getRecinto().getId();
                for (Sector s : f.getRecinto().getSectores()) {
                    if (s.getId() > maxSectorId) maxSectorId = s.getId();
                    for (Asiento a : s.getAsientos()) {
                        if (a.getId() > maxAsientoId) maxAsientoId = a.getId();
                    }
                }
            }
        }


        Usuario.setTotalUsuarios(maxUsuarioId + 1);
        Evento.setTotalEventos(maxEventoId + 1);
        Funcion.setTotalFunciones(maxFuncionId + 1);
        Recinto.setTotalRecintos(maxRecintoId + 1);
        Sector.setTotalSectores(maxSectorId + 1);
        Asiento.setTotalAsientos(maxAsientoId + 1);
    }

}
