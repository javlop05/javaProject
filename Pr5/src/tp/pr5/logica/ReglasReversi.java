package tp.pr5.logica;

import java.util.ArrayList;

public class ReglasReversi implements ReglasJuego{

	private final TipoJuego juego = TipoJuego.REVERSI;
	
	// Permite averiguar si en la partida ya tenemos un ganador o no.
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		Ficha ganador; 
		
			ganador = Ficha.VACIA;
			
		if (t.getCont(Ficha.NEGRA) == 0) {
			ganador = Ficha.BLANCA;
		} else if (t.getCont(Ficha.BLANCA) == 0) {
			ganador = Ficha.NEGRA;
		} else if ((t.getCont(Ficha.NEGRA) + t.getCont(Ficha.BLANCA))
				== (t.getAlto() * t.getAncho())) {
			if (t.getCont(Ficha.NEGRA) > t.getCont(Ficha.BLANCA))  {
				ganador = Ficha.NEGRA;
			}
			else if (t.getCont(Ficha.BLANCA) > t.getCont(Ficha.NEGRA)) {
				ganador = Ficha.BLANCA;
			}
		} else if (siguienteTurno(ultimoMovimiento.getJugador(), t) == null) {
			if (t.getCont(Ficha.NEGRA) > t.getCont(Ficha.BLANCA))  {
				ganador = Ficha.NEGRA;
			}
			else if (t.getCont(Ficha.BLANCA) > t.getCont(Ficha.NEGRA)) {
				ganador = Ficha.BLANCA;
			} 
		}
		return ganador;
	}

	// Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	public Tablero iniciaTablero() {
		Tablero tab = new Tablero(this.juego.getColsPorDefecto(), 
				this.juego.getFilasPorDefecto());
		tab.setCasilla(4, 4, Ficha.BLANCA);
		tab.setCasilla(5, 5, Ficha.BLANCA);
		tab.setCasilla(5, 4, Ficha.NEGRA);
		tab.setCasilla(4, 5, Ficha.NEGRA);
		calcularMovsPosibles(tab, jugadorInicial());
		return tab;
	}

	// Devuelve el color del jugador que comienza la partida.
	public Ficha jugadorInicial() {
		return Ficha.NEGRA;
	}

	// Devuelve el color del jugador al que le toca poner.
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t) {
		Ficha nextTurn;
		if (ultimoEnPoner == Ficha.BLANCA) nextTurn = Ficha.NEGRA;
		else nextTurn = Ficha.BLANCA;
		if (!calcularMovsPosibles(t, nextTurn).isEmpty()) {}
		else if (!calcularMovsPosibles(t, ultimoEnPoner).isEmpty()) {
			nextTurn = ultimoEnPoner;
		} else {
			nextTurn = null;
		}
		return nextTurn;
	}

	// Devuelve true si, con el estado del tablero dado, la partida ha terminado en tablas. 
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		boolean empate;
		
			empate = false;
		
		if ((t.getCont(Ficha.BLANCA) + t.getCont(Ficha.NEGRA)
				== (t.getAlto() * t.getAncho())) 
				&& t.getCont(Ficha.BLANCA) == t.getCont(Ficha.NEGRA))
			empate = true;
		
		return empate;
	}

	// Devuelve el tipo de juego.
	public TipoJuego getTipoJuego() {
		return this.juego;
	}
	
	// Calcula los movimientos posibles, y devuelve si hay o no movs Posibles. 
	public ArrayList calcularMovsPosibles (Tablero t, Ficha turno) {
		return calcularMovsReversi (t, turno);
	}
	
	//Metodo estatico para que jugadorAleatorio y MovimientoReversi tengan acceso a los movsPosibles
	public static ArrayList<MovimientoReversi> calcularMovsReversi (Tablero t, Ficha turno) {
		ArrayList<MovimientoReversi> movsPosibles = new ArrayList<MovimientoReversi> ();
		Ficha casilla;
		MovimientoReversi mov;
		for (int i = 1; i <= t.getAlto(); i++) {
			for (int j = 1; j <= t.getAncho(); j++) {
				casilla = t.getCasilla(j, i);
				if (casilla == Ficha.VACIA) {
					mov = new MovimientoReversi(j, i, turno);
					if (mov.movValidoReversi(t)) {
						movsPosibles.add(mov);
					}
				}
			}
		}
		return movsPosibles;
	}
}
