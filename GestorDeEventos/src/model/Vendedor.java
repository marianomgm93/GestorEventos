package model;

import java.util.ArrayList;

public class Vendedor extends Usuario{
    private ArrayList<Ticket> ticketsVendidos;

    public Vendedor(String nombre, String email, String contrasenia) {
        super(nombre, email, contrasenia);
        this.ticketsVendidos=new ArrayList<>();
    }

    public ArrayList<Ticket> getTicketsVendidos() {
        return ticketsVendidos;
    }


}
