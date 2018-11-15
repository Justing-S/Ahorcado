package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import utilidades.Metodos;;
/**
 * Servlet implementation class Juego
 */
@WebServlet("/Juego")
public class Juego extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String[]arrayPalabras={"avión","camión","fútbol","ágil","cárcel"};	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int vidasRestantes;
		int intentosUsados;
		String palabraAleatoria;
		String letrasUsadas;
		String ultimaLetra;
		String[]celdas;
		String finJuego=null;
		String imagen;
		
		if(request.getParameter("empezar")!=null) {
			HttpSession nuevoIntento=request.getSession();
			nuevoIntento.invalidate();
		}
		
		if(request.getSession(false)==null) {
			HttpSession juegoNuevo=request.getSession(true);
			
			
			vidasRestantes=6;
			intentosUsados=0;
			palabraAleatoria=Metodos.generaPalabra(arrayPalabras);
			letrasUsadas="";
			ultimaLetra="";
			imagen="imagenes/6.png";
			
			celdas=new String[palabraAleatoria.length()];
			for(int i=0;i<palabraAleatoria.length();i++) {
				celdas[i]="_";
			}
			
			juegoNuevo.setAttribute("vidasRestantes", vidasRestantes);
			juegoNuevo.setAttribute("intentosUsados", intentosUsados);
			juegoNuevo.setAttribute("palabraAleatoria", palabraAleatoria);
			juegoNuevo.setAttribute("letrasUsadas", letrasUsadas);
			juegoNuevo.setAttribute("ultimaLetra", ultimaLetra);
			juegoNuevo.setAttribute("celdas", celdas);
			juegoNuevo.setAttribute("imagen", imagen);
			
		}else {
			HttpSession juegoActual=request.getSession();
			
			celdas=(String[]) juegoActual.getAttribute("celdas");
			vidasRestantes=(int) juegoActual.getAttribute("vidasRestantes");
			intentosUsados=(int) juegoActual.getAttribute("intentosUsados");
			palabraAleatoria=(String) juegoActual.getAttribute("palabraAleatoria");
			letrasUsadas=(String) juegoActual.getAttribute("letrasUsadas");
			ultimaLetra=(String) juegoActual.getAttribute("ultimaLetra");
			imagen=(String)	juegoActual.getAttribute("imagen");
			try {
				finJuego=(String)juegoActual.getAttribute("finJuego");
			}catch(Exception e) {
				finJuego=null;
			}
		}
		
		
		request.setAttribute("vidasRestantes", vidasRestantes);
		request.setAttribute("intentosUsados", intentosUsados);
		request.setAttribute("palabraAleatoria", palabraAleatoria);
		request.setAttribute("letrasUsadas", letrasUsadas);
		request.setAttribute("ultimaLetra", ultimaLetra);
		request.setAttribute("celdas", celdas);
		request.setAttribute("finJuego", finJuego);
		request.setAttribute("imagen", imagen);
		
		String vista = "/Ahorcado.jsp";  
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vista);
		dispatcher.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession juegoActual=request.getSession();
		
		int vidasRestantes;
		int intentosUsados;
		String palabraAleatoria;
		String letrasUsadas;
		String ultimaLetra;
		String[]celdas;
		String estadoPalabra;
		String finJuego=null;
		String error;
		String imagen;
		
		String letraSinTildes;
		String palabraSinTildes;
		
		
		vidasRestantes=(int) juegoActual.getAttribute("vidasRestantes");
		intentosUsados=(int) juegoActual.getAttribute("intentosUsados");
		palabraAleatoria=(String) juegoActual.getAttribute("palabraAleatoria");
		letrasUsadas=(String) juegoActual.getAttribute("letrasUsadas");
		ultimaLetra=request.getParameter("letra");
		celdas=(String[]) juegoActual.getAttribute("celdas");
		
		error=Metodos.comprobarLetraIntroducida(Metodos.noTildes(ultimaLetra), letrasUsadas);
		
		letraSinTildes=Metodos.noTildes(ultimaLetra);
		palabraSinTildes=Metodos.noTildes(palabraAleatoria);
		
		if(error.equals("")) {
			if(palabraSinTildes.contains(letraSinTildes)) {
				celdas=Metodos.pintaCeldasSolucion(palabraAleatoria, ultimaLetra, celdas);
				intentosUsados++;
				letrasUsadas+=ultimaLetra;
			}else {
				intentosUsados++;
				letrasUsadas+=ultimaLetra;
				vidasRestantes--;
			}
			
		}else {
			request.setAttribute("error", error);
		}
		
		estadoPalabra=String.join("", celdas);
		imagen=Metodos.devuelveImagen(vidasRestantes);
		
		if(estadoPalabra.equals(palabraAleatoria)) {
			imagen="imagenes/pingu.png";
			finJuego="Has ganado!!!";
		}
		
		if(vidasRestantes==0) {
			finJuego="Has perdido";	
		}
		
		
		
		juegoActual.setAttribute("vidasRestantes", vidasRestantes);
		juegoActual.setAttribute("intentosUsados", intentosUsados);
		juegoActual.setAttribute("palabraAleatoria", palabraAleatoria);
		juegoActual.setAttribute("letrasUsadas", letrasUsadas);
		juegoActual.setAttribute("ultimaLetra",ultimaLetra);
		juegoActual.setAttribute("celdas", celdas);
		juegoActual.setAttribute("finJuego", finJuego);
		juegoActual.setAttribute("imagen", imagen);
		
		request.setAttribute("vidasRestantes", vidasRestantes);
		request.setAttribute("intentosUsados", intentosUsados);
		request.setAttribute("palabraAleatoria", palabraAleatoria);
		request.setAttribute("letrasUsadas", letrasUsadas);
		request.setAttribute("ultimaLetra", ultimaLetra);
		request.setAttribute("celdas", celdas);
		request.setAttribute("finJuego", finJuego);
		request.setAttribute("imagen", imagen);
		
		
		String vista = "/Ahorcado.jsp";  
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vista);
		dispatcher.forward(request,response);
		
	}

}
