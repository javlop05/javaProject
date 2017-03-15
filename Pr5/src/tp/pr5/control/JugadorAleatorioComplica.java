package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;
import java.util.Random;

public class JugadorAleatorioComplica extends Jugador {
	
	public JugadorAleatorioComplica(FactoriaTipoJuego f) {
		this.factoria = f;
	}

	// Devuelve el siguiente movimiento a ejecutar por el jugador aleatorio.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		int columna;
		Random aleatorio = new Random();
		
			columna = aleatorio.nextInt(tab.getAncho()) + 1;
			
		// la fila no se tiene en cuenta, por lo que ponemos un 0
		return this.factoria.creaMovimiento(columna, 0, color);
	}
	
}