package tp.pr5.control;

import tp.pr5.Vista.Observador;
import tp.pr5.logica.*;

public class ControladorGUI {
	private Partida partida;
	private FactoriaTipoJuego factoria;
	
	// Controla la partida y el scanner de la opción.
	public ControladorGUI(FactoriaTipoJuego f, Partida p) {
		this.partida = p;
		this.factoria = f;
		Utilidades.inicializarFichas();
	}
	
	// Llama al comienzaPartida de partida.
	public void comienzaPartida() {
		this.partida.comienzaPartida();
	}
	
	// Llama al reset de partida.
	public void reset() {
		this.partida.detenerPartida();
		this.partida.reset();
		this.partida.continuarPartida();
	}
		
	// Llama al deshacer de partida.
	public void undo() {
		this.partida.detenerPartida();
		this.partida.undo();
		this.partida.continuarPartida();
	}
	
	// Crea un movimiento aleatorio y llama a ejecutaMovimiento de partida.
	public void ponerAleatorio() {
		Jugador jugadorAleatorio = this.factoria.creaJugadorAleatorio();
		Movimiento mov = this.partida.obtenerMovimiento(jugadorAleatorio);
		this.partida.ejecutaMovimiento(mov);
	}
	
	// Crea el movimiento y llama al ejecutaMovimiento de partida.
	public void poner(int fila, int columna) { 
		Movimiento mov = this.factoria.creaMovimiento(columna, fila, this.partida.getTurno());
		this.partida.ejecutaMovimiento(mov);
	}
	
	// Crea las reglas del juego y llama a cambiarJuego de partida.
	public void cambiarJuego(TipoJuego t, int filas, int cols) {
		this.partida.detenerPartida();
		ReglasJuego reglas;
		
		if (t == TipoJuego.CONECTA4) {
			reglas = new ReglasConecta4();
			this.factoria = new FactoriaConecta4();
		} else if (t == TipoJuego.COMPLICA) {
			reglas = new ReglasComplica();
			this.factoria = new FactoriaComplica();
		} else if (t == TipoJuego.GRAVITY){
			reglas = new ReglasGravity(cols, filas);
			this.factoria = new FactoriaGravity(cols, filas);
		} else {
			reglas = new ReglasReversi();
			this.factoria = new FactoriaReversi();
		}
		
		this.partida.cambiarJuego(reglas);
		this.partida.continuarPartida();
	}
	
	// Llama a la partida para añadir un observador.
	public void addObservador(Observador o) {
		this.partida.addObservador(o);
	}
	
	// Cambia el modoJuego y el TipoTurno de la ficha recibida
	public void cambiarJugador(TipoTurno tipoTurno, Ficha ficha) {
		this.partida.detenerPartida();
		ficha.setTipoTurno(tipoTurno);
		if (tipoTurno == TipoTurno.HUMANO) {
			ficha.setModoJuego(new ModoHumano());
		} else {
			ficha.setModoJuego(new ModoAutomatico(this));
		}
		this.partida.continuarPartida();
	}
}