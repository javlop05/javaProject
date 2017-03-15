package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.ReglasJuego;

public interface FactoriaTipoJuego {

	// Construye el objeto Jugador capaz de crear movimientos aleatorios.
	abstract Jugador creaJugadorAleatorio();
	
	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	abstract Jugador creaJugadorHumanoConsola(java.util.Scanner in);
	
	// Construye un movimiento para el juego concreto.
	abstract Movimiento creaMovimiento(int col, int fil, Ficha color);
	
	// Construye las reglas del juego concreto.
	abstract ReglasJuego creaReglas();
	
}