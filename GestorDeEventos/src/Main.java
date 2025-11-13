import model.*;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Boleteria boleteria = new Boleteria();
        Organizador organizador=new Organizador();
        OrganizadorService organizadorService=new OrganizadorService();
        Scanner sc=new Scanner(System.in);
        boleteria.nuevoEvento(sc,organizador);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst());
        System.out.println(organizador.getEventosCreados().getFirst());

        Vendedor vendedor=new Vendedor("juan","juan@gmail.com","123456");
        VendedorService vendedorService=new VendedorService();
        vendedorService.nuevoTicket(sc,vendedor,boleteria.getEventos().getElementos());
        System.out.println(vendedor);


        /* TEST generica
        Lista<Evento> eventoLista = new Lista<>();
        eventoLista.add(new Evento("tal", "tal", Categoria.CINE));
        eventoLista.add(new Evento("tal", "tal", Categoria.CINE));
        eventoLista.ModificarElemento(0, new Evento("tol", "tol", Categoria.PARTIDO));
        System.out.println(eventoLista.getElementos().toString());
        System.out.println(eventoLista.BuscarElementoId(0));
   */
    }
}