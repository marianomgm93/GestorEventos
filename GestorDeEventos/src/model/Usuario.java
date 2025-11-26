/**
 * Clase abstracta base que representa un usuario del sistema.
 * Contiene información de identificación, nombre y credenciales.
 * Implementa la interfaz {@code ID} para manejar identificadores únicos.
 */
package model;

import java.util.Objects;

public abstract class Usuario implements ID {

    private static int totalUsuarios;
    private int id;
    private String nombre;
    private String email;
    private String contrasenia;
    private boolean activo;

    public Usuario() {
        activo=true;
        this.id=totalUsuarios++;
    }


    public Usuario(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.id=totalUsuarios++;
        activo=true;
    }

    public Usuario(int id, String nombre, String email, String contrasenia, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.activo=activo;
    }


    public static int getTotalUsuarios() {
        return totalUsuarios;
    }

    public int getId() {
        return id;
    }

    public static void setTotalUsuarios(int totalUsuarios) {
        Usuario.totalUsuarios = totalUsuarios;
    }


    @Override
    public void setId(int id) {
        this.id = id;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    /**
     * Devuelve una representación en cadena del objeto Usuario,
     * mostrando su ID, nombre, email y contraseña.
     *
     * @return Una cadena que representa el estado del Usuario.
     */
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