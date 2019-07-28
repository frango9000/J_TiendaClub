package app.model.models.abstracts;

public abstract class AbstractUsuario {

    protected byte id;
    protected String user;
    protected String pass;
    protected String nombre;
    protected String telefono;
    protected String email;
    protected String direccion;
    protected String descripcion;
    protected byte idAcceso;

    public AbstractUsuario(byte id, String user, String pass, String nombre, byte idAcceso) {
        this.id = id;
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.idAcceso = idAcceso;
    }

    public AbstractUsuario(String user, String pass, String nombre, byte idAcceso) {
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.idAcceso = idAcceso;
    }

    public int getId() {
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
