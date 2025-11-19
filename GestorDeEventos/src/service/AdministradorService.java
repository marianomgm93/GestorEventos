package service;

import model.Boleteria;
import model.Usuario;

public class AdministradorService {
    public String verUsuarios(Boleteria boleteria){
        StringBuilder sb=new StringBuilder();
        for(Usuario u : boleteria.getUsuarios().getElementos()){
            sb.append("ID: ").append(u.getId()).append("\tNombre: ").append(u.getNombre())
                    .append("\tEmail: ").append(u.getEmail()).append("\tTipo: ").append(u.getClass().getSimpleName()).append("\n");
        }
        return sb.toString();
    }
}
