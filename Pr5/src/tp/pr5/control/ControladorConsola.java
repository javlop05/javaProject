package tp.pr5.control;

import java.util.Scanner;

import tp.pr5.Vista.Observador;
import tp.pr5.logica.*;

public class ControladorConsola {
	private Partida partida;
	private Scanner in;
	private FactoriaTipoJuego factoria;
	private Jugador jugador1;
	private Jugador jugador2;
	private boolean partidaAcabada;
	
	// Controla la partida y el scanner de la opción.
	public ControladorConsola (FactoriaTipoJuego f, Partida p, java.util.Scanner in) {
		this.partida = p;
		this.in = in;
		this.factoria = f;
		this.jugador1 = this.factoria.creaJugadorHumanoConsola(this.in);
		this.jugador2 = this.factoria.creaJugadorHumanoConsola(this.in);
		this.partidaAcabada = false;
		Utilidades.inicializarFichas();
	}
	
	// Llama al reset de partida, establece jugadores a humanos y partidaAcabada a false
	public void reset() {
		this.partida.reset();
		this.jugador1 = this.factoria.creaJugadorHumanoConsola(this.in);
		this.jugador2 = this.factoria.creaJugadorHumanoConsola(this.in);
		this.partidaAcabada = false;
	}
	
	// Llama al undo de partida.
	public void undo() {
		this.partida.undo();
	}
	
	// Cambia el tipo de jugador (humano o aleatorio).
	public void cambiarJugador(String color, String tipoJugador) {
		if (color.equalsIgnoreCase("blancas") && tipoJugador.equalsIgnoreCase("humano")) {
			this.jugador1 = this.factoria.creaJugadorHumanoConsola(this.in);
		} else if (color.equalsIgnoreCase("blancas") && tipoJugador.equalsIgnoreCase("aleatorio")) {
			this.jugador1 = this.factoria.creaJugadorAleatorio();
		} else if (color.equalsIgnoreCase("negras") && tipoJugador.equals("humano")) {
			this.jugador2 = this.factoria.creaJugadorHumanoConsola(this.in);
		} else {
			this.jugador2 = this.factoria.creaJugadorAleatorio();
		} 
	}
	
	// Llama al ejecutaMovimiento de partida.
	public void poner() {
		Movimiento mov;
		if (this.partida.getTurno() == Ficha.BLANCA)
			// obtenerMovimiento puede lanzar excepción 
			mov = this.partida.obtenerMovimiento(jugador1);
		else 
			mov = this.partida.obtenerMovimiento(jugador2);
		
		if (mov != null)
			this.partida.ejecutaMovimiento(mov);
		else 
			// Vaciamos el buffer
			in.nextLine();
	}
	
	/* Primero cambiamos la factoria y después hacemos
	 * un reset con la factoria cambiada, reseteamos
	 * la partida y ponemos los jugadores a humanos. */
	public void cambiarJuego(String juego, int col, int fil) {
		if (juego.equalsIgnoreCase("gr")) {
			this.factoria = new FactoriaGravity(col, fil);
		} else if (juego.equalsIgnoreCase("c4")) {
			this.factoria = new FactoriaConecta4();
		} else if (juego.equalsIgnoreCase("rv")) {
			this.factoria = new FactoriaReversi();
		} else {
			this.factoria = new FactoriaComplica();
		} 
		this.partida.cambiarJuego(this.factoria.creaReglas());
		this.jugador1 = this.factoria.creaJugadorHumanoConsola(this.in);
		this.jugador2 = this.factoria.creaJugadorHumanoConsola(this.in);
	}
	
	// Devuelve si la partida está acabada o no.
	public boolean finalizada() {
		return this.partidaAcabada;
	}
	
	// Pone la partida a acabada.
	public void finaliza() {
		this.partidaAcabada = true;
	}
	
	// Llama a la partida para añadir un observador.
	public void addObservador(Observador o) {
		this.partida.addObservador(o);
	}
	
	// Llama a la partida para comenzar una nueva partida.
	public void comienzaPartida() {
		this.partida.comienzaPartida();
	}
	
}