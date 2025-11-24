package service;

import Utilidades.Validacion;
import model.Administrador;
import model.Boleteria;
import model.Usuario;

import java.util.Scanner;

public class AdministradorService {
    public String verUsuarios(Boleteria boleteria){
        StringBuilder sb=new StringBuilder();
        for(Usuario u : boleteria.getUsuarios().getElementos()){
            sb.append("ID: ").append(u.getId()).append("\tNombre: ").append(u.getNombre())
                    .append("\tEmail: ").append(u.getEmail()).append("\tTipo: ").append(u.getClass().getSimpleName())
                    .append("\tActivo: ").append((u.isActivo()) ? "Si" : "No").append("\n");
        }
        return sb.toString();
    }
    public void eliminarUsuario(Scanner sc, Boleteria boleteria,String archivo){
        Usuario usuario;
        verUsuarios(boleteria);
        verUsuariosActivos(boleteria);
        System.out.println("Ingrese id del usuario a bloquear");
        int idUsuario= Validacion.validarEntero(sc);
        usuario=boleteria.getUsuarios().buscarElementoId(idUsuario);
        if(usuario!=null && !(usuario instanceof Administrador)){
            usuario.setActivo(false);
            boleteria.guardarBoleteria(archivo);
            System.out.println("El usuario se ha bloqueado con exito");
        }else{
            System.out.println("El usuario no pudo bloquearse");
        }
    }
    public void deseliminarUsuario(Scanner sc, Boleteria boleteria,String archivo){
        Usuario usuario;
        verUsuarios(boleteria);
        verUsuariosInactivos(boleteria);
        System.out.println("Ingrese id del usuario a desbloquear");
        int idUsuario= Validacion.validarEntero(sc);
        usuario=boleteria.getUsuarios().buscarElementoId(idUsuario);
        if(usuario!=null && !(usuario instanceof Administrador)){
            usuario.setActivo(true);
            boleteria.guardarBoleteria(archivo);
            System.out.println("El usuario se ha desbloqueado con exito");
        }else{
            System.out.println("El usuario no pudo desbloquear");
        }
    }
    public void verUsuariosActivos(Boleteria boleteria) {
        System.out.println(boleteria.mostrarUsuariosActivos());
    }
    public void verUsuariosInactivos(Boleteria boleteria){
        System.out.println(boleteria.mostrarUsuariosInactivos());
    }
}
