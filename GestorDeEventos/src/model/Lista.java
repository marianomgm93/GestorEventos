package model;

import exceptions.ElementoNoEncontradoException;
import exceptions.NumeroInvalidoException;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Lista<T extends ID> {
    private ArrayList<T> elementos = new ArrayList<>();


    public String add(T objeto) {
        elementos.add(objeto);
        return "Objeto agregado";
    }

    public String remove(T objeto) {

        for (T elemento : elementos) {
            if (elemento == objeto) {
                elementos.remove(objeto);
                return "Se elimino el objeto";

            }
        }
        return "No se encontro el objeto";
    }


    public T BuscarElementoId(int id) {

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


    public ArrayList<T> getElementos() {
        return elementos;
    }

    public void setElementos(ArrayList<T> elementos) {
        this.elementos = elementos;
    }

    @Override
    public String toString() {
        return "Lista{" +
                "elementos=" + elementos +
                '}';
    }
}
