package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.UsuarioInvalidoException;
import model.*;

import java.util.Scanner;

public class Menu {

    private final OrganizadorService os = new OrganizadorService();
    private final VendedorService vs = new VendedorService();
    private final AdministradorService as = new AdministradorService();

    public void inicio(Scanner sc, Boleteria boleteria, String archivo) {
        int opcion;
        do {
            System.out.println("""
                    ═══════════════════════════════════════════════════════════════
                                      BIENVENIDO A BOLETERÍA
                    ═══════════════════════════════════════════════════════════════
                    1 - Registrar nuevo usuario
                    2 - Iniciar sesión
                    0 - Salir del sistema
                    """);
            opcion = Validacion.validarEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuNuevoUsuario(sc, boleteria, archivo);
                    break;
                case 2:
                    loginMenu(sc, boleteria, archivo);
                    break;
                case 0:
                    System.out.println("""
                            ═══════════════════════════════════════════════════════════════
                                              ¡GRACIAS POR USAR BOLETERÍA!
                                                ¡Hasta pronto!
                            ═══════════════════════════════════════════════════════════════
                            """);
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);
    }

    private void menuNuevoUsuario(Scanner sc, Boleteria boleteria, String archivo) {
        int opcion;
        do {
            System.out.println("""
                    ═══════════════════════════════════════════════════════════════
                                       CREAR NUEVA CUENTA
                    ═══════════════════════════════════════════════════════════════
                    1 - Crear cuenta de Organizador
                    2 - Crear cuenta de Vendedor
                    0 - Volver atrás
                    """);
            opcion = Validacion.validarEntero(sc, "Seleccione tipo de cuenta: ");

            switch (opcion) {
                case 1:
                    os.crearOrganizador(sc, boleteria, archivo);
                    break;
                case 2:
                    vs.crearVendedor(sc, boleteria, archivo);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } while (opcion != 0);
    }

    private void loginMenu(Scanner sc, Boleteria boleteria, String archivo) {
        System.out.println("════════════════════════════════ Iniciar Sesión ════════════════════════════════");
        System.out.print("Email       → ");
        String email = sc.nextLine().trim();
        System.out.print("Contraseña  → ");
        String contrasenia = sc.nextLine();

        try {
            Usuario usuario = boleteria.buscarPorEmail(email);
            if (Validacion.validarUsuario(usuario, contrasenia)) {
                if (!usuario.isActivo()) {
                    System.out.println("Tu cuenta está bloqueada. Contacta al administrador.");
                    return;
                }
                System.out.println("¡Bienvenido, " + usuario.getNombre() + "!\n");

                if (usuario instanceof Vendedor) {
                    menuVendedor(sc, boleteria, (Vendedor) usuario, archivo);
                } else if (usuario instanceof Organizador) {
                    menuOrganizador(sc, boleteria, (Organizador) usuario, archivo);
                } else if (usuario instanceof Administrador) {
                    menuAdministrador(sc, boleteria, (Administrador) usuario, archivo);
                }
            } else {
                System.out.println("Contraseña incorrecta.");
            }
        } catch (UsuarioInvalidoException e) {
            System.out.println(e.getMessage());
        }
    }

    // ============================== PANEL DEL VENDEDOR ==============================
    private void menuVendedor(Scanner sc, Boleteria b, Vendedor vendedor, String archivo) {
        String opcion;
        do {
            System.out.println("""
            ═══════════════════════════════════════════════════════════════
                                  PANEL DEL VENDEDOR
            ═══════════════════════════════════════════════════════════════
            1 - Ver eventos disponibles
            2 - Vender nuevo ticket
            3 - Ver mis tickets vendidos
            4 - Calcular mi recaudación
            5 - Reimprimir ticket
            6 - Ver mis estadísticas
            7 - Cambiar mi contraseña
            
            0 - Cerrar sesión
            """);
            System.out.print("Opción → ");
            opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println(b.mostrarEventos());
                    break;
                case "2":
                    vs.nuevoTicket(sc, vendedor, b, archivo);
                    break;
                case "3":
                    vs.verMisTickets(vendedor, b);
                    break;
                case "4":
                    vs.calcularRecaudacion(vendedor);
                    break;
                case "5":
                    vs.reimprimirTicket(sc, vendedor, b);
                    break;
                case "6":
                    vs.verMisEstadisticas(vendedor);
                    break;
                case "7":
                    UtilidadesGenerales.cambiarContrasena(sc, vendedor, archivo, b);
                    break;
                case "0":
                    System.out.println("Sesión cerrada. ¡Hasta luego, " + vendedor.getNombre() + "!");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            // Pausa después de todas las opciones excepto vender ticket y cerrar sesión
            if (!opcion.equals("0") && !opcion.equals("2")) {
                System.out.println("\nPresione ENTER para continuar...");
                sc.nextLine();
            }
        } while (!opcion.equals("0"));
    }

    // ============================== PANEL DEL ORGANIZADOR ==============================
    private void menuOrganizador(Scanner sc, Boleteria b, Organizador organizador, String archivo) {
        String opcion;
        do {
            System.out.println("""
                    ═══════════════════════════════════════════════════════════════
                                         PANEL DEL ORGANIZADOR
                    ═══════════════════════════════════════════════════════════════
                    1 - Ver todos los eventos del sistema
                    2 - Crear nuevo evento
                    3 - Agregar función a un evento activo
                    4 - Ver mis eventos (activos/inactivos)
                    5 - Ver funciones de un evento
                    6 - Dar de baja un evento
                    7 - Reactivar un evento dado de baja
                    8 - Cambiar mi contraseña
                    0 - Cerrar sesión
                    """);
            System.out.print("Opción → ");
            opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    System.out.println(b.mostrarEventos());
                    break;
                case "2":
                    os.nuevoEvento(sc, organizador, b, archivo);
                    break;
                case "3":
                    os.agregarFuncion(sc, organizador, archivo, b);
                    break;
                case "4":
                    os.verMisEventos(organizador);
                    break;
                case "5":
                    os.verMisFunciones(sc, organizador);
                    break;
                case "6":
                    os.darDeBajaEvento(sc, organizador, b, archivo);
                    break;
                case "7":
                    os.reactivarEvento(sc, organizador, b, archivo);
                    break;
                case "8":
                    UtilidadesGenerales.cambiarContrasena(sc, organizador, archivo, b);
                    break;
                case "0":
                    System.out.println("Sesión cerrada. ¡Hasta luego, " + organizador.getNombre() + "!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
            if (!opcion.equals("0")) {
                System.out.println("\nPresione ENTER para continuar...");
                sc.nextLine();
            }
        } while (!opcion.equals("0"));
    }

    // ============================== PANEL DE ADMINISTRACIÓN ==============================
    private void menuAdministrador(Scanner sc, Boleteria boleteria, Administrador admin, String archivo) {
        int opcion;
        do {
            System.out.println("""
                    ═══════════════════════════════════════════════════════════════
                                        PANEL DE ADMINISTRACIÓN
                    ═══════════════════════════════════════════════════════════════
                    1  - Ver todos los usuarios
                    2  - Bloquear usuario
                    3  - Desbloquear usuario
                    4  - Ver usuarios activos
                    5  - Ver usuarios inactivos
                    6  - Ver todos los eventos del sistema
                    7  - Ver recaudación total del sistema
                    8  - Ver recaudación por evento
                    9  - Ranking de vendedores (más ventas)
                    10 - Estadísticas generales del sistema
                    11 - Cambiar mi contraseña
                    0  - Cerrar sesión
                    """);
            opcion = Validacion.validarEntero(sc, "Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println(as.verUsuarios(boleteria));
                    break;
                case 2:
                    as.eliminarUsuario(sc, boleteria, archivo);
                    break;
                case 3:
                    as.deseliminarUsuario(sc, boleteria, archivo);
                    break;
                case 4:
                    as.verUsuariosActivos(boleteria);
                    break;
                case 5:
                    as.verUsuariosInactivos(boleteria);
                    break;
                case 6:
                    as.verTodosLosEventos(boleteria);
                    break;
                case 7:
                    as.verRecaudacion(boleteria);
                    break;
                case 8:
                    as.verRecaudacionPorEvento(sc, boleteria);
                    break;
                case 9:
                    as.rankingVendedores(boleteria);
                    break;
                case 10:
                    as.verEstadisticasGenerales(boleteria);
                    break;
                case 11:
                    UtilidadesGenerales.cambiarContrasena(sc, admin, archivo, boleteria);
                    break;
                case 0:
                    System.out.println("Sesión de administrador cerrada.");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
            if (opcion != 0) {
                System.out.println("\nPresione ENTER para continuar...");
                sc.nextLine();
            }
        } while (opcion != 0);
    }
}