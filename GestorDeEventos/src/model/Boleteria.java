package model;

import org.json.JSONArray;
import org.json.JSONObject;


public class Boleteria {
    private Lista<Usuario> usuarios;
    private Lista<Evento> eventos;

    public Boleteria(Lista<Usuario> usuarios, Lista<Evento> eventos) {
        this.usuarios = usuarios;
        this.eventos = eventos;
    }
    public Boleteria(JSONObject o){
        this.usuarios= new Lista<>();
        this.eventos=new Lista<>();
        JSONArray jUsuarios=o.getJSONArray("usuarios");
        JSONArray jEventos=o.getJSONArray("eventos");
        for (int i = 0; i <jUsuarios.length() ; i++) {
            if (jUsuarios.getJSONObject(i).getJSONArray("ticketsVendidos")==null){
                this.usuarios.add(new Organizador(jUsuarios.getJSONObject(i)));
            }else{
                this.usuarios.add(new Vendedor(jUsuarios.getJSONObject(i)));
            }
        }
        for (int i = 0; i < jEventos.length(); i++) {
            this.eventos.add(new Evento(jEventos.getJSONObject(i)));
        }

    }

}
