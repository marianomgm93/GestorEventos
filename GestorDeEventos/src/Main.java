import model.*;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String archivo="GestorDeEventos\\src\\boleteria.json";
        Boleteria boleteria = new Boleteria();
        OrganizadorService organizadorService=new OrganizadorService();
        Scanner sc=new Scanner(System.in);

        organizadorService.crearOrganizador(sc,boleteria,archivo);
        Organizador organizador=(Organizador) boleteria.getUsuarios().getElementos().getFirst();
        organizadorService.nuevoEvento(sc,organizador,boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst(),boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());


        Vendedor vendedor=new Vendedor("juan","juan@gmail.com","123456");
        VendedorService vendedorService=new VendedorService();
        vendedorService.nuevoTicket(sc,vendedor,boleteria.getEventos().getElementos(),boleteria,archivo);
        System.out.println(vendedor);


        boleteria.fromJSON(archivo);
        System.out.println(boleteria);


    }
}