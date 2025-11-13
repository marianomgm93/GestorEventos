package model;

import org.json.JSONObject;

public class Funcion {
    private static int totalFunciones;
    private int id;
    private String hora;
    private Recinto recinto;
    private double precioBase;

    public Funcion(String hora, Recinto recinto, double precioBase) {
        this.id = totalFunciones++;
        this.hora = hora;
        this.recinto = recinto;
        this.precioBase = precioBase;
    }

    public Funcion(JSONObject o){
        this.id=o.getInt("id");
        totalFunciones++;
        this.hora=o.getString("hora");
        this.recinto=new Recinto(o.getJSONObject("recinto"));
        this.precioBase=o.getDouble("precioBase");
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gethora() {
        return hora;
    }

    public void sethora(String hora) {
        this.hora = hora;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(int precioBase) {
        this.precioBase = precioBase;
    }

    public String asientosDisponibles(){
        StringBuilder sb=new StringBuilder();
        for (Sector s: this.recinto.getSectores()){
            for(Asiento a: s.getAsientos()){
                sb.append("Sector: ").append(s.getId()).append(" ").append(s.getNombre());
                sb.append("Tipo: ").append(s.getTipo()).append("\n");
                if(a.isDisponible()){
                    sb.append(a);
                }
            }
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Funcion{");
        sb.append("id=").append(id);
        sb.append(", hora='").append(hora).append('\'');
        sb.append(", recinto=").append(recinto);
        sb.append(", precioBase=").append(precioBase);
        sb.append('}');
        return sb.toString();
    }
}
