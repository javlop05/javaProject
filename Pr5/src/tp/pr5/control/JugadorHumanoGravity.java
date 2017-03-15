package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.Tablero;

public class JugadorHumanoGravity extends Jugador {
	private Scanner sc;
	
	public JugadorHumanoGravity(Scanner in, FactoriaTipoJuego f) {
		this.sc = in;
		this.factoria = f;
	}
	
	// Devuelve el siguiente movimiento a ejecutar por el jugador humano.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		System.out.print("Introduce la columna: ");
		int columna = this.sc.nextInt();
		System.out.print("Introduce la fila: ");
		int fila = this.sc.nextInt();
		this.sc.nextLine();
		return this.factoria.creaMovimiento(columna, fila, color);
	}

}
