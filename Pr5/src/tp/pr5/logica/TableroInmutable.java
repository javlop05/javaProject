package tp.pr5.logica;

public interface TableroInmutable {
	
	// Devuelve el número de filas del tablero.
	public int getAlto();
	
	// Devuelve el número de columnas del tablero.
	public int getAncho();
	
	// Devuelve el color de la ficha situada en la fila y columna dada.
	public Ficha getCasilla(int fila, int col);
	
	// Dibuja el tablero para mostrarlo por pantalla.
	public String toString();
	
	// Devuelve el contador del color pasado como parámetro
	public int getCont (Ficha color);
}