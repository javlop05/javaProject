package tp.pr5.logica;

import java.util.ArrayList;

public interface ReglasJuego {

	// Permite averiguar si en la partida ya tenemos un ganador o no.
	abstract Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t);
	
	// Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	abstract Tablero iniciaTablero();
	
	// Devuelve el color del jugador que comienza la partida.
	abstract Ficha jugadorInicial();
	
	// Devuelve el color del jugador al que le toca poner.
	abstract Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t);

	// Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas. 
	abstract boolean tablas(Ficha ultimoEnPoner, Tablero t);

	// Devuelve el tipo de juego.
	abstract TipoJuego getTipoJuego();
	
	// Devuelve los movimientos posibles (sugerencias)
	abstract ArrayList calcularMovsPosibles(Tablero t, Ficha turno);
}

