import model.Boleteria;
import model.Evento;
import model.Organizador;
import model.Vendedor;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Boleteria boleteria=new Boleteria();
        Organizador organizador=new Organizador();
        OrganizadorService organizadorService=new OrganizadorService();
        Scanner sc=new Scanner(System.in);
        boleteria.nuevoEvento(sc,organizador);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst());
        System.out.println(organizador.getEventosCreados().getFirst());

        Vendedor vendedor=new Vendedor("juan","juan@gmail.com","123456");
        VendedorService vendedorService=new VendedorService();
        vendedorService.nuevoTicket(sc,vendedor,boleteria.getEventos());
        System.out.println(vendedor);
    }
}