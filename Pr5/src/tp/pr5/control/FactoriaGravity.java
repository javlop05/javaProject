package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoGravity;
import tp.pr5.logica.ReglasGravity;
import tp.pr5.logica.ReglasJuego;

public class FactoriaGravity implements FactoriaTipoJuego {
	private int numCols;
	private int numFilas;
	
	public FactoriaGravity(int numCols, int numFilas) {
		this.numCols = numCols;
		this.numFilas = numFilas;
	}
	
	// Construye el objeto Jugador capaz de crear movimientos aleatorios.
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioGravity(this);
	}
	
	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in) {
		return new JugadorHumanoGravity(in, this);
	}
	
	// Construye un movimiento para el Gravity.
	public Movimiento creaMovimiento(int col, int fil, Ficha color) {
		return new MovimientoGravity (col, fil, color);
	}
	
	// Construye las reglas del Gravity.
	public ReglasJuego creaReglas() {
		return new ReglasGravity(this.numCols, this.numFilas);
	}
}
