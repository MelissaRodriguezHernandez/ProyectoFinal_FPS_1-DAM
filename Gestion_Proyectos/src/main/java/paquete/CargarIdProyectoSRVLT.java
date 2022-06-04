package paquete;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CargarIdProyectoSRVLT
 */
@WebServlet("/CargarIdProyectoSRVLT")
public class CargarIdProyectoSRVLT extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		BBDD.idProyectoActual = request.getParameter("id");
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().append(String.valueOf(BBDD.idProyectoActual));
	}

}
