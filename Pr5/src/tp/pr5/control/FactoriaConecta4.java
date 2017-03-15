package tp.pr5.control;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoConecta4;
import tp.pr5.logica.ReglasConecta4;
import tp.pr5.logica.ReglasJuego;

public class FactoriaConecta4 implements FactoriaTipoJuego {
	
	// Construye el objeto Jugador capaz de crear movimientos aleatorios.
	public Jugador creaJugadorAleatorio() {
		return new JugadorAleatorioConecta4(this);
	}

	/* Construye el objeto Jugador encargado de pedir al usuario por consola
	   el siguiente movimiento a realizar. */
	public Jugador creaJugadorHumanoConsola(java.util.Scanner in) {
		return new JugadorHumanoConecta4(in,this);
	}
	
	// Construye un movimiento para el Conecta 4.
	public Movimiento creaMovimiento(int col, int fil, Ficha color) {
		return new MovimientoConecta4(col, color);
	}
	 
	// Construye las reglas del Conecta 4.
	public ReglasJuego creaReglas() {
		return new ReglasConecta4();
	}

}
