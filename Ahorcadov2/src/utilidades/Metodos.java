package utilidades;

import java.text.Normalizer;

public class Metodos {

	//Metodo que nos saca una celda aleatoria de un array y nos la devuelve
	public static String generaPalabra(String[]palabras) {
		String palabraSeleccionada=palabras[(int) (Math.random() * (palabras.length-1))];
		return palabraSeleccionada;
	}
	
	
	public static String[] pintaCeldasSolucion(String palabraAleatoria,String letra,String[] celdas) {
		//Quitamos signos diacriticos a la palabra y a la letra
		String palabraSinTildes=noTildes(palabraAleatoria);
		String letraSinTildes=noTildes(letra);
		
		String[]palabra=palabraSinTildes.split("");
		
		//COmparamos celda por celda si la letraSinTildes es igual a la posicion del array igualamos celdas[i]
		//a la posicion de la palabra con signos diacriticos de [i]
		for(int i=0;i<palabra.length;i++) {
			if(palabra[i].equals(letraSinTildes)) {
				celdas[i]=Character.toString(palabraAleatoria.charAt(i));
			}
		}
		
		return celdas;
			
	}
	
	public static String comprobarLetraIntroducida(String letra,String letrasUsadas) {
		//SI letra es una cadena vacia nos dara un error
		if(letra.equals("")) {
			return "Introduce una letra";
			//SI no comprobara que no estaba ya usada y que no es un digito o caracter no alfanumerico
		}else {
			char caracter=letra.charAt(0);
			boolean alfanumerico=Character.isLetterOrDigit(caracter);
			
			
			
			if(alfanumerico==false || Character.isDigit(caracter)) {
				return "Introduce una letra";
			}else if(letrasUsadas.contains(letra)){
				return "Ya probaste esa letra";
			}else if(letra.length()>1) {
				return "Introduce solo un caracter";
			}else {
				return "";
			}
		}
		
		
	}
	
	//Metodo para quitar signos diacriticos
	public static String noTildes(String letra) {
		String primera=letra;
		String segunda=Normalizer.normalize(primera, Normalizer.Form.NFD);
		
		segunda=segunda.replaceAll("[^\\p{ASCII}]","");
		
		segunda=segunda.toLowerCase();
		
		return segunda;
	}
	
	//Metodo para devolver string con la ruta de la imagen
	public static String devuelveImagen(int vidas) {
		String imagen="imagenes/"+vidas+".png";
		return imagen;
	}
	
	
	
	public static void main(String[]args) {
		System.out.println(noTildes("Á"));
	}
}
