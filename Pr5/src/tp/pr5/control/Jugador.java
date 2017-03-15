package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public abstract class Jugador {
	protected FactoriaTipoJuego factoria;
	
	// Devuelve el siguiente movimiento a ejecutar por el jugador.
	public abstract Movimiento getMovimiento(Tablero tab, Ficha color) 
			throws java.util.InputMismatchException;
	
}