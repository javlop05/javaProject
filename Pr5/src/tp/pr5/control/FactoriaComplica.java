package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoComplica;
import tp.pr5.logica.ReglasComplica;
import tp.pr5.logica.ReglasJuego;

public class FactoriaComplica implements FactoriaTipoJuego {

	// Construye el objeto Jugador capaz de crear movimientos aleatorios.
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioComplica(this);
	}
	
	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in) {
		return new JugadorHumanoComplica(in, this);
	}
	
	// Construye un movimiento para el Complica.
	public Movimiento creaMovimiento(int col, int fil, Ficha color) {
		return new MovimientoComplica (col, color);
	}
	
	// Construye las reglas del Complica.
	public ReglasJuego creaReglas() {
		return new ReglasComplica();
	}
	
}
