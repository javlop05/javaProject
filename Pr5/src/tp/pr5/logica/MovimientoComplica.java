package tp.pr5.logica;

public class MovimientoComplica extends Movimiento {
	
	public MovimientoComplica(int donde, Ficha color) {
		this.columna = donde;
		this.fila = -1;
		this.jugador = color;
		this.colorUndo = Ficha.VACIA;
	}
	
	// Ejecuta el movimiento sobre el tablero que se recibe como parámetro.
	public void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido {
		boolean huecoEncontrado;
		int fila;
		
			fila = 1;
			huecoEncontrado = false;
		
		if (this.columna > 0 && this.columna <= tab.getAncho()) {
				
			/* si antes de realizar el movimiento la columna está llena, 
			 * metemos en el colorUndo la ficha de la columna situada más
			 * abajo del tablero y desplazamos hacia abajo las fichas
			 */
			if (tab.getCasilla(this.columna, fila) != Ficha.VACIA) {
				this.colorUndo = tab.getCasilla(this.columna, tab.getAlto());
				for (int f = tab.getAlto(); f > 1; f--) {
					tab.setCasilla(this.columna, f, tab.getCasilla(this.columna, f-1));
				}
			} else {
				// dejamos caer la ficha
				while (fila < tab.getAlto() && !huecoEncontrado) {
					if (tab.getCasilla (this.columna, fila + 1) == Ficha.VACIA) {
						fila++;
					} else {
						huecoEncontrado = true;
					}
				}
			}
			// actualizamos la fila donde se ha insertado la casilla
			this.fila = fila;
			// introducimos la nueva ficha
			tab.setCasilla(this.columna, this.fila, this.jugador);
		} else {
			String msg = "Columna incorrecta. Debe estar entre 1 y " +
					tab.getAncho() + ".";
			throw new MovimientoInvalido(msg);
		}
	}
	
	// Deshace el último movimiento del tablero.
	public void undo(Tablero tab) {
		/* en el atributo fila ya tenemos la fila donde se insertó la ficha,
		 * por tanto no es necesario buscar la fila donde se insertó
		 */
		if (this.fila == 1 && this.colorUndo != Ficha.VACIA) {
			for (int f = 1; f < tab.getAlto(); f++) {
				tab.setCasilla(this.columna, f, tab.getCasilla(this.columna, f+1));
			}
			tab.setCasilla(this.columna, tab.getAlto(), this.colorUndo);
		} else {
			tab.setCasilla(this.columna, this.fila, this.colorUndo);
		}
	}
	
}