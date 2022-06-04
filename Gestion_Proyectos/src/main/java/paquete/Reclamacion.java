package paquete;

import java.sql.SQLException;
import java.util.ArrayList;

public class Reclamacion {
    /*Atributos*/
    private String id;
    private String idProyecto;
    private String nombre;
    private String decripcion;
    private String idUsuario;
    private String estado;

    /*Constructores*/

    public Reclamacion(String id, String idProyecto, String nombre, String decripcion, String idUsuario, String estado) {
        this.id = id;
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.decripcion = decripcion;
        this.idUsuario = idUsuario;
        this.estado = estado;
    }
    
    /*toString*/

    @Override
    public String toString() {
        return "Reclamacion{" +
                "id='" + id + '\'' +
                ", idProyecto='" + idProyecto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", decripcion='" + decripcion + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
    /*Geters y Setters*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(String idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
	

}
