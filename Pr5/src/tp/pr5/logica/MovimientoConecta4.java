package tp.pr5.logica;

public class MovimientoConecta4 extends Movimiento {
	
	public MovimientoConecta4(int donde, Ficha color) {
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
			if (tab.getCasilla(this.columna, fila) != Ficha.VACIA) {
				throw new MovimientoInvalido("Columna llena.");
			} else {
				// dejamos caer la ficha
				while (fila < tab.getAlto() && !huecoEncontrado) {
					if (tab.getCasilla(this.columna, fila + 1) == Ficha.VACIA) {
						fila++;
					} else {
						huecoEncontrado = true;
					}
				}
				this.fila = fila;
				tab.setCasilla(this.columna, this.fila, this.jugador);
			}
		} else {
			String msg = "Columna incorrecta. Debe estar entre 1 y " + 
							tab.getAncho() + ".";
			throw new MovimientoInvalido(msg);
		}
	}
	
	// Deshace el último movimiento del tablero.
	public void undo(Tablero tab) {
		/* En fila tenemos la fila donde se insertó la ficha
		 * colorUndo en conecta4 siempre valdrá vacía
		 * ya que en una columna llena no se puede insertar
		 * una nueva ficha */
		tab.setCasilla(this.columna, this.fila, this.colorUndo);
	}
	
}