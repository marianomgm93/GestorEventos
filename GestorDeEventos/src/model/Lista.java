/**
 * Implementación de una lista genérica que almacena elementos que deben
 * implementar la interfaz {@code ID}.
 * <p>
 * Esta clase proporciona métodos básicos para agregar, buscar, modificar y
 * eliminar elementos, manejando excepciones específicas del dominio.
 *
 * @param <T> El tipo de elementos que contendrá la lista, debe extender de {@code ID}.
 */
package model;

import Utilidades.Validacion;
import exceptions.ElementoNoEncontradoException;
import exceptions.EventoRepetidoException;
import exceptions.NumeroInvalidoException;
import exceptions.UsuarioRepetidoException;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Lista<T extends ID> {

    /**
     * La colección interna de elementos de tipo T.
     */
    private ArrayList<T> elementos = new ArrayList<>();


    /**
     * Agrega un objeto a la lista, verificando previamente que no se encuentre
     * repetido en la colección.
     *
     * @param objeto El objeto de tipo T (que implementa ID) a agregar.
     * @return {@code true} si el objeto fue agregado exitosamente.
     * @throws EventoRepetidoException Si el objeto es una instancia de {@code Evento} y ya existe en la lista.
     * @throws UsuarioRepetidoException Si el objeto es una instancia de {@code Usuario} y ya existe en la lista.
     */
    public boolean add(T objeto) throws  EventoRepetidoException, UsuarioRepetidoException{
        if(Validacion.repetido(objeto, this.elementos)){
            if(objeto instanceof Evento) throw new EventoRepetidoException("El evento ingresado ya se encuentra cargado en el sistema");
            else throw new UsuarioRepetidoException("El usuario ingresado ya se encuentra cargado en el sistema");
        }else{
            elementos.add(objeto);
        }
        return true;
    }

    /**
     * Elimina una instancia específica del objeto de la lista.
     *
     * @param objeto El objeto de tipo T a eliminar.
     * @return Un mensaje indicando si el objeto fue eliminado o si no fue encontrado.
     */
    public String remove(T objeto) {

        for (T elemento : elementos) {
            if (elemento == objeto) {
                elementos.remove(objeto);
                return "Se elimino el objeto";

            }
        }
        return "No se encontro el objeto";
    }


    /**
     * Busca y devuelve un elemento de la lista utilizando su ID único.
     * Si no encuentra el elemento, lanza {@link ElementoNoEncontradoException}
     * que es capturada e imprime un mensaje de error.
     *
     * @param id El identificador único del elemento a buscar.
     * @return El elemento de tipo T encontrado con ese ID, o {@code null} si no se encuentra.
     */
    public T buscarElementoId(int id) {

        try {
            for (T elemento : elementos) {
                if (elemento.getId() == id) {
                    return elemento;
                }
            }

            throw new ElementoNoEncontradoException("No se encontró el elemento con id: " + id);
        } catch (ElementoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Modifica un elemento existente en la lista buscando por su ID y
     * reemplazándolo con la nueva instancia. El ID del objeto actualizado
     * se restablece al ID original.
     *
     * @param id El identificador del elemento a modificar.
     * @param actualizado El nuevo objeto de tipo T que reemplazará al elemento existente.
     * @return Un mensaje indicando si la modificación fue exitosa o si el elemento no fue encontrado.
     */
    public String ModificarElemento(int id, T actualizado) {

        try {
            for (T elemento : elementos) {
                if (elemento.getId() == id) {
                    elementos.set(elementos.indexOf(elemento), actualizado);
                    elementos.get(elementos.indexOf(actualizado)).setId(id);
                    return "Se modifico con exito";
                }
            }
            throw new ElementoNoEncontradoException("No se encontró el elemento con id: " + id);
        } catch (ElementoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
        return "No se encontro el evento";
    }


    /**
     * Obtiene la lista interna de elementos.
     *
     * @return El {@code ArrayList} de elementos.
     */
    public ArrayList<T> getElementos() {
        return elementos;
    }

    /**
     * Establece una nueva lista de elementos para reemplazar la actual.
     *
     * @param elementos El nuevo {@code ArrayList} de elementos.
     */
    public void setElementos(ArrayList<T> elementos) {
        this.elementos = elementos;
    }

    /**
     * Devuelve una representación en cadena del objeto Lista, mostrando
     * la lista de sus elementos.
     *
     * @return Una cadena que representa el estado del objeto Lista.
     */
    @Override
    public String toString() {
        return "Lista{" +
                "elementos=" + elementos +
                '}';
    }
}