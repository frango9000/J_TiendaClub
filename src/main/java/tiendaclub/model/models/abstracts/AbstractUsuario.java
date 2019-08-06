package tiendaclub.model.models.abstracts;

public abstract class AbstractUsuario extends Persistible {

    protected String username;
    protected String pass;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;
    protected int idAcceso;

    public AbstractUsuario(int id, String username, String pass, String nombre, int idAcceso) {
        this.id = id;
        this.username = username;
        this.pass = pass;
        this.nombre = nombre;
        this.idAcceso = idAcceso;
    }

    public AbstractUsuario(String username, String pass, String nombre, int idAcceso) {
        this.username = username;
        this.pass = pass;
        this.nombre = nombre;
        this.idAcceso = idAcceso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(int idAcceso) {
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
                ", user='" + username + '\'' +
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
