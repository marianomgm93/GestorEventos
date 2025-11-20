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

    /**
     * Constructor para crear un Usuario con sus datos esenciales. Asigna un ID automático.
     *
     * @param nombre El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param contrasenia La contraseña del usuario.
     */
    public Usuario(String nombre, String email, String contrasenia) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.id=totalUsuarios++;
        activo=true;
    }

    /**
     * Constructor para crear un Usuario con todos sus atributos, incluyendo un ID predefinido.
     *
     * @param id El identificador único del usuario.
     * @param nombre El nombre del usuario.
     * @param email El correo electrónico del usuario.
     * @param contrasenia La contraseña del usuario.
     */
    public Usuario(int id, String nombre, String email, String contrasenia, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        totalUsuarios++;
        this.activo=activo;
    }

    /**
     * Obtiene el número total de usuarios que han sido instanciados.
     *
     * @return El contador estático de usuarios.
     */
    public static int getTotalUsuarios() {
        return totalUsuarios;
    }

    /**
     * Obtiene el identificador único del usuario.
     *
     * @return El ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Implementación necesaria para la interfaz ID.
     * Establece el identificador único del usuario.
     *
     * @param id El nuevo ID para el usuario.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece un nuevo nombre para el usuario.
     *
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece un nuevo correo electrónico para el usuario.
     *
     * @param email El nuevo correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña.
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     * Establece una nueva contraseña para el usuario.
     *
     * @param contrasenia La nueva contraseña.
     */
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
        return id == usuario.id && Objects.equals(nombre, usuario.nombre) && Objects.equals(email, usuario.email) && Objects.equals(contrasenia, usuario.contrasenia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, email, contrasenia);
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