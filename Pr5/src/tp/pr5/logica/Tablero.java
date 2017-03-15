package tp.pr5.logica;

import java.util.HashMap;

public class Tablero implements TableroInmutable {
	private int alto;
	private int ancho;
	private Ficha [][] tablero;
	//contador de ficha de cada color
	private HashMap<Ficha, Integer> contador;
	
	// Construye un tablero vacío.
	public Tablero(int tx, int ty) {
		if (tx <= 0 || ty <= 0){
			tx = 1;
			ty = 1;
		}
		//inicializamos el hashMap
		this.contador = new HashMap<Ficha, Integer> ();
		Ficha[] colores = Ficha.values();
		for (Ficha c: colores) {
			this.contador.put(c, 0);
		}
		
		this.alto = ty;
		this.ancho = tx;
		this.tablero = new Ficha [alto][ancho];
		inicializarTablero();
	}
	
	public void inicializarTablero() {
		for (int i = 0; i < this.alto; i++) {
			for (int j = 0; j < this.ancho; j++) {
				this.tablero [i][j] = Ficha.VACIA;
			}
		}
		//como todas las fichas son vacias, las contamos
		this.contador.put(Ficha.VACIA, this.alto * this.ancho);
	}
	
	// Devuelve el número de filas del tablero.
	public int getAlto() {
		return this.alto;
	}
	
	// Devuelve el número de columnas del tablero.
	public int getAncho() {
		return this.ancho;
	} 
	
	// Devuelve el color de la casilla situada en la fila "x" y la columna "y".
	public Ficha getCasilla(int x, int y) {
		Ficha elegida;
		if ((x > this.ancho) || (y > this.alto) 
				|| (x <= 0) || (y <= 0)) {
			elegida = Ficha.VACIA;
		} else {
			elegida = this.tablero [y-1][x-1];
		}
		return elegida;
	}
	
	// Inserta la ficha en las coordenadas (x, y) del tablero.
	public void setCasilla(int x, int y, Ficha color) {
		if ((x > this.ancho) || (y > this.alto) 
				|| (x <= 0) || (y <= 0)) {
			x = 1; 
			y = 1;
		}
		
		//disminuimos el cont de la ficha sobre la que ponemos la nueva
		Ficha fichaSob = getCasilla(x,y);
		int contSob = this.contador.get(fichaSob);
		contSob--;
		this.contador.put(fichaSob, contSob);
		
		//aumentamos el cont de la ficha nueva
		int contNueva = this.contador.get(color);
		contNueva++;
		this.contador.put(color, contNueva);
		
		this.tablero [y-1][x-1] = color;
	}
	
	// Convierte en String el tablero de la partida, para así poder mostrarlo por pantalla.
	public String toString() {
		String tablero_str;
		
			tablero_str = "";
			
		for (int fila = 0; fila < this.alto; fila++) {
			tablero_str += "|";
			for (int columna = 0; columna < this.ancho; columna++) {
				tablero_str += (this.tablero[fila][columna].colorASimbolo());
			}
			tablero_str += "|\n";
		}
		
		// dibujamos la barra inferior
		tablero_str += "+";
		for (int i = 0; i < this.ancho; i++) {
			tablero_str += "-";
		}
		tablero_str += "+\n" + " ";
		for (int i = 0; i < this.ancho; i++) {
			if (i < 9) {
				tablero_str += (i+1);
			} else {
				tablero_str += (i-9);
			}
		}
		tablero_str += "\n";
		return tablero_str;
	}
	
	//Usado para la vistaGUI y reglasReversi, devuelve el num de fichas del color dado
	 public int getCont (Ficha color) {
		 return this.contador.get(color);
	 }
}