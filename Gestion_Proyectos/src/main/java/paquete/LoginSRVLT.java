package paquete;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginSRVLT
 */
@WebServlet("/LoginSRVLT")
public class LoginSRVLT extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * Almacenamos en objetos String la información que ha introducido el usuario en la página web
	 * y lo introduciomos por parametro al metodo comprobarLogin de la clase Persona, el cual,
	 * dependiendo de los datos retornara un valor de tipo int.
	 * Dependiendo de ste valor respondera a la página diferentes opciones.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int valor = 0;
		 
		String email = request.getParameter("email1");
		
		String contrasena_desencriptada = request.getParameter("contrasena1");
		
		String contrasena_encriptada = Persona.getMD5(contrasena_desencriptada);
		
		try {
			valor = Persona.comprobarLogin(contrasena_encriptada, email);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(valor == 1) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().append(String.valueOf("No existe ningun usuario con ese email"));
		}
		if(valor == 2) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().append(String.valueOf("La clave es incorrecta"));
		}
		if(valor == 3) {
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.getWriter().append(String.valueOf(valor));
		}
		
	}

}
