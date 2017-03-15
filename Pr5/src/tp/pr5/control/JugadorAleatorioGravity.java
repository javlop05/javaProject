package tp.pr5.control;

import java.util.Random;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorAleatorioGravity extends Jugador {
	
	public JugadorAleatorioGravity(FactoriaTipoJuego f) {
		this.factoria = f;
	}

	// Devuelve el siguiente movimiento a ejecutar por el jugador aleatorio.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		int columna, fila;
		Random aleatorio = new Random();
		
		do {
			columna = aleatorio.nextInt(tab.getAncho()) + 1;
			fila = aleatorio.nextInt(tab.getAlto()) + 1;
		} while (tab.getCasilla(columna, fila) != Ficha.VACIA);
			
		return this.factoria.creaMovimiento(columna, fila, color);
	}
}
