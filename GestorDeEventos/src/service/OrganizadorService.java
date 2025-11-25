package service;

import Utilidades.UtilidadesGenerales;
import Utilidades.Validacion;
import exceptions.*;
import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OrganizadorService {
    public void nuevoEvento(Scanner sc, Organizador organizador, Boleteria boleteria, String archivo) {

        System.out.println("Ingrese nombre del evento");
        String nombre = sc.nextLine();
        System.out.println("Ingrese una descripcion breve");
        String descripcion = sc.nextLine();
        int opcion = 0;
        Categoria categoria = null;
        do {
            System.out.println("Categorias:\n1\tCine\n2\tConcierto\n3\tTeatro\n4\tStand UP\n5\tDeportivo");
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero contemplado entre las opciones");
                sc.nextLine();
            }
        } while (opcion < 1 || opcion > 5);
        switch (opcion) {
            case 1:
                categoria = Categoria.CINE;
                break;
            case 2:
                categoria = Categoria.CONCIERTO;
                break;
            case 3:
                categoria = Categoria.TEATRO;
                break;
            case 4:
                categoria = Categoria.STAND_UP;
                break;
            case 5:
                categoria = Categoria.PARTIDO;
                break;
        }

        Evento evento = new Evento(nombre, descripcion, categoria);
        try {
            boleteria.guardarEvento(evento, archivo);
            organizador.getEventosCreados().add(evento);
            boleteria.guardarBoleteria(archivo);
            System.out.println("El evento se creo correctamente");
        } catch (UsuarioRepetidoException e) {
            System.out.println(e.getMessage());
        } catch (EventoRepetidoException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
        public void modificarEvento(Scanner sc, Boleteria boleteria, String archivo) {
            boolean flag = false;
            int eventoId;
            Evento evento;
            do {
                System.out.println("Ingrese id del evento");
                eventoId = Validacion.validarEntero(sc);
                evento = boleteria.getEventos().buscarElementoId(eventoId);
                if (evento != null) {
                    flag = true;
                }
            } while (!flag);

            System.out.println("Ingrese nombre del evento");
            String nombre = sc.nextLine();
            System.out.println("Ingrese una descripcion breve");
            String descripcion = sc.nextLine();
            int opcion = 0;
            Categoria categoria = Categoria.CINE;
            do {
                System.out.println("Categorias:\n1\tCine\n2\tConcierto\n3\tTeatro\n4\tStand UP\n5\tDeportivo");

                opcion = Validacion.validarEntero(sc);

            } while (opcion < 1 || opcion > 5);
            switch (opcion) {
                case 1:
                    categoria = Categoria.CINE;
                    break;
                case 2:
                    categoria = Categoria.CONCIERTO;
                    break;
                case 3:
                    categoria = Categoria.TEATRO;
                    break;
                case 4:
                    categoria = Categoria.STAND_UP;
                    break;
                case 5:
                    categoria = Categoria.PARTIDO;
                    break;
            }

            evento.setNombre(nombre);
            evento.setCategoria(categoria);
            evento.setDescripcion(descripcion);
            boleteria.guardarBoleteria(archivo);
        }
    */
    public void agregarFuncion(Scanner sc, Organizador organizador, String archivo, Boleteria boleteria) {
        LocalDateTime fechayHora;
        double precio = 0;
        boolean flag = false;
        if (!organizador.getEventosCreados().isEmpty()) {


            verMisEventos(organizador);
            Evento evento = null;
            do {
                System.out.println("Ingrese id del evento al que quiere agregar nuevas funciones:");
                int idEvento = Validacion.validarEntero(sc);
                try {
                    evento = organizador.buscarEvento(idEvento);
                } catch (ElementoNoEncontradoException e) {
                    System.out.println(e.getMessage());
                }


            } while (evento == null);
            fechayHora = Validacion.validarLocalDateTime(sc);

            System.out.println("Ingrese el precio base");
            do {
                try {
                    precio = sc.nextDouble();
                    sc.nextLine();
                    flag = true;
                } catch (InputMismatchException e) {
                    System.out.println("Debe ingresar un numero");
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("El numero ingresado es invalido");
                    sc.nextLine();
                }
            } while (!flag);
            Recinto recinto = nuevoRecinto(sc);
            Funcion funcion = new Funcion(fechayHora, recinto, precio);
            if (Validacion.validarFuncion(boleteria, funcion)) {
                evento.getFunciones().add(funcion);
                System.out.println("La funcion se creó correctamente");
                boleteria.guardarBoleteria(archivo);
            } else System.out.println("El recinto seleccionado se encuentra ocupado en esa fecha");

        } else {
            System.out.println("Debes tener eventos creados para agregar nuevas funciones");
        }
    }

    public Recinto nuevoRecinto(Scanner sc) {
        System.out.println("Ingrese nombre del recinto");
        String nombre = sc.nextLine();
        System.out.println("Ingrese direccion del recinto");
        String direccion = sc.nextLine();
        int capacidad = 0;
        ArrayList<Sector> sectores = generarSectores(sc);
        for (int i = 0; i < sectores.size(); i++) {
            capacidad += sectores.get(i).getAsientos().size();
        }
        return new Recinto(nombre, direccion, capacidad, sectores);
    }

    public ArrayList<Sector> generarSectores(Scanner sc) {
        int cantidadSectores = 0;
        double extra = 0;
        boolean tieneAsientos = false;

        cantidadSectores = Validacion.validarEntero(sc, "Ingrese la cantidad de sectores que desea agregar (1 - 6): ", 1, 6);
        ArrayList<Sector> sectores = new ArrayList<>();
        String nombre;
        String option;
        int cantidadAsientos = 0;
        for (int i = 0; i < cantidadSectores; i++) {
            System.out.println("Ingrese nombre del sector: " + (i + 1));
            nombre = sc.nextLine();
            extra = Validacion.validarPrecio(sc, "Ingrese un valor agregado para este sector");
            System.out.println("El sector tiene asientos? S/N");
            option = sc.nextLine();
            String mensajeLugar;
            if (option.equalsIgnoreCase("s")) {
                mensajeLugar = "cantidad de asientos";
                tieneAsientos = true;
            } else {
                mensajeLugar = "capacidad";
            }
            cantidadAsientos = Validacion.validarEntero(sc, ("Ingrese la " + mensajeLugar + " del sector " + (i + 1)) + "(1-200): ", 1, 200);
            ArrayList<Asiento> asientos = new ArrayList<>();
            for (int j = 0; j < cantidadAsientos; j++) {
                asientos.add(new Asiento(j));
            }
            sectores.add(new Sector(nombre, extra, tieneAsientos, asientos));
        }
        return sectores;
    }

    public void crearOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        String nombre, email, contrasenia;
        boolean flagEmail = false;
        boolean flagContrasenia = false;
        System.out.println("Ingrese nombre de organizador");
        nombre = sc.nextLine();
        do {
            System.out.println("Ingrese email");
            email = sc.nextLine();
            try {
                flagEmail = Validacion.validarEmail(email);

            } catch (EmailInvalidoException e) {
                System.out.println(e.getMessage());
            }
        } while (!flagEmail);
        do {
            System.out.println("ingrese contrasenia (Debe tener al menos 8 caracteres, una letra y un numero)");
            contrasenia = sc.nextLine();
            try {
                flagContrasenia = Validacion.validarContrasena(contrasenia);
            } catch (ContraseniaInvalidaException e) {
                System.out.println(e.getMessage());
            }

        } while (!flagContrasenia);
        for (Usuario u : boleteria.getUsuarios().getElementos()) {
            if (email.equals(u.getEmail())) {
                flagEmail = false;
                System.out.println("El email ingresado ya esta en uso");
            }
        }
        if (flagEmail) {
            boleteria.guardarUsuario(new Organizador(nombre, email, contrasenia), archivo);
            System.out.println("La cuenta se creo correctamente");
        }
    }
/*
    /// Probar modificacion
    public void modificarOrganizador(Scanner sc, Boleteria boleteria, String archivo) {
        int organizadorId;
        Organizador organizador;

        System.out.println("Organizadores:\n" + boleteria.mostrarOrganizadores());
        organizadorId = Validacion.validarEntero(sc, "Ingrese id del usuario a modificar");
        if (boleteria.getUsuarios().buscarElementoId(organizadorId) instanceof Organizador) {
            organizador = (Organizador) boleteria.getUsuarios().buscarElementoId(organizadorId);

            String nombre, email, contrasenia;
            boolean flagEmail = false;
            boolean flagContrasenia = false;
            System.out.println("Ingrese nombre de organizador");
            nombre = sc.nextLine();
            do {
                System.out.println("Ingrese email");
                email = sc.nextLine();
                try {
                    flagEmail = Validacion.validarEmail(email);

                } catch (EmailInvalidoException e) {
                    System.out.println(e.getMessage());
                }
            } while (!flagEmail);
            do {
                System.out.println("ingrese contrasenia");
                contrasenia = sc.nextLine();
                try {
                    flagContrasenia = Validacion.validarContrasena(contrasenia);
                } catch (ContraseniaInvalidaException e) {
                    System.out.println(e.getMessage());
                }

            } while (!flagContrasenia);
            organizador.setNombre(nombre);
            organizador.setContrasenia(contrasenia);
            organizador.setEmail(email);
            boleteria.guardarBoleteria(archivo);
        } else System.out.println("El elemento seleccionado no es un organizador");

    }
    */

    public void verMisEventos(Organizador o) {
        StringBuilder sb = new StringBuilder();
        sb.append("/////////////////////// Eventos Creados ///////////////////////\n");
        sb.append("Total de eventos: ").append(o.getEventosCreados().size());
        for (Evento e : o.getEventosCreados()) {
            sb.append("\nId:").append(e.getId()).append("\tNombre: ").append(e.getNombre())
                    .append("\tFunciones disponibles: ").append(e.getFunciones().size());
        }
        sb.append("\n/////////////////////// Fin eventos creados ///////////////////////");
        System.out.println(sb);
    }

    public void verMisFunciones(Scanner sc, Organizador o) {
        StringBuilder sb = new StringBuilder();
        verMisEventos(o);
        boolean flag = false;
        String eventoId;
        Evento evento = null;
        do {
            System.out.println("Ingrese el id del evento o \"S\" para salir");
            eventoId = sc.nextLine();
            if (!eventoId.equalsIgnoreCase("s")) {
                for (Evento e : o.getEventosCreados()) {
                    if (("" + e.getId()).equalsIgnoreCase(eventoId)) {
                        evento = e;
                        flag = true;
                    }
                }
            }else {
                System.out.println("...Saliendo...");
            }
            if (!flag && !eventoId.equalsIgnoreCase("s")) System.out.println("El numero ingresado es invalido, intentelo nuevamente");
        } while (!flag && !eventoId.equalsIgnoreCase("s"));
        sb.setLength(0);
        if(!eventoId.equalsIgnoreCase("s")){
            sb.append("/////////////////////// Funciones ///////////////////////\n");
            for (Funcion f : evento.getFunciones()) {
                sb.append("id: ").append(f.getId()).append("\tFecha: ").append(UtilidadesGenerales.formatearFecha(f.getFechayHora())).append("\tRecinto: ").append(f.getRecinto().getNombre()).append("\n");

            }
            sb.append("\n/////////////////////// Eventos Creados ///////////////////////");
            System.out.println(sb);
        }
    }
}
