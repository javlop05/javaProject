package tp.pr5.logica;

import java.util.ArrayList;

public class MovimientoReversi extends Movimiento {

	// Casillas volteadas por el movimiento
	private ArrayList<Casilla> volteos;
	
	public MovimientoReversi(int columna, int fila, Ficha color) {
		this.columna = columna;
		this.fila = fila;
		this.jugador = color;
		this.colorUndo = Ficha.VACIA;
		this.volteos = new ArrayList<Casilla> ();
	}
	
	// Ejecuta el movimiento sobre el tablero que se recibe como parámetro.
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean valido;
		int altura, anchura;
    	Ficha ocupada;
    		
    		altura = tab.getAlto();
    		anchura = tab.getAncho();
    		valido = false;
    		
		if (this.columna > 0 && this.columna <= anchura && 
    			this.fila > 0 && this.fila <= altura) {
			
    			ocupada = tab.getCasilla(this.columna, this.fila);
    			
    			// si no esta ocupada
	    	if (ocupada == Ficha.VACIA) {
	    		
	    		/* Obtenemos los movimientos posibles, para cada uno 
	    		 * se actualiza las fichas que voltea*/
	    		ArrayList <MovimientoReversi> movsPosibles = 
	    				ReglasReversi.calcularMovsReversi(tab, this.jugador);
	    		
	    		int i = 0;
	    		// Buscamos si este movimiento esta dentro de los posibles
	    		while (!valido && (i < movsPosibles.size())) {
	    			MovimientoReversi aux = movsPosibles.get(i);
	    			if ((aux.fila == this.fila) && (aux.columna == this.columna)) {
	    				valido = true;
	    				this.volteos = aux.volteos;
	    			} else i++;
	    		}
	    		if (valido) {
	    			tab.setCasilla(this.columna, this.fila, this.jugador);
	    			voltearFichas(tab);
	    		} else {
	    			throw new MovimientoInvalido("Movimiento incorrecto.");
	    		}
	    	} else {
	    		// casilla ocupada
	    		throw new MovimientoInvalido("Casilla ocupada.");
	    	}
		} else {
			// posición incorrecta
    		throw new MovimientoInvalido("Posición incorrecta.");
		}
	}

	// Deshace el último movimiento del tablero (eliminando la ficha y desvolteando)
	public void undo(Tablero tab) {
		//deshacemos la ficha puesta
		tab.setCasilla(this.columna, this.fila, this.colorUndo);
		//deshacemos los volteos qe causo poner la ficha
		deshacerVolteos(tab);
	}
	
	// Verifica si, con el movimiento dado, se rodea alguna ficha del contrincante
	public boolean movValidoReversi (Tablero t) {
		boolean valido;
		int i;
		Direccion [] direccion;
		
			valido = false;
			i = 0;
			direccion = Direccion.values();

		//comprobamos todas las direcciones y las que volteen las añadimos al arrayList volteos
		while ((i < direccion.length)) {
			valido |= comprobarDir (t, direccion[i]);
			i++;
		}

		return valido;
	}

	/* Dada una direccion añade al ArrayList de volteos, las fichas que se voltean
	 * en esa direccion */
	private boolean comprobarDir (Tablero t, Direccion dir) {
		boolean voltea, opuestaEncontrada;
		int fila, columna;
		Ficha color, oponente, ficha; 

			voltea = false;
			opuestaEncontrada = false;
			fila = this.fila;
			columna = this.columna;
			color = this.jugador;
			oponente = obtenerOponente(color);
			ficha = oponente;

		//recorre todas las fichas contrarias
		while ((fila > 0 && fila <= t.getAlto()) &&
				(columna > 0 && columna <= t.getAncho())  
				&& (ficha == oponente)){
			fila += dir.getContFilas();
			columna += dir.getContColumnas();
			ficha = t.getCasilla(columna, fila);
			if (ficha == oponente) opuestaEncontrada = true;
		}

		voltea = (opuestaEncontrada && (ficha == color));
		
		/* si voltea recorremos desde la ultima posicion (nuestro color) hasta el otro 
		 * extremo donde tenemos otra ficha de nuestro color, y metemos todas esas casillas
		 * en el arrayList de volteos*/
		if (voltea) {
			int f = fila - dir.getContFilas();
			int c = columna - dir.getContColumnas();
			while ((f != this.fila) || (c != this.columna)) {
				Casilla casilla = new Casilla (c, f);
				this.volteos.add(casilla);
				//restamos los incrementos
				f -= dir.getContFilas();
				c -= dir.getContColumnas();
			}
		}
		
		return voltea;
	}

	// Dada una ficha devuelve el oponente
	private Ficha obtenerOponente (Ficha jugador) {
		Ficha adversario;

			adversario = Ficha.VACIA;

		if (jugador == Ficha.NEGRA) {
			adversario = Ficha.BLANCA;
		} else if (jugador == Ficha.BLANCA) {
			adversario = Ficha.NEGRA;
		}

		return adversario;
	}

	//Recorre el ArrayList con las casillas que debemos voltear
	private void voltearFichas (Tablero t) {
		for (Casilla c: this.volteos) {
			t.setCasilla(c.getColumna(), c.getFila(), this.jugador);
		}
	}
	
	// Recorre el ArrayList con las casillas que debemos desvoltear
	private void deshacerVolteos (Tablero t) {
		Ficha oponente;
		
			oponente = obtenerOponente (this.jugador);
			
		for (Casilla c: this.volteos) {
			t.setCasilla(c.getColumna(), c.getFila(), oponente);
		}
	}
}
