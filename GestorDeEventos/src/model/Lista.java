package model;

import java.util.ArrayList;

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

        for (T elemento : elementos) {
            if (elemento.getId() == id) {

                return elemento;
            }
        }
        return null;
    }

    public String ModificarElemento(int id, T actualizado) {
        actualizado.setId(id);
        for (T elemento : elementos) {
            if (elemento.getId() == id) {
                elementos.set(elementos.indexOf(elemento), actualizado);

                return "Se modifico con exito";
            }
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
