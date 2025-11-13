package model;

import org.json.JSONObject;

public class Asiento {
    private static int totalAsientos;
    private int numero;
    private boolean disponible;

    public Asiento() {
        this.numero = totalAsientos++;
        this.disponible = true;
    }

    public Asiento(int numero) {
        this.numero = numero;
        totalAsientos++;
        this.disponible = true;
    }

    public Asiento(JSONObject o) {
        this.numero = o.getInt("numero");
        totalAsientos++;
        this.disponible = o.getBoolean("disponible");
    }
    public JSONObject toJSON(){
        JSONObject o = new JSONObject();
        o.put("numero", this.numero);
        o.put("disponible",this.disponible);
        return o;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Asiento{");
        sb.append("numero=").append(numero);
        sb.append(", disponible=").append(disponible);
        sb.append('}');
        return sb.toString();
    }
}
