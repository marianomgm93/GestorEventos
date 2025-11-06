package model;

import java.util.ArrayList;
import java.util.Objects;

public class Organizador extends Usuario{
    private ArrayList <Evento> eventosCreados;

    public Organizador() {
    }

    public Organizador(String nombre, String email, String contrasenia) {
        super(nombre, email, contrasenia);
        this.eventosCreados = new ArrayList<>();
    }

    public ArrayList<Evento> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(ArrayList<Evento> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Organizador{");
        sb.append("eventosCreados=").append(eventosCreados);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Organizador that)) return false;
        return Objects.equals(eventosCreados, that.eventosCreados);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventosCreados);
    }

}
