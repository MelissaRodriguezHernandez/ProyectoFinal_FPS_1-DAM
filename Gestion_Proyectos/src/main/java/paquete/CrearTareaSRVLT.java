package paquete;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CrearTareaSRVLT
 */
@WebServlet("/CrearTareaSRVLT")
public class CrearTareaSRVLT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			int idFase = Integer.parseInt(request.getParameter("idFase"));
			String nombre = request.getParameter("nombre");
			String descripcion = request.getParameter("descripcion");
			String idUsuario = request.getParameter("idUsuario");
			String fecha = request.getParameter("fecha");
			
			BBDD.insertTarea(idFase, nombre, descripcion, idUsuario, fecha);
			BBDD.actualizarEstadoProyecto();
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
