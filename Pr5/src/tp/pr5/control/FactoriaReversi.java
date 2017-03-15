package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoReversi;
import tp.pr5.logica.ReglasJuego;
import tp.pr5.logica.ReglasReversi;

public class FactoriaReversi implements FactoriaTipoJuego {

	// Construye el objeto Jugador capaz de crear movimientos aleatorios.
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioReversi(this);
	}

	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	public Jugador creaJugadorHumanoConsola(Scanner in) {
		return new JugadorHumanoReversi(in, this);
	}

	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	public Movimiento creaMovimiento(int col, int fil, Ficha color) {
		return new MovimientoReversi (col, fil, color);
	}

	// Construye un movimiento para el Gravity.
	public ReglasJuego creaReglas() {
		return new ReglasReversi();
	}

}
