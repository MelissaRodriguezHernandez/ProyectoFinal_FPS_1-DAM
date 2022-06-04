package paquete;

import java.util.ArrayList;
import java.util.Date;

public class Proyecto {
    /*Atributos*/
    private String id;
    private String nombre;
    private String descripcion;
    private Tipo tipo;
    private String idDirector;
    private Estado estado;
    private Date fechaI;
    private Date fechaF;

    /*Constructores*/

    public Proyecto(String id, String nombre, String descripcion, Tipo tipo, String idDirector, Estado estado, Date fechaI, Date fechaF) {
        this.id = new String();
        this.nombre = new String();
        this.descripcion = new String();
        this.idDirector = new String();
        this.fechaI = new Date();
        this.fechaF = new Date();

        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setTipo(tipo);
        this.setIdDirector(idDirector);
        this.setEstado(estado);
        this.setFechaI(fechaI);
        this.setFechaF(fechaF);
    }
    
    public Proyecto(String id, String nombre, String descripcion, String idDirector) {
        this.id = new String();
        this.nombre = new String();
        this.descripcion = new String();
        this.idDirector = new String();
    

        this.setId(id);
        this.setNombre(nombre);
        this.setDescripcion(descripcion);
        this.setTipo(tipo);
        this.setIdDirector(idDirector);
        this.setEstado(estado);
    
    }
    /*toString*/
    @Override
    public String toString() {
        return "Proyecto{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tipo=" + tipo +
                ", idDirector='" + idDirector + '\'' +
                ", estado=" + estado +
                ", fechaI=" + fechaI +
                ", fechaF=" + fechaF +
                '}';
    }

    /*Getters y Setters*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(String idDirector) {
        this.idDirector = idDirector;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    /*Otros metodos*/

   
    /**
     *Se asigna aleatoriamente un identificador al proyecto.
     * hay que tener en cuenta que no se repita con alguno existente
     * @throws ClassNotFoundException
     */
    public static String asignarId() throws ClassNotFoundException{
        int min = 0;
        int max = 9999;
        ArrayList<Proyecto> listaProyectos = BBDD.recuperarProyectos();

        String id = String.valueOf((int) Math.floor(Math.random() * (max - min + 1) + min));

        int i = 0;

        while(i<listaProyectos.size()) {
            if(listaProyectos.get(i).getId().equals(id)) {
                i = 0;
                id = String.valueOf((int) Math.floor(Math.random() * (max - min + 1) + min));
            }else {
                i++;
            }
        }
        return id;
    }
    

   
}
