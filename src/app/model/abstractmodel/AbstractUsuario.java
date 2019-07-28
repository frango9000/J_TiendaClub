package app.model.abstractmodel;

public class AbstractUsuario {

    private byte id;
    private String user;
    private String pass;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String descripcion;
    private byte idAcceso;

    public AbstractUsuario(byte id, String user, String pass, String nombre, String telefono, String email, String direccion, String descripcion, byte idAcceso) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.idAcceso = idAcceso;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(byte idAcceso) {
        this.idAcceso = idAcceso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractUsuario usuario = (AbstractUsuario) o;

        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", pass='" + pass + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", idAcceso=" + idAcceso +
                '}';
    }
}