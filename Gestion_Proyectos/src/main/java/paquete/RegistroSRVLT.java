package paquete;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistroSRVLT
 */
@WebServlet("/RegistroSRVLT")
public class RegistroSRVLT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 *Almacenamos en objetos Strings la información del usuario que nos manda el js 
	 *y la enviamos a la base de datos para almacenarlo con el metodo insertUsuario de la clase BBDD, el cual retorna un booleano;
	 *Si devuelve true es que el usuario ya existe, por lo tanto no se almacenara; si retorna false no existe por lo tanto se guardara en la base de datos.
	 *En el caso que retorne true se enviara una respuesta al js.
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		boolean retorno = false;
		
		try {
			
			//el id se genera gracias al metodo asignarId de la clase Persona
			String	id = Persona.asignarId();
			
			String nombre = request.getParameter("nombre");
			
			String apellidos = request.getParameter("apellidos");
			
			String email = request.getParameter("email");
			
			//Almacenamos en un String la contraseña intrducida por el usuario 
			//para luego encriptarla con el metodo getMD5 de la clase Persona y mandarlo asi a la base de datos
			String contrasena_desencriptada = request.getParameter("contrasena");
			
			String contrasena_encriptada = Persona.getMD5(contrasena_desencriptada);
						
			retorno = BBDD.insertUsuario(id,nombre,apellidos,email, contrasena_encriptada);
									
					
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().append(String.valueOf(retorno));	
		
	}

}
