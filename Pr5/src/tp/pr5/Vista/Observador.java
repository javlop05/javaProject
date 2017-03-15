package tp.pr5.Vista;

import java.util.ArrayList;

import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;

public interface Observador {
	
	/* Notifica que ha empezado una nueva partida, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	void onComienzaPartida(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles);
	
	// Notifica que se ha reiniciado la partida, mostrando el tablero inicial y el turno.
	void onReset(TableroInmutable tab, Ficha turno, ArrayList<Movimiento> movsPosibles);
	
	// Notifica que ha terminado la partida, mostrando el tablero y el ganador.
	void onPartidaTerminada(TableroInmutable tablero, Ficha ganador);
	
	/* Notifica que se ha cambiado el juego, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	void onCambioJuego(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles);
	
	/* (Uso solo en consola)
	 * Notifica que no se ha podido deshacer el movimiento, mostrando el tablero y el turno.
	 * */
	void onUndoNotPossible(TableroInmutable tablero, Ficha turno);
	
	// Notifica que se ha deshecho un movimiento, mostando el tablero y el turno.
	void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas, ArrayList<Movimiento> movsPosibles);
	
	// Notifica que se ha terminado de realizar un movimiento, mostrando el tablero y el turno
	void onMovimientoEnd(TableroInmutable tablero, Ficha turno, ArrayList<Movimiento> movsPosibles);
	
	// Notifica que se ha intentado realizar un movimiento incorrecto.
	void onMovimientoIncorrecto(MovimientoInvalido movimientoException);
	
	// Prepara a la interfaz para realizar un movimiento
	void onMovimientoStart(TableroInmutable tab, Ficha turno);
}