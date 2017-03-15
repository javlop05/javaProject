package tp.pr5.logica;

import java.util.ArrayList;

import tp.pr5.Vista.Observador;
import tp.pr5.control.Jugador;
import tp.pr5.control.ModoJugador;

public class Partida {
	public static int MAXUNDO = 10;
	
	private Movimiento undoStack[];
	private int numUndo; // contador de movimientos almacenados en el buffer
	private int start;
	private int end;
	
	private ReglasJuego reglasJuego;
	private Tablero tablero;
	private Ficha turno;
	private boolean terminada;
	private Ficha ganador;
	
	private ArrayList<Observador> obs;
	
	// Crea una partida nueva.
	public Partida(ReglasJuego reglas) {
		this.reglasJuego = reglas;
		this.undoStack = new Movimiento [MAXUNDO];
		this.numUndo = 0;
		this.start = 0;
		this.end = 0;
		this.tablero = reglasJuego.iniciaTablero();
		this.turno = reglasJuego.jugadorInicial();
		this.terminada = false;
		this.ganador = Ficha.VACIA;
		this.obs = new ArrayList<Observador>();
	}
	
	// Reinicia la partida creada.
	private void reset(ReglasJuego reglas) {
		this.reglasJuego = reglas;
		// borramos la información del tablero
		this.tablero = reglasJuego.iniciaTablero();
		// vuelven a empezar blancas
		this.turno = reglasJuego.jugadorInicial();
		// vaciamos el buffer de movimientos
		this.numUndo = 0;
		this.start = 0;
		this.end = 0;
		this.terminada = false;
		this.ganador = Ficha.VACIA;
	}
	
	// Reset del mismo tipo de juego, notifica con onReset
	public void reset() {
		reset(this.reglasJuego);
		for (Observador o: this.obs)
			o.onReset(this.tablero, this.turno,
					this.reglasJuego.calcularMovsPosibles(this.tablero, this.turno));
	}
	
	//Reset cambiando el tipo de juego, notifica con onCambioJuego
	public void cambiarJuego(ReglasJuego r) {
		reset(r);
		for (Observador o: this.obs)
			o.onCambioJuego(this.tablero, this.turno, this.reglasJuego.getTipoJuego(), 
					this.reglasJuego.calcularMovsPosibles(this.tablero, this.turno));
	}
	
	// Realiza el movimiento indicado comprobando si es válido.
	public void ejecutaMovimiento(Movimiento mov) {
		boolean empate;
		Ficha ganador;
			
		if (!isTerminada() && this.turno == mov.getJugador()) {
			// puede generarse una excepcion
			try {
				mov.ejecutaMovimiento(this.tablero); 
				
				// si ejecutaMovimiento no lanza una excepción
				almacenarNewMov(mov);
				ganador = this.reglasJuego.hayGanador (mov, this.tablero);
				empate = this.reglasJuego.tablas(mov.getJugador(), this.tablero);
				
				if (!empate && ganador != Ficha.VACIA) {
					this.ganador = ganador;
					this.terminada = true;
					//notificamos partida acabada
					for (Observador o: this.obs)
						o.onPartidaTerminada(this.tablero, ganador);
					detenerPartida();
				} else if (empate) {
					this.terminada = true;
					//notificamos partida acabada
					for (Observador o: this.obs)
						o.onPartidaTerminada(this.tablero, ganador);
					detenerPartida();
				} else {
					this.turno = this.reglasJuego.siguienteTurno(this.turno, this.tablero);
					//notificamos movimiento realizado
					for (Observador o: this.obs)
						o.onMovimientoEnd(this.tablero, this.turno,
								this.reglasJuego.calcularMovsPosibles(this.tablero, this.turno));
					continuarPartida();
				}
			} catch (MovimientoInvalido movInvalido) { 
				notificaMovimientoInvalido(movInvalido);
			}
			
		} else if (isTerminada()) {
			MovimientoInvalido movInvalido = 
					new MovimientoInvalido("Partida terminada no se puede poner");
			notificaMovimientoInvalido(movInvalido);
			
		} else if (this.turno != mov.getJugador()) {
			MovimientoInvalido movInvalido = 
					new MovimientoInvalido("No es el turno de la ficha que esta intentando poner");
			notificaMovimientoInvalido(movInvalido);
		}
	}
	
	// Almacena en el buffer el último movimiento válido realizado.
	public void almacenarNewMov(Movimiento mov) {
		// calculamos la posición donde insertaremos el nuevo movimiento
		if (this.end == MAXUNDO - 1) {
			this.end = 0;
		} else {
			this.end++;
		}
		
		// si nuestro buffer no esta lleno, aumentamos el contador
		if (this.numUndo < MAXUNDO) {
			this.numUndo++;
		}	
		/* cuando queramos guardar un movimiento, si end coincide con start,
		 * es porque hemos dado la vuelta al buffer y vamos a 
		 * sobrescribir. End y start no pueden coincidir a no ser que el buffer
		 * este vacío  */
		else if (this.end == this.start) {
			this.start++;
		}
		
		this.undoStack[this.end] = mov;
	}
	
	// Deshace el último movimiento si es posible.
	public void undo () {
		boolean deshacer, hayMas;
		Movimiento mov;
		
			deshacer = false;
		
		if (this.numUndo > 0) {
			mov = this.undoStack[end];
			// eliminamos el movimiento del buffer
			this.numUndo--;
			if (this.end == 0) {
				this.end = MAXUNDO - 1;
			} else {
				this.end--;
			}
			mov.undo(this.tablero);
			deshacer = true;
			this.turno = this.reglasJuego.siguienteTurno(this.turno, this.tablero);
		}
		if (deshacer) {
			hayMas = (this.numUndo > 0);
			for (Observador o: this.obs)
				o.onUndo(this.tablero, this.turno, hayMas, 
						this.reglasJuego.calcularMovsPosibles(this.tablero, this.turno));
		} else 
			for (Observador o: this.obs)
				o.onUndoNotPossible(this.tablero, this.turno);
	}
	
	// Devuelve el turno del jugador al que le toca poner.
	public Ficha getTurno() {
		return this.turno;
	}
	
	// Devuelve el ganador de la partida.
	public Ficha getGanador() {
		return this.ganador;
	}
	
	// Devuelve si la partida ha concluido o no.
	public boolean isTerminada() {
		return this.terminada;
	}
	
	// Devuelve el tablero de la partida.
	public Tablero getTablero() {
		return this.tablero;
	}
	
	// Devuelve el movimiento del jugador dado.
	public Movimiento obtenerMovimiento(Jugador jugador) {
		Movimiento mov = null;
		try  {
			mov = jugador.getMovimiento(this.tablero, this.turno);
		} catch (java.util.InputMismatchException e) {
			MovimientoInvalido movInvalido = 
					new MovimientoInvalido ("La columna y la fila deben ser numeros.\n");
			notificaMovimientoInvalido(movInvalido);
		}
		return mov;
	}
	
	// Añade un nuevo observador.
	public void addObservador(Observador o) {
		this.obs.add(o);
	}

	// Notifica a la vista que la partida ha comenzado (se ha iniciado la app).
	public void comienzaPartida() {
		for (Observador o: obs) {
			o.onComienzaPartida(this.tablero, this.turno, this.reglasJuego.getTipoJuego(),
					this.reglasJuego.calcularMovsPosibles(this.tablero, this.turno));
		}
	}
	
	// Notifica a la vista que hubo un movIncorrecto
	private void notificaMovimientoInvalido (MovimientoInvalido movInv) {
		for (Observador o: this.obs) {
			o.onMovimientoIncorrecto(movInv);
		}
	}
	
	// Detiene la partida (usado para las hebras)
	public void detenerPartida() {
		this.turno.getModoJuego().terminar();
	}
	
	// Continua la partida (usado para las hebras) 
	public void continuarPartida() {
		if (this.terminada != true) {
			
			for (Observador o: this.obs)
				o.onMovimientoStart(this.tablero, this.turno);
			
			this.turno.getModoJuego().comenzar();
		}
	}
}