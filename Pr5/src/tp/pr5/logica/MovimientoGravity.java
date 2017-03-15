package tp.pr5.logica;

public class MovimientoGravity extends Movimiento {
	
	public MovimientoGravity(int columna, int fila, Ficha color) {
		this.columna = columna;
		this.fila = fila;
		this.jugador = color;
		this.colorUndo = Ficha.VACIA;
	}
	
	// Ejecuta el movimiento sobre el tablero que se recibe como parámetro.
    public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
    	int distanciaArriba, distanciaAbajo, distanciaIzq, distanciaDer;
    	int altura, anchura;
    	Ficha ocupada;
    		
    		altura = tab.getAlto();
    		anchura = tab.getAncho();
    		
    	if (this.columna > 0 && this.columna <= anchura && 
    			this.fila > 0 && this.fila <= altura) {
    			ocupada = tab.getCasilla(this.columna, this.fila);
    			// si no esta ocupada
	    	if (ocupada == Ficha.VACIA) {
	    		distanciaArriba = this.fila - 1;
	    		distanciaAbajo = altura - this.fila;
	    		distanciaIzq = this.columna - 1;
	    		distanciaDer = anchura - this.columna;
	    		// si no le afecta la gravedad
		    	if ((distanciaArriba == distanciaAbajo) &&
		    			(distanciaDer == distanciaIzq)) {
		    		tab.setCasilla(this.columna, this.fila, this.jugador);
		    	} else {
		    		gravedad(distanciaArriba, distanciaAbajo, distanciaIzq, 
		    				distanciaDer, tab);
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
	
	// Deshace el último movimiento del tablero.
	public void undo(Tablero tab) {
		tab.setCasilla(this.columna, this.fila, this.colorUndo);
	}
	
	// Se encarga de desplazar la ficha.
	private void gravedad(int distArriba, int distAbajo, int distIzq, 
			int distDer, Tablero tab) {
		Ficha siguiente;
		int desplaFila, desplaColumna;
			
			desplaFila = 0;
			desplaColumna = 0;
		
		// desplazamiento de la fila si tiene hueco
		if (distArriba < distAbajo) {
			desplaFila = -1;  
		} else if (distArriba > distAbajo) {
			desplaFila = 1;
		}
		// desplazamiento de la columna si tiene hueco
		if (distIzq < distDer) {
			desplaColumna = -1;  
		} else if (distIzq > distDer) {
			desplaColumna = 1;
		}
		
		/*
		 *  1  |  2
		 *  -------
		 *  3  |  4
		*/
		
		// 1er cuadrante
		if ((desplaFila == -1) && (desplaColumna == -1)) {
			if (distArriba > distIzq) {
				desplaFila = 0;
			} else if (distArriba < distIzq) {
				desplaColumna = 0;
			}
		} 
		
		// 2do cuadrante
		if ((desplaFila == -1) && (desplaColumna == +1)) {
			if (distArriba > distDer) {
				desplaFila = 0;
			} else if (distArriba < distDer) {
				desplaColumna = 0;
			}
		} 
		
		// 3er cuadrante
		if ((desplaFila == +1) && (desplaColumna == -1)) {
			if (distAbajo > distIzq) {
				desplaFila = 0;
			} else if (distAbajo < distIzq) {
				desplaColumna = 0;
			}
		} 
		
		// 4to cuadrante
		if ((desplaFila == +1) && (desplaColumna == +1)) {
			if (distAbajo > distDer) {
				desplaFila = 0;
			} else if (distAbajo < distDer) {
				desplaColumna = 0;
			}
		} 
		
		siguiente = tab.getCasilla(this.columna + desplaColumna, this.fila + desplaFila);
	
		while ((siguiente == Ficha.VACIA) && 
					(this.columna > 1 && this.columna < tab.getAncho()) &&
					(this.fila > 1 && this.fila < tab.getAlto())) {
				this.fila += desplaFila;
				this.columna += desplaColumna;
				siguiente = tab.getCasilla(this.columna + desplaColumna, this.fila + desplaFila);
		}
		tab.setCasilla(this.columna, this.fila, this.jugador);
	}
	
}