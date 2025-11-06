package model;

public abstract class Usuario {
    private static int totalUsuarios;
    private int id;
    private String nombre;
    private String email;
    private String contrasenia;

    public Usuario() {
        this.id=totalUsuarios++;
    }

    public Usuario(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.id=totalUsuarios++;
    }

    public static int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
