import model.*;
import service.Menu;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String archivo="GestorDeEventos\\src\\boleteria.json";
        Boleteria boleteria = new Boleteria();
       /* OrganizadorService organizadorService=new OrganizadorService();*/
        Scanner sc=new Scanner(System.in);
        Menu menu=new Menu();
/*
        organizadorService.crearOrganizador(sc,boleteria,archivo);
        Organizador organizador=(Organizador) boleteria.getUsuarios().getElementos().getFirst();
        organizadorService.nuevoEvento(sc,organizador,boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst(),boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());


        VendedorService vendedorService=new VendedorService();
        vendedorService.crearVendedor(sc,boleteria,archivo);
        Vendedor vendedor= (Vendedor) boleteria.getUsuarios().buscarElementoId(1);
        vendedorService.nuevoTicket(sc,vendedor,boleteria,archivo);
        System.out.println(vendedor);
*/

        boleteria.fromJSON(archivo);
        System.out.println(boleteria);
        while(true) {
            menu.lanzarMenu(sc,boleteria,archivo);
        }
    }
}