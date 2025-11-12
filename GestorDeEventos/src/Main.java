import model.Organizador;
import service.OrganizadorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Organizador organizador=new Organizador();
        OrganizadorService organizadorService=new OrganizadorService();
        Scanner sc=new Scanner(System.in);
        organizadorService.nuevoEvento(sc,organizador);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst());
        System.out.println(organizador.getEventosCreados().getFirst());
    }
}