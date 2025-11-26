import model.*;
import service.Menu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String archivo = "GestorDeEventos/src/boleteria.json";
        Boleteria boleteria = new Boleteria();
        Scanner sc = new Scanner(System.in);
        Menu menu = new Menu();

        boolean datosCargados = false;

        // 1. Intentar cargar desde JSON
        try {
            boleteria.fromJSON(archivo);
            System.out.println("Datos cargados correctamente");
            datosCargados = true;
        } catch (Exception e) {
            System.out.println("No se encontró el archivo JSON o hubo un error al cargar.");
            System.out.println("Se iniciará con datos por defecto...");
        }

        // 2. Si no se cargó o está vacío crear usuarios base
        if (!datosCargados || boleteria.getUsuarios().getElementos().isEmpty()) {
            System.out.println("Creando usuarios predeterminados...");

            Administrador admin = new Administrador(0, "admin", "admin@boleteria.com", "admin");
            Organizador organizador = new Organizador(1, "organizador", "organizador@boleteria.com", "organizador", true);
            Vendedor vendedor = new Vendedor(2, "vendedor", "vendedor@boleteria.com", "vendedor", true);

            boleteria.getUsuarios().add(admin);
            boleteria.getUsuarios().add(organizador);
            boleteria.getUsuarios().add(vendedor);
            boleteria.actualizarContadores();
            boleteria.guardarBoleteria(archivo);

            System.out.println("Usuarios predeterminados creados y guardados correctamente.");
            System.out.println("""
                    
                    CREDENCIALES DE ACCESO (para pruebas):
                    ──────────────────────────────────────────────
                    → Administrador : admin@boleteria.com     | contraseña: admin
                    → Organizador   : organizador@boleteria.com | contraseña: organizador
                    → Vendedor      : vendedor@boleteria.com   | contraseña: vendedor
                    ──────────────────────────────────────────────
                    """);
        } else {
            // Si se cargó desde JSON  también sincronizar contadores por seguridad
            boleteria.actualizarContadores();
        }

        System.out.println("""
            ═══════════════════════════════════════════════════════════
                         SISTEMA DE BOLETERÍA INICIADO CORRECTAMENTE
            ═══════════════════════════════════════════════════════════
            """);
        // Iniciar menú
        menu.inicio(sc, boleteria, archivo);

        // Cerrar recursos
        sc.close();
        System.out.println("\nSistema finalizado. ¡Gracias por usar Boletería!");
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
problema usuarios con mismo id--+
Guardar recintos y poder seleccionarlos--?
cambiar formato de la fecha en ver funciones menu organizador
Validar que el nuevo ticket no permita elegir eventos sin funciones disponibles
//todo revisar errror al crear tickets no aparecen las funciones y crashea
 */