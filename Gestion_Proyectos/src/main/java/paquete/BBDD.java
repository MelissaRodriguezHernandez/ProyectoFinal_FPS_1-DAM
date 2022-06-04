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
	    
	    /**
	     *Metodo que selecciona todas las filas de la tabla proyecto de la base de datos, fila por fila
	     * va creando un nuevo objeto proyecto con la información y lo mete en un arraylist de proyectos.
	     * Resumidamente metemos en un arraylist todas los proyectos de la base de datos para usarla posteriormente en otros metodos
	     * @return
	     * @throws ClassNotFoundException
	     */
	    public static ArrayList<Proyecto> recuperarProyectos() throws ClassNotFoundException{

	        ArrayList<Proyecto> listaProyectos = new ArrayList<>();

	        try {
	            String query = "SELECT * FROM proyecto";
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            String url = "jdbc:mysql://localhost:3306/proyecto";
	            Connection con = DriverManager.getConnection(url, "root", "mysql");
	            Statement st = con.createStatement();
	            ResultSet rs= st.executeQuery(query);

	            while (rs.next()) {

	                String id = rs.getString("id");
	                String nombre = rs.getString("nombre");
	                String descripcion = rs.getString("descripcion");
	                String idDirector = rs.getString("idDirector");

	                listaProyectos.add(new Proyecto(id, nombre, descripcion, idDirector));

	            }
	        }catch(SQLException e) {
	            System.out.println("Error al recuperar las personas");
	        }

	        return listaProyectos;

	    }

	    
	    /**
	     * Metodo que carga la informacion almacenada en la base de datos
	     *  de los proyectos creados por el usuario y lo va concatenando con html,
	     *  para luego enviarlo a la pagina principal
	     * @return
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static String trabajosDirector() throws ClassNotFoundException, SQLException {
	        String resultado = "";
	        String id = usuarioWeb;
	        String query = "SELECT * FROM proyecto.proyecto WHERE idDirector='"+id+"'";
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);

	        resultado = "<h2>Proyectos propios</h2>";

	        while (rs.next()) {
	            resultado = resultado
	                    + "<div onclick=\"enviaridProyecto("+rs.getString("id")+")\"><a href=\"proyecto.html\"><h3>"+rs.getString("nombre")+" <span >"+rs.getString("id")+"</span>| "
	                    +rs.getString("tipo")+" | "
	                    +rs.getString("estado")+"|</h3></a></div>";
	        }


	        return resultado;
	    }

	    /**
	     * Metodo que carga la informacion almacenada en la base de datos
	     *  de los proyectos donde el usuario participa y lo va concatenando con html,
	     *  para luego enviarlo a la pagina principal
	     * @return
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static String trabajosParticipante() throws ClassNotFoundException, SQLException{
	        String resultado = "";
	        String id = usuarioWeb;
	        String query = "SELECT p.id , p.nombre, p.tipo, p.estado FROM proyecto p JOIN grupo_participantes gp ON gp.idProyecto = p.id WHERE gp.idUsuario='"+id+"' GROUP BY p.id" ;
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);

	        resultado = "<h2>Proyectos participando</h2>";

	        while (rs.next()) {
	            resultado = resultado
	                    + "<div onclick=\"enviaridProyecto("+rs.getString("id")+")\"><a href=\"proyecto.html\"><h3>"+rs.getString("nombre")+" <span>"+rs.getString("id")+"</span>| "
	                    +rs.getString("tipo")+" | "
	                    +rs.getString("estado")+"|</h3></a></div>";
	        }


	        return resultado;
	    }

	    /**
	     * Metodo que carga la informacion del usuario y los concatena con html
	     * para luego enviarlo a la pagina
	     * @return
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static String informacionUsuario() throws ClassNotFoundException, SQLException {
	        String resultado = "";
	        String id = usuarioWeb;
	        String query = "SELECT id, nombre, apellidos, email FROM usuario  WHERE id='"+id+"'";
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);

	        resultado = "<h1>Cuenta</h1>";

	        while(rs.next()) {
	            resultado = resultado + "<h4> ID |"+rs.getString("id")+"</h4>"
	                    +"<h4>"+rs.getString("nombre")+"</h4>"
	                    +"<h4>"+rs.getString("apellidos")+"</h4>"
	                    +"<h4>"+rs.getString("email")+"</h4>";
	        }
	        return resultado;
	    }

	
}







