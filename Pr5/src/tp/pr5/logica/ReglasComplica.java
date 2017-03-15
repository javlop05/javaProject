package tp.pr5.logica;

import java.util.ArrayList;

public class ReglasComplica implements ReglasJuego {
	
	private final TipoJuego juego = TipoJuego.COMPLICA;
	
	// Permite averiguar si en la partida ya tenemos un ganador o no.
	public Ficha hayGanador(Movimiento ultimoMovimiento, Tablero t) {
		boolean grupoBlancas, grupoNegras;
		int altura, anchura, fila, columna;
		Ficha ficha, ganador;
		
			grupoBlancas = false; grupoNegras = false;
			altura = t.getAlto();
			anchura = t.getAncho();
			fila = 1; columna = ultimoMovimiento.getColumna();
			ganador = Ficha.VACIA;
		
		// buscamos si hay grupos de blancas y grupos de negras
		while (columna <= anchura && (!grupoBlancas || !grupoNegras)) {
			fila = 1;
			while (fila <= altura && (!grupoBlancas || !grupoNegras)) {
				ficha = t.getCasilla(columna, fila);
				if ((ficha == Ficha.BLANCA) && !grupoBlancas) 
					grupoBlancas = comprobarGrupo(columna, fila, t);
				else if ((ficha == Ficha.NEGRA) && !grupoNegras)
					grupoNegras = comprobarGrupo(columna, fila, t);
				fila++;
			}
			columna++;
		}
		
		// determinamos si hay un único grupo de fichas, y por tanto, un ganador
		if (grupoBlancas && !grupoNegras) ganador = Ficha.BLANCA;
		else if (!grupoBlancas && grupoNegras) ganador = Ficha.NEGRA;
		
		return ganador;
	}
	
	// Construye el tablero que hay que utilizar para la partida, según las reglas del juego.
	public Tablero iniciaTablero() {
		return new Tablero (this.juego.getColsPorDefecto(),
				this.juego.getFilasPorDefecto());
	}
	
	// Devuelve el color del jugador que comienza la partida.
	public Ficha jugadorInicial() {
		return Ficha.BLANCA;
	}
	
	// Devuelve el color del jugador al que le toca poner.
	public Ficha siguienteTurno(Ficha ultimoEnPoner, Tablero t){
		Ficha nextTurn;
		if (ultimoEnPoner == Ficha.BLANCA) {
			nextTurn = Ficha.NEGRA;
		} else {
			nextTurn = Ficha.BLANCA;
		}
		return nextTurn;
	}
	
	// En el Complica, devuelve siempre false debido a que la partida no finaliza en tablas.
	public boolean tablas(Ficha ultimoEnPoner, Tablero t) {
		return false;
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