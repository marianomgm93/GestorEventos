package model;

import Utilidades.JSONUtiles;
import exceptions.UsuarioInvalidoException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Boleteria {
    private Lista<Usuario> usuarios;
    private Lista<Evento> eventos;
    private ArrayList<Ticket> vendidos;

    public Boleteria() {
        usuarios = new Lista<>();
        eventos = new Lista<>();
        vendidos = new ArrayList<>();
    }

    public Lista<Usuario> getUsuarios() { return usuarios; }
    public Lista<Evento> getEventos() { return eventos; }
    public ArrayList<Ticket> getVendidos() { return vendidos; }

    // ====================== CARGA DESDE JSON ======================
    public void fromJSON(String archivo) throws Exception{
        JSONObject o = new JSONObject(JSONUtiles.downloadJSON(archivo));
        this.usuarios = new Lista<>();
        this.eventos = new Lista<>();
        this.vendidos = new ArrayList<>();

        JSONArray jUsuarios = o.getJSONArray("usuarios");
        JSONArray jEventos = o.getJSONArray("eventos");
        JSONArray jVendidos = o.getJSONArray("vendidos");

        // Cargar usuarios
        for (int i = 0; i < jUsuarios.length(); i++) {
            JSONObject ju = jUsuarios.getJSONObject(i);
            if (ju.has("ticketsVendidos")) {
                this.usuarios.add(new Vendedor(ju));
            } else if (ju.has("eventosCreados")) {
                this.usuarios.add(new Organizador(ju));
            } else {
                this.usuarios.add(new Administrador(ju));
            }
        }

        // Cargar eventos (con campo activo)
        for (int i = 0; i < jEventos.length(); i++) {
            Evento e = new Evento(jEventos.getJSONObject(i));
            this.eventos.add(e);
        }

        // Cargar tickets vendidos
        for (int i = 0; i < jVendidos.length(); i++) {
            this.vendidos.add(new Ticket(jVendidos.getJSONObject(i)));
        }

        actualizarContadores();
    }

    // ====================== GUARDADO ======================
    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        JSONArray jarrEventos = new JSONArray();
        JSONArray jarrUsuarios = new JSONArray();
        JSONArray jarrVendidos = new JSONArray();

        for (Evento e : this.eventos.getElementos()) {
            jarrEventos.put(e.toJSON());
        }
        for (Usuario u : this.usuarios.getElementos()) {
            if (u instanceof Vendedor v) {
                jarrUsuarios.put(v.toJSON());
            } else if (u instanceof Organizador org) {
                jarrUsuarios.put(org.toJSON());
            } else if (u instanceof Administrador adm) {
                jarrUsuarios.put(adm.toJSON());
            }
        }
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

    // ====================== RECAUDACIÓN ======================
    public String calcularRecaudacion() {
        if (this.vendidos.isEmpty()) {
            return "No hay datos de tickets vendidos.";
        }

        double total = 0;
        for (Ticket t : this.vendidos) {
            if (t != null) {
                total += t.getPrecio();
            }
        }
        return String.format("Recaudación total del sistema: $%.2f", total);
    }

    // ====================== MOSTRAR ======================
    public String mostrarEventos() {
        StringBuilder sb = new StringBuilder();
        sb.append("/////////////////////// EVENTOS DISPONIBLES ///////////////////////\n");
        int activos = 0;

        for (Evento e : this.eventos.getElementos()) {
            if (e.isActivo()) {
                activos++;
                sb.append("ID: ").append(e.getId())
                        .append(" | Nombre: ").append(e.getNombre())
                        .append(" | Categoría: ").append(e.getCategoria())
                        .append(" | Funciones: ").append(e.getFunciones().size())
                        .append("\n");
            }
        }

        if (activos == 0) {
            sb.append("No hay eventos activos disponibles en este momento.\n");
        } else {
            sb.append("Total de eventos activos: ").append(activos).append("\n");
        }
        sb.append("////////////////////////////////////////////////////////////////\n");
        return sb.toString();
    }

    public String mostrarOrganizadores() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : usuarios.getElementos()) {
            if (u instanceof Organizador) {
                sb.append(u).append("\n");
            }
        }
        return sb.length() == 0 ? "No hay organizadores registrados.\n" : sb.toString();
    }

    public String mostrarVendedores() {
        StringBuilder sb = new StringBuilder();
        for (Usuario u : usuarios.getElementos()) {
            if (u instanceof Vendedor) {
                sb.append(u).append("\n");
            }
        }
        return sb.length() == 0 ? "No hay vendedores registrados.\n" : sb.toString();
    }

    // ====================== USUARIOS ACTIVOS/INACTIVOS ======================
    public String mostrarUsuariosActivos() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================== USUARIOS ACTIVOS ====================\n");
        boolean hay = false;
        for (Usuario u : usuarios.getElementos()) {
            if (u.isActivo()) {
                hay = true;
                sb.append(String.format("ID: %d | %-15s | %-25s | %-10s | Activo: Sí%n",
                        u.getId(), u.getNombre(), u.getEmail(), u.getClass().getSimpleName()));
            }
        }
        if (!hay) sb.append("No hay usuarios activos.\n");
        return sb.toString();
    }

    public String mostrarUsuariosInactivos() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================== USUARIOS INACTIVOS ====================\n");
        boolean hay = false;
        for (Usuario u : usuarios.getElementos()) {
            if (!u.isActivo()) {
                hay = true;
                sb.append(String.format("ID: %d | %-15s | %-25s | %-10s | Activo: No%n",
                        u.getId(), u.getNombre(), u.getEmail(), u.getClass().getSimpleName()));
            }
        }
        if (!hay) sb.append("No hay usuarios inactivos.\n");
        return sb.toString();
    }

    // ====================== BÚSQUEDA ======================
    public Usuario buscarPorEmail(String email) throws UsuarioInvalidoException {
        for (Usuario u : this.usuarios.getElementos()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        throw new UsuarioInvalidoException("No existe usuario con email: " + email);
    }

    // ====================== ACTUALIZAR CONTADORES ======================
    public void actualizarContadores() {
        int maxEventoId = -1;
        int maxFuncionId = -1;
        int maxUsuarioId = -1;
        int maxRecintoId = -1;
        int maxSectorId = -1;
        int maxAsientoId = -1;
        int maxTicketId = -1;
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
        for (Ticket t : vendidos) {
            if (t.getId() > maxTicketId) maxTicketId = t.getId();
        }

        Usuario.setTotalUsuarios(maxUsuarioId + 1);
        Evento.setTotalEventos(maxEventoId + 1);
        Funcion.setTotalFunciones(maxFuncionId + 1);
        Recinto.setTotalRecintos(maxRecintoId + 1);
        Sector.setTotalSectores(maxSectorId + 1);
        Asiento.setTotalAsientos(maxAsientoId + 1);
        Ticket.setTotalTickets(maxTicketId + 1);
    }

}