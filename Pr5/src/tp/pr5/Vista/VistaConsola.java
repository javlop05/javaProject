package tp.pr5.Vista;

import java.util.ArrayList;
import java.util.Scanner;

import tp.pr5.control.ControladorConsola;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.MovimientoReversi;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;

public class VistaConsola implements Observador {
	
	private ControladorConsola controlConsola;
	private Scanner in;

	public VistaConsola (ControladorConsola c, Scanner in) {
		this.controlConsola = c;
		this.in = in;
		this.controlConsola.addObservador(this);
	}

	// Pone en funcionamiento la partida creada.
	public void run() {
		String opcion;
		boolean exit;

			exit = false;

		this.controlConsola.comienzaPartida();

		// Mientras que el usuario no haya salido o no se haya acabado
		while ((!exit) && !this.controlConsola.finalizada()) {

			System.out.print("Qué quieres hacer? ");	
			opcion = this.in.nextLine();
			String arrayStr[] = opcion.split(" ");

			if (opcion.equalsIgnoreCase("salir")) {
				exit = true;

			} else if (opcion.equalsIgnoreCase("poner")) {
				this.controlConsola.poner();
				
			} else if (opcion.equalsIgnoreCase("deshacer")) {
				this.controlConsola.undo();
				
			} else if (opcion.equalsIgnoreCase("reiniciar")) {
				this.controlConsola.reset();

			} else if (arrayStr[0].equalsIgnoreCase("Jugar")
					&& (arrayStr.length == 2 || arrayStr.length == 4)) {
				int col, fil;
				boolean juegoCorrecto;
				
					col = 0;
					fil = 0;
					juegoCorrecto = false;
						 
				if (arrayStr[1].equalsIgnoreCase("gr") && arrayStr.length == 4) {
					try {
						col = Integer.parseInt(arrayStr[2]);
						fil = Integer.parseInt(arrayStr[3]);
						juegoCorrecto = true;
					} catch (java.lang.NumberFormatException e) {
						System.err.print("Las dimensiones deben ser numericas.\n");
					}	
				} else if ((arrayStr[1].equalsIgnoreCase("co") || 
							arrayStr[1].equalsIgnoreCase("c4") ||
							arrayStr[1].equalsIgnoreCase("rv")) &&
							arrayStr.length == 2) {
					juegoCorrecto = true;
				} 
				
				if (juegoCorrecto) {
					this.controlConsola.cambiarJuego(arrayStr[1], col, fil);
				} else {
					System.err.print("No te entiendo.\n");
				}

			} else if (arrayStr[0].equalsIgnoreCase("Jugador")
					&& (arrayStr.length == 3)
					&& (arrayStr[1].equalsIgnoreCase("blancas")|| 
							arrayStr[1].equalsIgnoreCase("negras")) 
					&& (arrayStr[2].equalsIgnoreCase("humano")|| 
							arrayStr[2].equalsIgnoreCase("aleatorio"))) {
				//Ya nos hemos asegurado que el comando es correcto
				this.controlConsola.cambiarJugador(arrayStr[1], arrayStr[2]);

			} else if (opcion.equalsIgnoreCase("Ayuda")) {
				mostrarAyuda();

			} else {
				System.err.print("No te entiendo.\n");
			}
		}
	}

	// Muestra por pantalla las instrucciones del juego.
	private void mostrarAyuda() {
		System.out.print("Los comandos disponibles son:\n\n");
		System.out.print("PONER: utilízalo para poner la siguiente ficha.\n");
		System.out.print("DESHACER: deshace el último movimiento hecho en la partida.\n");
		System.out.print("REINICIAR: reinicia la partida.\n");
		System.out.print("JUGAR [c4|co|gr|rv] [tamX tamY]: cambia el tipo de juego.\n");
		System.out.print("JUGADOR [blancas|negras] [humano|aleatorio]: cambia el tipo de jugador.\n");
		System.out.print("SALIR: termina la aplicación.\n");
		System.out.print("AYUDA: muestra esta ayuda.\n\n");
	}

	// Notifica que se ha reiniciado la partida, mostrando el tablero inicial y el turno.
	public void onReset(TableroInmutable tab, Ficha turno, ArrayList<Movimiento> movsPosibles) {
		System.out.print("Partida reiniciada.\n");
		System.out.print(tab.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");
	}

	// Notifica que ha terminado la partida, mostrando el tablero y el ganador.
	public void onPartidaTerminada(TableroInmutable tablero, Ficha ganador) {
		System.out.println(tablero.toString() + "\n");
		if (ganador != Ficha.VACIA) {
			System.out.print("Ganan las " + 
					ganador.colorDeFicha() + "\n");
		} else {
			System.out.print("Partida terminada en tablas.\n");
		}
		// El controlador finaliza la partida
		this.controlConsola.finaliza();
	}

	// Notifica que se ha cambiado el juego, proporcionando el tablero inicial y el turno.
	public void onCambioJuego(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles) {
		System.out.print("Partida reiniciada.\n");
		System.out.print(tab.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");
	}

	// Notifica que no se ha podido deshacer el movimiento, mostrando el tablero y el turno.
	public void onUndoNotPossible(TableroInmutable tablero, Ficha turno) {
		System.err.print("Imposible deshacer.\n");
		System.out.print(tablero.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");
	}

	// Notifica que se ha deshecho un movimiento, mostando el tablero y el turno.
	public void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas, ArrayList<Movimiento> movsPosibles) {
		System.out.print(tablero.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");
	}

	// Notifica que se ha terminado de realizar un movimiento, mostando el tablero y el turno.
	public void onMovimientoEnd(TableroInmutable tablero, Ficha turno, ArrayList<Movimiento> movsPosibles) {
		System.out.print(tablero.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");		
	}

	// Notifica que se ha intentado realizar un movimiento incorrecto.
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		String mensaje = movimientoException.getMessage();
		System.err.print(mensaje);
	}

	// Notifica que ha empezado una nueva partida, mostrando el tablero y el turno.
	public void onComienzaPartida(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles) {
		System.out.print(tab.toString() + "\n");
		System.out.print("Juegan " + 
				turno.colorDeFicha() + "\n");
	}

	// No requiere implementación para el modo consola
	public void onMovimientoStart(TableroInmutable tab, Ficha turno) {}
	
}