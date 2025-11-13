package model;

import java.util.ArrayList;

public class Lista<T> {
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
