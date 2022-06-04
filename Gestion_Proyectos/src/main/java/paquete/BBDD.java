package paquete;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class BBDD {
	 static String usuarioWeb;
	 static String idProyectoActual;
	
	 /**
	     * Pasamos por parametro la información que ha introducido el usuario en el formulario de registro y
	     * ejecutamos un query en nuestra base de datos en la tabla de usuarios para insertar toda esta información
	     * @param id
	     * @param nombre
	     * @param apellidos
	     * @param email
	     * @param contrasena
	     * @return existe
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	    public static boolean insertUsuario(String id, String nombre, String apellidos, String email, String contrasena ) throws SQLException, ClassNotFoundException {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO usuario (id, nombre, apellidos, email, contraseña) VALUES ('"+id+"','"+nombre+"','"+apellidos+"','"+email+"','"+contrasena+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();

	        ArrayList<Persona> listaPersonas = recuperarPersonas();

	        int i = 0;
	        boolean existe = false;

	        while(!existe && i<listaPersonas.size()) {
	            if(email.equals(listaPersonas.get(i).getEmail())) {
	                existe = true;
	            }else {
	                i++;
	            }
	        }

	        if(!existe) {
	            st.executeUpdate(query);
	        }

	        return existe;
	    }

	    /**
	     * Metodo que selecciona todas las filas de la tabla usuario de la base de datos, fila por fila
	     * va creando un nuevo objeto persona con la información y lo mete en un arraylist de personas.
	     * Resumidamente metemos en un arraylist todas los usuarios de la base de datos para usarla posteriormente en otros metodos
	     * @return listaPersonas
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static ArrayList<Persona> recuperarPersonas() throws ClassNotFoundException, SQLException {

	        ArrayList<Persona> listaPersonas = new ArrayList<>();

	        try {
	            String query = "SELECT * FROM usuario";
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            String url = "jdbc:mysql://localhost:3306/proyecto";
	            Connection con = DriverManager.getConnection(url, "root", "mysql");
	            Statement st = con.createStatement();
	            ResultSet rs= st.executeQuery(query);

	            while (rs.next()) {

	                String id = rs.getString("id");
	                String nombre = rs.getString("nombre");
	                String apellidos = rs.getString("apellidos");
	                String email = rs.getString("email");
	                String contrasena = rs.getString("contraseña");
	                listaPersonas.add(new Persona(id, nombre, apellidos, email, contrasena));

	            }
	        }catch(SQLException e) {
	            System.out.println("Error al recuperar las personas");
	        }
	        return listaPersonas;
	    }
	
}







