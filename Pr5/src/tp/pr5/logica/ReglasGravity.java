package tp.pr5.logica;

import java.util.ArrayList;

public class ReglasGravity implements ReglasJuego {
	
	private final TipoJuego juego = TipoJuego.GRAVITY;
	
	private int numCols;
	private int numFilas;
	
	public ReglasGravity(int numCols, int numFilas) {
		this.numCols = numCols;
		this.numFilas = numFilas;
	}
	
	// Permite averiguar si en la partida ya tenemos un ganador o no.
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		Ficha ganador;
		
			ganador = Ficha.VACIA;
			
		// miramos si con el último movimiento se generó un grupo	
		if (comprobarGrupo(ultimoMovimiento.getColumna(), ultimoMovimiento.getFila(), t)) 
			ganador = ultimoMovimiento.getJugador();
		
		return ganador;
	}	
	
	// Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	public Tablero iniciaTablero() {
		return new Tablero(this.numCols, this.numFilas);
	}
	
	// Devuelve el color del jugador que comienza la partida.
	public Ficha jugadorInicial() {
		return Ficha.BLANCA;
	}
	
	// Devuelve el color del jugador al que le toca poner.
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha nextTurn;
		if (ultimoEnPoner == Ficha.BLANCA) {
			nextTurn = Ficha.NEGRA;
		} else {
			nextTurn = Ficha.BLANCA;
		}
		return nextTurn;
	}
	
	// Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas.
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		boolean hueco;
		int fila, columna;
		
			columna = 1;
			hueco = false;
		
		while ((columna <= t.getAncho()) && (!hueco)) {
			fila = 1;
			while ((fila <= t.getAlto()) && (!hueco)) {
				hueco = (t.getCasilla(columna, fila) == Ficha.VACIA);
				fila++;
			}
			columna++;
		}
		return (!hueco);
	}
	
	// Verifica si a partir de la posición dada se forma un grupo de fichas.
	private boolean comprobarGrupo(int x, int y, Tablero t) { 
		return (Utilidades.vertical(x, y, t) ||
				Utilidades.horizontal(x, y, t) ||
				Utilidades.diagonal(x, y, t) ||
				Utilidades.diagonal2(x, y, t));
	}

	// Devuelve el tipo de juego.
	public TipoJuego getTipoJuego () {
		return this.juego;
	}

	// Devuelve los movimientos posibles (sugerencias)
	public ArrayList<Movimiento> calcularMovsPosibles(Tablero t, Ficha turno) {
		return new ArrayList<Movimiento> ();
	}
}