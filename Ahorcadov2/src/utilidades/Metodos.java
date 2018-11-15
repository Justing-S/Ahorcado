package utilidades;

import java.text.Normalizer;

public class Metodos {

	public static String generaPalabra(String[]palabras) {
		String palabraSeleccionada=palabras[(int) (Math.random() * (palabras.length-1))];
		return palabraSeleccionada;
	}
	
	public static String[] pintaCeldasSolucion(String palabraAleatoria,String letra,String[] celdas) {
		String palabraSinTildes=noTildes(palabraAleatoria);
		String letraSinTildes=noTildes(letra);
		
		String[]palabra=palabraSinTildes.split("");
		
		for(int i=0;i<palabra.length;i++) {
			if(palabra[i].equals(letraSinTildes)) {
				celdas[i]=Character.toString(palabraAleatoria.charAt(i));
			}
		}
		
		return celdas;
			
	}
	
	public static String comprobarLetraIntroducida(String letra,String letrasUsadas) {
		if(letra.equals("")) {
			return "Introduce una letra";
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
	
	public static String noTildes(String letra) {
		String primera=letra;
		String segunda=Normalizer.normalize(primera, Normalizer.Form.NFD);
		
		segunda=segunda.replaceAll("[^\\p{ASCII}]","");
		
		segunda=segunda.toLowerCase();
		
		return segunda;
	}
	
	public static String devuelveImagen(int vidas) {
		String imagen="imagenes/"+vidas+".png";
		return imagen;
	}
	
	
	
	public static void main(String[]args) {
		System.out.println(noTildes("Á"));
	}
}
