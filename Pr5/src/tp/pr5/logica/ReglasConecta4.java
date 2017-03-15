package tp.pr5.logica;

import java.util.ArrayList;

public class ReglasConecta4 implements ReglasJuego {
		
	private final TipoJuego juego = TipoJuego.CONECTA4;
	
	// Permite averiguar si en la partida ya tenemos un ganador o no.
 	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		Ficha ganador;
		
			ganador = Ficha.VACIA;
		
		// comprobamos si se ha formado un grupo de fichas
		if (comprobarGrupo(ultimoMovimiento.getColumna(), ultimoMovimiento.getFila(), t))
			ganador = ultimoMovimiento.getJugador();
		
		return ganador;
	}	

 	// Construye el tablero que hay que utilizar para la partida, segun las reglas del juego.
	public Tablero iniciaTablero() {
		return new Tablero(this.juego.getColsPorDefecto(), 
				this.juego.getFilasPorDefecto());
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
		boolean lleno;
		int fila, columna;
		fila = 1; // miraremos la fila superior
		columna = 1;
		lleno = false;
		
		while ((columna <= t.getAncho()) && 
				(t.getCasilla(columna, fila) != Ficha.VACIA)) {
			columna++;
		}
		/* si hemos llegado hasta el final de la fila superior y 
		 * todas estaban llenas, lleno = true
		 */
		if (columna > t.getAncho()) {
			lleno = true;
		}
		return lleno;
	}
	
	// Verifica si a partir de la posición dada se forma un grupo de fichas.
	private boolean comprobarGrupo(int x, int y, Tablero t) { 
		return (Utilidades.vertical(x, y, t) ||
				Utilidades.horizontal(x, y, t) ||
				Utilidades.diagonal(x, y, t) ||
				Utilidades.diagonal2(x, y, t));
	}
	
	//Devuelve el tipo de juego que se corresponde con estas reglas
	public TipoJuego getTipoJuego () {
		return this.juego;
	}
	
	// Devuelve los movimientos posibles (sugerencias)
	public ArrayList<Movimiento> calcularMovsPosibles(Tablero t, Ficha turno) {
		return new ArrayList<Movimiento> ();
	}
}