package service;

import Utilidades.Validacion;
import model.*;
import java.util.Scanner;

public class AdministradorService {

    // 1. Ver todos los usuarios
    public String verUsuarios(Boleteria boleteria) {
        StringBuilder sb = new StringBuilder();
        sb.append("════════════════════════════════════════════════════════════\n");
        sb.append("                   TODOS LOS USUARIOS DEL SISTEMA\n");
        sb.append("════════════════════════════════════════════════════════════\n");
        if (boleteria.getUsuarios().getElementos().isEmpty()) {
            sb.append("No hay usuarios registrados.\n");
        } else {
            sb.append(String.format("%-5s %-20s %-30s %-12s %-6s%n", "ID", "Nombre", "Email", "Rol", "Estado"));
            sb.append("────────────────────────────────────────────────────────────────────\n");
            for (Usuario u : boleteria.getUsuarios().getElementos()) {
                String rol = u instanceof Administrador ? "Admin" :
                        u instanceof Organizador ? "Organizador" : "Vendedor";
                String estado = u.isActivo() ? "Activo" : "Bloqueado";
                sb.append(String.format("%-5d %-20s %-30s %-12s %-6s%n",
                        u.getId(), u.getNombre(), u.getEmail(), rol, estado));
            }
        }
        return sb.toString();
    }

    // 2. Bloquear usuario
    public void eliminarUsuario(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println(verUsuarios(boleteria));
        System.out.println("Ingrese el ID del usuario a BLOQUEAR:");
        int id = Validacion.validarEntero(sc);
        Usuario usuario = boleteria.getUsuarios().buscarElementoId(id);

        if (usuario == null) {
            System.out.println("No existe un usuario con ese ID.");
        } else if (usuario instanceof Administrador) {
            System.out.println("No se puede bloquear a un Administrador.");
        } else if (!usuario.isActivo()) {
            System.out.println("Este usuario ya está bloqueado.");
        } else {
            usuario.setActivo(false);
            boleteria.guardarBoleteria(archivo);
            System.out.println("Usuario \"" + usuario.getNombre() + "\" bloqueado correctamente.");
        }
    }

    // 3. Desbloquear usuario
    public void deseliminarUsuario(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println("════════════════ USUARIOS BLOQUEADOS ════════════════");
        verUsuariosInactivos(boleteria);
        System.out.println("Ingrese el ID del usuario a DESBLOQUEAR:");
        int id = Validacion.validarEntero(sc);
        Usuario usuario = boleteria.getUsuarios().buscarElementoId(id);

        if (usuario == null) {
            System.out.println("No existe un usuario con ese ID.");
        } else if (!usuario.isActivo()) {
            usuario.setActivo(true);
            boleteria.guardarBoleteria(archivo);
            System.out.println("Usuario \"" + usuario.getNombre() + "\" desbloqueado correctamente.");
        } else {
            System.out.println("Este usuario ya está activo.");
        }
    }

    // 4. Ver usuarios activos
    public void verUsuariosActivos(Boleteria boleteria) {
        System.out.println(boleteria.mostrarUsuariosActivos());
    }

    // 5. Ver usuarios inactivos
    public void verUsuariosInactivos(Boleteria boleteria) {
        System.out.println(boleteria.mostrarUsuariosInactivos());
    }

    // 6. Recaudación total
    public void verRecaudacion(Boleteria boleteria) {
        System.out.println(boleteria.calcularRecaudacion());
    }

    // 7. Ver todos los eventos del sistema (activos e inactivos)
    public void verTodosLosEventos(Boleteria boleteria) {
        System.out.println("════════════════ TODOS LOS EVENTOS DEL SISTEMA ════════════════");
        if (boleteria.getEventos().getElementos().isEmpty()) {
            System.out.println("No hay eventos registrados.");
            return;
        }
        System.out.printf("%-5s %-30s %-15s %-10s %-8s%n", "ID", "Nombre", "Categoría", "Estado", "Funciones");
        System.out.println("────────────────────────────────────────────────────────────────────");
        for (Evento e : boleteria.getEventos().getElementos()) {
            String estado = e.isActivo() ? "ACTIVO" : "DADO DE BAJA";
            System.out.printf("%-5d %-30s %-15s %-10s %-8d%n",
                    e.getId(), e.getNombre(), e.getCategoria(), estado, e.getFunciones().size());
        }
    }

    // 8. Recaudación por evento
    public void verRecaudacionPorEvento(Scanner sc, Boleteria boleteria) {
        System.out.println("════════════════ RECAUDACIÓN POR EVENTO ═════════════════════");
        if (boleteria.getEventos().getElementos().isEmpty()) {
            System.out.println("No hay eventos registrados.");
            return;
        }

        for (Evento e : boleteria.getEventos().getElementos()) {
            double total = 0;
            for (Ticket t : boleteria.getVendidos()) {
                if (t.getEventoId() == e.getId()) {
                    total += t.getPrecio();
                }
            }
            String estado = e.isActivo() ? "ACTIVO" : "DADO DE BAJA";
            System.out.printf("ID %-3d | %-25s | %-10s | Recaudado: $%.2f%n",
                    e.getId(), e.getNombre(), estado, total);
        }
        System.out.println("────────────────────────────────────────────────────────────");
    }

    // 9. Ranking de vendedores por tickets vendidos
    public void rankingVendedores(Boleteria boleteria) {
        System.out.println("════════════════ RANKING DE VENDEDORES ═════════════════════");
        var vendedores = boleteria.getUsuarios().getElementos().stream()
                .filter(u -> u instanceof Vendedor && u.isActivo())
                .map(u -> (Vendedor) u)
                .sorted((v1, v2) -> Integer.compare(v2.getTicketsVendidos().size(), v1.getTicketsVendidos().size()))
                .toList();

        if (vendedores.isEmpty()) {
            System.out.println("No hay vendedores activos.");
            return;
        }

        System.out.printf("%-5s %-20s %-25s %-8s%n", "Puesto", "Nombre", "Email", "Tickets");
        System.out.println("────────────────────────────────────────────────────────────");
        int puesto = 1;
        for (Vendedor v : vendedores) {
            System.out.printf("%-5d %-20s %-25s %-8d%n",
                    puesto++, v.getNombre(), v.getEmail(), v.getTicketsVendidos().size());
        }
    }

    // 10. Estadísticas generales del sistema
    public void verEstadisticasGenerales(Boleteria boleteria) {
        long eventosActivos = boleteria.getEventos().getElementos().stream().filter(Evento::isActivo).count();
        long eventosInactivos = boleteria.getEventos().getElementos().size() - eventosActivos;
        long totalTickets = boleteria.getVendidos().size();
        double recaudacionTotal = boleteria.getVendidos().stream()
                .mapToDouble(Ticket::getPrecio).sum();
        long usuariosTotales = boleteria.getUsuarios().getElementos().size();

        System.out.println("════════════════ ESTADÍSTICAS GENERALES DEL SISTEMA ════════════════");
        System.out.printf("Eventos activos           : %d%n", eventosActivos);
        System.out.printf("Eventos dados de baja     : %d%n", eventosInactivos);
        System.out.printf("Total tickets vendidos    : %d%n", totalTickets);
        System.out.printf("Recaudación total         : $%.2f%n", recaudacionTotal);
        System.out.printf("Usuarios registrados      : %d%n", usuariosTotales);
        System.out.println("════════════════════════════════════════════════════════════");
    }
}