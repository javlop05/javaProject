package tp.pr5.control;

import java.util.ArrayList;
import java.util.Random;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoReversi;
import tp.pr5.logica.ReglasReversi;
import tp.pr5.logica.Tablero;

public class JugadorAleatorioReversi extends Jugador{

	public JugadorAleatorioReversi (FactoriaTipoJuego f) {
		this.factoria = f;
	}

	// Devuelve el siguiente movimiento a ejecutar por el jugador aleatorio.
	public Movimiento getMovimiento(Tablero tab, Ficha color) throws java.util.InputMismatchException {
		//Generamos un numero aleatorio entre el rango (0, tamaño del Array - 1)
		Random aleatorio = new Random();
		ArrayList <MovimientoReversi> movsPosibles = 
				ReglasReversi.calcularMovsReversi(tab, color);
		
		int num = aleatorio.nextInt(movsPosibles.size());
		
		// Obtenemos ese movimiento del array de movPosibles
		Movimiento ret = movsPosibles.get(num);
		
		return ret;
	}
}
