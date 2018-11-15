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
		//Primero declaro las variables que voy a usar para el juego
		int vidasRestantes;
		int intentosUsados;
		String palabraAleatoria;
		String letrasUsadas;
		String ultimaLetra;
		String[]celdas;
		String finJuego=null;
		String imagen;
		
		//Si el parametro empezar no es nulo quiere decir que le hemos dado al link de volver a empezar
		//por lo que invalidamos la sesion actual
		if(request.getParameter("empezar")!=null) {
			HttpSession nuevoIntento=request.getSession();
			nuevoIntento.invalidate();
		}
		
		//Si no hay sesion creamos una nueva sesion e iniciamos todas las variables a un valor por defecto
		
		if(request.getSession(false)==null) {
			HttpSession juegoNuevo=request.getSession(true);
			
			
			vidasRestantes=6;
			intentosUsados=0;
			//Sacamos una palabra aleatoria con el metodo de la clase metodos
			palabraAleatoria=Metodos.generaPalabra(arrayPalabras);
			letrasUsadas="";
			ultimaLetra="";
			imagen="imagenes/6.png";
			
			//Rellenamos celdas de guiones bajos siendo el numero de guiones igual a la longitud de la palabra
			celdas=new String[palabraAleatoria.length()];
			for(int i=0;i<palabraAleatoria.length();i++) {
				celdas[i]="_";
			}
			
			//Metemos todas las variables en la sesion
			juegoNuevo.setAttribute("vidasRestantes", vidasRestantes);
			juegoNuevo.setAttribute("intentosUsados", intentosUsados);
			juegoNuevo.setAttribute("palabraAleatoria", palabraAleatoria);
			juegoNuevo.setAttribute("letrasUsadas", letrasUsadas);
			juegoNuevo.setAttribute("ultimaLetra", ultimaLetra);
			juegoNuevo.setAttribute("celdas", celdas);
			juegoNuevo.setAttribute("imagen", imagen);
			
			//Si ya existe una sesion recogemos los datos de la sesion 
		}else {
			HttpSession juegoActual=request.getSession();
			
			celdas=(String[]) juegoActual.getAttribute("celdas");
			vidasRestantes=(int) juegoActual.getAttribute("vidasRestantes");
			intentosUsados=(int) juegoActual.getAttribute("intentosUsados");
			palabraAleatoria=(String) juegoActual.getAttribute("palabraAleatoria");
			letrasUsadas=(String) juegoActual.getAttribute("letrasUsadas");
			ultimaLetra=(String) juegoActual.getAttribute("ultimaLetra");
			imagen=(String)	juegoActual.getAttribute("imagen");
			//Ya que fin de juego nos puede dar nulo lo metemos dentro de un try catch
			try {
				finJuego=(String)juegoActual.getAttribute("finJuego");
			}catch(Exception e) {
				finJuego=null;
			}
		}
		
		
		//Mandamos todos los datos a el jsp para pintarlos
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
		
		//VOlvemos a declarar las variables que vamos a utilizar
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
		
		//Cogemos las variables contenidas en la sesion
		vidasRestantes=(int) juegoActual.getAttribute("vidasRestantes");
		intentosUsados=(int) juegoActual.getAttribute("intentosUsados");
		palabraAleatoria=(String) juegoActual.getAttribute("palabraAleatoria");
		letrasUsadas=(String) juegoActual.getAttribute("letrasUsadas");
		//Ultima letra al ser un parametro que nos pasa el usuario por un input lo recojemos con un get parameter
		ultimaLetra=request.getParameter("letra");
		celdas=(String[]) juegoActual.getAttribute("celdas");
		
		//Quitamos los signos diacriticos tanto de la letra como de la palabra para compararlas
		letraSinTildes=Metodos.noTildes(ultimaLetra);
		palabraSinTildes=Metodos.noTildes(palabraAleatoria);
		
		error=Metodos.comprobarLetraIntroducida(letraSinTildes, letrasUsadas);
		//Si el metodo comprobarLetraINtroducida nos devuelve una cadena vacia significa que no hay errores
		if(error.equals("")) {
			//Si la palabra sin tilde contiene la letra sin tilde se aumentara un intento se pintaran las celdas
			//pero no se quitaran vidas
			if(palabraSinTildes.contains(letraSinTildes)) {
				celdas=Metodos.pintaCeldasSolucion(palabraAleatoria, ultimaLetra, celdas);
				intentosUsados++;
				letrasUsadas+=ultimaLetra;
				//Si no aumentaran los intentos usados y se quitaran vidas
			}else {
				intentosUsados++;
				letrasUsadas+=ultimaLetra;
				vidasRestantes--;
			}
			//SI la cadena no es nula envia un atributo al jsp llamado error con la cadena de error
		}else {
			request.setAttribute("error", error);
		}
		
		//COmprobaremos la como tenemos la palabra actualmente sacando los valores del array
		estadoPalabra=String.join("", celdas);
		//Tambien sacaremos la imagen que vamos a enviar al jsp en funcion de las vidas
		imagen=Metodos.devuelveImagen(vidasRestantes);
		
		//Si la palabra que tenemos actualmente es igual a la palabra aleatoria significa que habremos ganado
		//por lo que le mandaremos un atributo de fin de juego al jsp y la imagen de victoria
		if(estadoPalabra.equals(palabraAleatoria)) {
			imagen="imagenes/pingu.png";
			finJuego="Has ganado!!!";
		}
		
		//SI las vidas han llegado a 0 le mandaremos el atributo de fin de juego también
		if(vidasRestantes==0) {
			finJuego="Has perdido";	
		}
		
		
		//Metemos todos los datos en la sesion
		juegoActual.setAttribute("vidasRestantes", vidasRestantes);
		juegoActual.setAttribute("intentosUsados", intentosUsados);
		juegoActual.setAttribute("palabraAleatoria", palabraAleatoria);
		juegoActual.setAttribute("letrasUsadas", letrasUsadas);
		juegoActual.setAttribute("ultimaLetra",ultimaLetra);
		juegoActual.setAttribute("celdas", celdas);
		juegoActual.setAttribute("finJuego", finJuego);
		juegoActual.setAttribute("imagen", imagen);
		
		//Le pasamos todos los datos a el jsp
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
