package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorHumanoComplica extends Jugador {
	private Scanner sc;

	public JugadorHumanoComplica(Scanner in, FactoriaTipoJuego f) {
		this.sc = in;
		this.factoria = f;
	}
	
	// Devuelve el siguiente movimiento a ejecutar por el jugador humano.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		System.out.print("Introduce la columna: ");
		int columna = this.sc.nextInt();
		this.sc.nextLine();
		// metemos 0 pues la fila no se tiene en cuenta
		return this.factoria.creaMovimiento(columna, 0, color);
	}
}
