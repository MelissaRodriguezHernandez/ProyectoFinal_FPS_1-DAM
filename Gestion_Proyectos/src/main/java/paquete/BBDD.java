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
	    
	    /**
	     *Seleccionamos toda la información en la base de datos del proyecto seleccionado para luego incrustarlo en un html 
	     *este metodo devolvera un String que contendra el html que se enviara de vuelta a la página web
	     * @return
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static String cargarInfoProyecto() throws ClassNotFoundException, SQLException {
	        String resultado = "";
	        String id = idProyectoActual;

	        String query = "SELECT id, nombre, tipo, estado , descripcion, fechaInicio, fechaFinal FROM proyecto  WHERE id='"+id+"'";
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);


	        while(rs.next()) {
	            resultado = resultado + "<div><h3>Id | <h2>"+rs.getString("id")+"</h2></h3><br><br>"
	                    +"<h3>Nombre | <h2>"+rs.getString("nombre")+"</h2></h3><br><br>"
	                    +"<h3>Tipo | <h2>"+rs.getString("tipo")+"</h2></h3><br><br>"
	                    +"<h3>Estado | <h2>"+rs.getString("estado")+"</h2></h3><br><br></div>"
	                    +"<div><h3>Descripción | <h2>"+rs.getString("descripcion")+"</h2></h3><br><br>"
	                    +"<h3>Fecha de inicio | <h2>"+rs.getString("fechaInicio")+"</h2></h3><br><br>"
	                    +"<h3>Fecha de terminio | <h2>"+rs.getString("fechaFinal")+"</h2></h3><br><br></div>"
	                    +"<div><i class=\"icon icon-pie-chart\" id=\"icon\"></i></div>";
	        }
	        return resultado;
	    }

	    
	    /**
	     *Metodo que insertara un nuevo proyecto en la base de datos con toda la información pasada por parametro
	     * @param id
	     * @param nombre
	     * @param idDirector
	     * @param descripcion
	     * @param tipo
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static void insertProyecto(String id, String nombre, String idDirector, String descripcion, String tipo, String fechaI, String fechaF) throws ClassNotFoundException, SQLException {

	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO proyecto (id, nombre, descripcion, tipo, idDirector, fechaInicio, fechaFinal) VALUES ('"+id+"','"+nombre+"','"+descripcion+"','"+tipo+"','"+idDirector+"','"+fechaI+"','"+fechaF+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        try {
	            st.executeUpdate(query);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    /**
	     *Metodo que añadira una fase a la base de datos con la información pasada por parametro
	     *el id del proyecto será el del abierto por el usuario
	     *La fase se añade dentro de un proyecto ya creado
	     *Cada vez que elegimos y abrimos un proyecto cogemos el id del proyecto y lo almacenamos en atributo estatico
	     *para luego utilizarlo en otros metodos
	     * @param idProyecto
	     * @param nombre
	     * @param descripcion
	     * @param estado
	     * @param importancia
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static void insertFase( String nombre, String descripcion, String importancia, String fechaI, String fechaF) throws ClassNotFoundException, SQLException {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO fase (idProyecto, nombre, descripcion, importancia, fecha_inicio, fecha_final) VALUES ('"+BBDD.idProyectoActual+"','"+nombre+"','"+descripcion+"','"+importancia+"','"+fechaI+"','"+fechaF+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        try {
	            st.executeUpdate(query);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    /**
	     *Metodo que añadira un usuario (el id se pasa por parametro) a la tabla de grupo de los participantes en el proyecto
	     *Otra vez el id del proyecto se coje del atributo estatico
	     * @param idUsuario
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static void insertParticipanteProyecto(String idUsuario) throws ClassNotFoundException, SQLException {

	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO grupo_participantes VALUES ('"+BBDD.idProyectoActual+"','"+idUsuario+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        try {
	            st.executeUpdate(query);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    /**
	     *Se pasa por parametro el id del usuario y el de la fase para luego insertar un nuevo participante en la tabla de
	     * grupo de participantes por fase en el proyecto
	     * Otra vez el id del proyecto se coje del atributo estatico
	     * @param idUsuario
	     * @param idFase
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static void insertParticipanteFase(String idUsuario, String idFase) throws ClassNotFoundException, SQLException {

	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO grupo_participantes_fase VALUES ('"+BBDD.idProyectoActual+"','"+idUsuario+"','"+idFase+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        try {
	            st.executeUpdate(query);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	    
	    /**
	     * Este metodo carga todas las fases de un proyecto para mostrarlas 
	     * en los formularios de añadir tareas 
	     * Otra vez el id del proyecto se coje del atributo estatico
	     * devolvera un String con el html que se incrustara en la página web
	     * @return
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static String cargarFasesForm() throws ClassNotFoundException, SQLException {
	        String resultado = "";

	        String query = "SELECT id, nombre FROM fase WHERE idProyecto='"+BBDD.idProyectoActual+"'" ;
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);

	        while(rs.next()) {
	            resultado = resultado + "<option value= \""+rs.getString("id")+"\">"+rs.getString("nombre")+"</option>";
	        }

	        return resultado;
	    }
	    
	   /**
	    *  Este metodo carga todas los participantes de un proyecto para mostrarlos 
	     * en los formularios de añadir tareas
	     * Otra vez el id del proyecto se coje del atributo estatico
	     * devolvera un String con el html que se incrustara en la página web
	    * @return
	    * @throws ClassNotFoundException
	    * @throws SQLException
	    */
	    public static String inforParticipanteForm() throws ClassNotFoundException, SQLException {
	        String resultado = "";

	        String query = "SELECT idUsuario FROM grupo_participantes WHERE idProyecto='"+BBDD.idProyectoActual+"' AND idUsuario IS NOT NULL" ;
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        ResultSet rs= st.executeQuery(query);

	        while(rs.next()) {
	            resultado = resultado + "<option value= \""+rs.getString("idUsuario")+"\">"+rs.getString("idUsuario")+"</option>";
	        }

	        return resultado;
	    }

	    /**
	     *Metodo que añadira una tarea nueva a la base de datos con toda la informaacion pasada por parametro
	     *Otra vez el id del proyecto se coje del atributo estatico
	     * @param idFase
	     * @param nombre
	     * @param descripcion
	     * @param idUsuario
	     * @param fecha
	     * @throws ClassNotFoundException
	     * @throws SQLException
	     */
	    public static void insertTarea( int idFase, String nombre, String descripcion, String idUsuario, String fecha) throws ClassNotFoundException, SQLException {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        String url = "jdbc:mysql://localhost:3306/proyecto";
	        String query = "INSERT INTO tarea(idFase, idProyecto, nombre, descripcion, idUsuario, fechaEntrega) VALUES ('"+idFase+"','"+BBDD.idProyectoActual+"','"+nombre+"','"+descripcion+"','"+idUsuario+"','"+fecha+"')";
	        Connection con = DriverManager.getConnection(url, "root", "mysql");
	        Statement st = con.createStatement();
	        try {
	            st.executeUpdate(query);
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }


	
}







