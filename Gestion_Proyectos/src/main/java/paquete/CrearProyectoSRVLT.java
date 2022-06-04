package paquete;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CrearProyectoSRVLT
 */
@WebServlet("/CrearProyectoSRVLT")
public class CrearProyectoSRVLT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String id = Proyecto.asignarId();
			
			String idDirector = BBDD.usuarioWeb;
			
			String nombre = request.getParameter("nombre");
			
			String descripcion = request.getParameter("descripcion");
			
			String tipo = request.getParameter("tipo");
						
			String fechaI = request.getParameter("fechaI");
			
			String fechaF = request.getParameter("fechaF");
												
			BBDD.insertProyecto(id, nombre, idDirector, descripcion, tipo, fechaI, fechaF);
								
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
