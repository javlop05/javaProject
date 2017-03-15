package tp.pr5.logica;

import tp.pr5.control.ModoHumano;
import tp.pr5.control.TipoTurno;

public class Utilidades {
	
	// Verifica si, desde la posición dada, hay un grupo en la vertical "|".
	public static boolean vertical(int x, int y, Tablero t) {
		boolean grupo;
		int contVer;
		int f, c;
		int altura;
		Ficha fichaAmirar;
		
			grupo = false;
			contVer = 1;
			c = x; f = y;
			altura = t.getAlto();
			fichaAmirar = t.getCasilla(c, f);
			
		// vertical arriba
		while ((f >= 0) && 
				(t.getCasilla(c, f-1) == fichaAmirar) 
				&& (!grupo)) {
			contVer++;
			if (contVer < 4) {
				f--;
			} else {
				grupo = true;
			}
		}
		f = y;
						
		// vertical abajo
		while ((f < altura) && 
				(t.getCasilla(c, f+1) == fichaAmirar)
				&& (!grupo)) {
				contVer++;
				if (contVer < 4) {
					f++;
				} else {
					grupo = true;
				}
		}
		
		return grupo;
	}
	
	// Verifica si, desde la posición dada, hay un grupo en la horizontal "--".
	public static boolean horizontal(int x, int y, Tablero t) {
		boolean grupo;
		int contHor;
		int f, c;
		int anchura;
		Ficha fichaAmirar;
		
			grupo = false;
			contHor = 1;
			c = x; f = y;
			anchura = t.getAncho();
			fichaAmirar = t.getCasilla(c, f);
		
		// horizontal derecha
		while ((c < anchura) && 
				(t.getCasilla(c+1, f) == fichaAmirar)
				&& (!grupo)) {
				contHor++;
				if (contHor < 4) {
					c++;
				} else {
					grupo = true;
				}
		}
		c = x;
		
		// horizontal izquierda
		while ((c >= 0) && 
				(t.getCasilla(c-1, f) == fichaAmirar) 
				&& (!grupo)) {
				contHor++;
				if (contHor < 4) {
					c--;
				} else {
					grupo = true;
				}
		}
		
		return grupo;
	}

	// Verifica si, desde la posición dada, hay grupo en la diagonal "/".
	public static boolean diagonal(int x, int y, Tablero t) {
		boolean grupo;
		int contCre;
		int f, c;
		int anchura, altura;
		Ficha fichaAmirar;
		
			grupo = false;
			contCre = 1;
			c = x; f = y;
			anchura = t.getAncho();
			altura = t.getAlto();
			fichaAmirar = t.getCasilla(c, f);
			
		// noreste 
		while ((f >= 0) && (c < anchura) && 
				(t.getCasilla(c+1, f-1) == fichaAmirar) 
				&& (!grupo)) {
				contCre++;
				if (contCre < 4) {
					f--;
					c++;
				} else {
					grupo = true;
				}
			}
			f = y;
			c = x;
			
		// suroeste 
		while ((f < altura) && (c >= 0) && 
				(t.getCasilla(c-1, f+1) == fichaAmirar)
				&& (!grupo)) {
				contCre++;
				if (contCre < 4) {
					f++;
					c--;
				} else {
					grupo = true;
				}
		}
			
		return grupo;
	}

	// Verifica si, desde la posición dada, hay grupo en la diagonal "\".
	public static boolean diagonal2(int x, int y, Tablero t) {
		boolean grupo;
		int contDec;
		int f, c;
		int anchura, altura;
		Ficha fichaAmirar;
		
			grupo = false;
			contDec = 1;
			c = x; f = y;
			anchura = t.getAncho();
			altura = t.getAlto();
			fichaAmirar = t.getCasilla(c, f);
			
		// noroeste 
		while ((f >= 0) && (c >= 0) && 
				(t.getCasilla(c-1, f-1) == fichaAmirar) 
				&& (!grupo)) {
			contDec++;
			if (contDec < 4) {
				f--;
				c--;
			} else {
				grupo = true;
			}
		}
		f = y;
		c = x;
		
		// sureste 
		while ((f < altura) && (c < anchura) && 
				(t.getCasilla(c+1, f+1) == fichaAmirar)
				&& (!grupo)) {
			contDec++;
			if (contDec < 4) {
				f++;
				c++;
			} else {
				grupo = true;
			}
		}
		
		return grupo;
	}
	
	//Usado en ambos controladores para inicializar las fichas a Humanas
	public static void inicializarFichas() {
		Ficha.BLANCA.setModoJuego(new ModoHumano());
		Ficha.NEGRA.setModoJuego(new ModoHumano());
		
		Ficha.BLANCA.setTipoTurno(TipoTurno.HUMANO);
		Ficha.NEGRA.setTipoTurno(TipoTurno.HUMANO);
	}
}