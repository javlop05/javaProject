package tp.pr5.logica;

// Usado para el arrayList de casillas volteadas (MovimientoReversi)
public class Casilla {
	private int columna;
	private int fila;
	
	public Casilla (int columna, int fila) {
		this.columna = columna;
		this.fila = fila;
	}
	
	public int getColumna() {
		return this.columna;
	}
	
	public int getFila () {
		return this.fila;
	}

}
