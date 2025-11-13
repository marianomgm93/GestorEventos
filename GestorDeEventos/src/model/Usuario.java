package model;

public abstract class Usuario implements ID {
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

    public Usuario(int id, String nombre, String email, String contrasenia) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        totalUsuarios++;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("id=").append(id);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", contrasenia='").append(contrasenia).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
