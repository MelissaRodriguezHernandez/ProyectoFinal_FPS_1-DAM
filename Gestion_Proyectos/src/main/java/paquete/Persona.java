package paquete;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Persona {
    /*Atributos*/
    private String id;
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;
   

    /*Constructores*/
    
    public Persona(String id, String nombre, String apellidos, String email, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena = contrasena;
    }


    /*Otros metodos*/
    
    @Override
    public String toString() {
        return "Persona{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", contraseña='" + contrasena + '\'' +
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    /**
     * Cuando el usuario haga el formulario de registro pasara la contraseña que
     * sera el parametro de nuestro metodo. El texto en cuestion se encriptara y
     * se enviara a la base de datos
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Se asigna aleatoriamente un identificador al proyecto que se pasa por
     * parametro. hay que tener en cuenta que no se repita con alguno existente
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static String asignarId() throws ClassNotFoundException, SQLException {
        int min = 0;
        int max = 9999;
        ArrayList<Persona> listaPersonas = BBDD.recuperarPersonas();
        String id = String.valueOf((int) Math.floor(Math.random() * (max - min + 1) + min));

        int i = 0;

        while(i<listaPersonas.size()) {
            if(listaPersonas.get(i).getId().equals(id)) {
                i = 0;
                id = String.valueOf((int) Math.floor(Math.random() * (max - min + 1) + min));
            }else {
                i++;
            }
        }
        return id;
    }


    /**
     *El usuario al iniciar sesion introducira el email y la contraseña que seran los dos primeros parametros que le pasaremos al metodo
     * Con un bucle comprobaremos si el email existe en el sistema
     * El metodo retorna un valor de tipo int dependiendo de cada caso.
     * En el caso que el usario alla introducido el email y la contraseña correctamente,
     * cojeremos el id de este usuario y lo introduciremos al atributo estatico usuarioWeb, el cual
     * usaremos en la página web para mostrar todo el contenido de la cuenta del usuario
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static int comprobarLogin(String contrasena, String email) throws ClassNotFoundException, SQLException{
        ArrayList <Persona> listaPersonas = BBDD.recuperarPersonas();
        boolean existe = false;
        boolean coinciden = false;
        int valor = 0;

        int i = 0;
        while(!coinciden && i<listaPersonas.size()) {
            if(email.equals(listaPersonas.get(i).getEmail())) {
                existe = true;
                if(contrasena.equals(listaPersonas.get(i).getContrasena())) {
                    BBDD.usuarioWeb = listaPersonas.get(i).getId();
                    coinciden = true;
                }
            }
            i++;
        }
        if(!existe) {
            valor = 1;
        }

        if(existe && !coinciden) {
            valor = 2;
        }

        if(existe && coinciden) {

            valor = 3;

        }

        return valor;
    }

   
    
}
