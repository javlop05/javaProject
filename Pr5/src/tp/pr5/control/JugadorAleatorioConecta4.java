package tp.pr5.control;

import java.util.Random;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorAleatorioConecta4 extends Jugador {
	
	public JugadorAleatorioConecta4(FactoriaTipoJuego f) {
		this.factoria = f;
	}
	
	// Devuelve el siguiente movimiento a ejecutar por el jugador aleatorio.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		int columna;
		Random aleatorio = new Random();
		
		// generamos un aleatorio hasta que la columna no esté llena
		do {
			columna = aleatorio.nextInt(tab.getAncho()) + 1;
		} while (tab.getCasilla(columna, 1) != Ficha.VACIA);
		
		// la fila no se tiene en cuenta, por lo que ponemos un 0
		return this.factoria.creaMovimiento(columna, 0, color);
	}

}