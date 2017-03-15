package tp.pr5.logica;

public abstract class Movimiento {
	protected Ficha jugador;
	protected int columna;
	protected int fila;
	protected Ficha colorUndo;
	
	// Ejecuta el movimiento sobre el tablero que se recibe como parámetro.
	public abstract void ejecutaMovimiento(Tablero tab) throws MovimientoInvalido; 
	
	// Devuelve el jugador al que pertenece el turno.
	public Ficha getJugador() {
		return this.jugador;
	}
	
	// Devuelve la columna en la que se ha elegido poner.
	public int getColumna() {
		return this.columna;
	}
	
	// Devuelve la fila.
	public int getFila() {
		return this.fila;
	}
	
	// Devuelve la ficha que se ha perdido al realizar el mov (desplazándola).
	public Ficha getColorUndo() {
		return this.colorUndo;
	}
	
	// Deshace el último movimiento del tablero.
	public abstract void undo(Tablero tab);
	
}