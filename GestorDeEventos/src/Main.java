import model.*;
import service.Menu;
import service.OrganizadorService;
import service.VendedorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String archivo="GestorDeEventos\\src\\boleteria.json";
        Boleteria boleteria = new Boleteria();
     //   OrganizadorService organizadorService=new OrganizadorService();
        Scanner sc=new Scanner(System.in);
        Menu menu=new Menu();
/*
///        organizadorService.crearOrganizador(sc,boleteria,archivo);
        Organizador organizador=(Organizador) boleteria.getUsuarios().getElementos().getFirst();
        organizadorService.nuevoEvento(sc,organizador,boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());
        organizadorService.agregarFuncion(sc,organizador.getEventosCreados().getFirst(),boleteria,archivo);
        System.out.println(organizador.getEventosCreados().getFirst());
*/
/*
        VendedorService vendedorService=new VendedorService();
        vendedorService.crearVendedor(sc,boleteria,archivo);
        Vendedor vendedor= (Vendedor) boleteria.getUsuarios().buscarElementoId(1);
        vendedorService.nuevoTicket(sc,vendedor,boleteria,archivo);
        System.out.println(vendedor);
*/
/// ULTIMOS CAMBIOS: CREADA CLASE ADMINISTRADOR, SERVICIO, Y METODO VER USUARIOS

        try{
            boleteria.fromJSON(archivo);
            System.out.println("Los datos se cargaron correctamente");

        }catch (Exception e){
            System.out.println("La carga de datos falló");
        }
/*

        Administrador adm=new Administrador(0,"admin","admin","admin");
        boleteria.getUsuarios().getElementos().add(adm);
*/
        while(true) {
            menu.inicio(sc,boleteria,archivo);
        }
    }
}
/*
Cambiar tipo para que al generar tickets, si es campo no tenga lugar de asiento(QUE SEA VISIBLE)
contructor en ticket para sector sin asiento donde asiento=NULL
VERIFICAR LOS TIPOS DE SECTOR AGREGAR LA MODIFICACION DE PRECIO POR OTRO LADO
Login corrupto
Printeos con mas info en vender tickets
comprar dos veces mismo asiento
sleccionar sector antes de mostrar asientos
al ver usuarios mostrar activo embellecido
fixear ver usuarios inactivos
validar recintos en fechas(que no se superpongan)
 */