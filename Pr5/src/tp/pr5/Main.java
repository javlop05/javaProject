package tp.pr5;
import java.util.Scanner;

import org.apache.commons.cli.*;

import tp.pr5.logica.*;
import tp.pr5.Vista.VistaConsola;
import tp.pr5.Vista.VistaGUI;
import tp.pr5.control.*;

public class Main {
	private static String modo = "view";
	
	public static void main(java.lang.String[] args) {
		FactoriaTipoJuego f;
		ReglasJuego reglas;
		Partida partida;
			
			f = AnalisisArgs(args);
			reglas = f.creaReglas();
			partida = new Partida(reglas);
		
		if (modo.equals("console")) {
			Scanner in = new Scanner(System.in);
			ControladorConsola controlConsola = new ControladorConsola(f, partida, in);
			VistaConsola vistaConsola = new VistaConsola(controlConsola, in);
			vistaConsola.run();
			in.close();
			System.exit(0);
		} else {
			ControladorGUI c = new ControladorGUI(f, partida); 
			VistaGUI vistaGrafica = new VistaGUI(c);
		}	
	}
	
	
	@SuppressWarnings("static-access")
	private static Options configurarOpciones () {
		// FASE 1: Configuramos las opciones de validación de entrada.
		Options options = new Options();

		options.addOption (OptionBuilder.withLongOpt("game")
				.withDescription("Tipo de juego "
						+ "(c4, co, gr, rv)."
						+ " Por defecto, c4.")
						.hasArg()
						.withArgName("game")
						.create("g"));

		options.addOption (OptionBuilder.withLongOpt("help")
				.withDescription("Muestra esta ayuda.")
				.create("h"));

		options.addOption (OptionBuilder.withLongOpt("ui")
				.withDescription("Tipo de interfaz (console"
						+ ", window). Por defecto, console.")
						.hasArg()
						.withArgName("tipo")
						.create("u"));
		
		options.addOption (OptionBuilder.withLongOpt("tamX")
				.withDescription("Número de columnas del"
						+ " tablero (sólo para Gravity). "
						+ "Por defecto, 10." )
						.hasArg()
						.withArgName("columnNumber")
						.create("x"));

		options.addOption (OptionBuilder.withLongOpt("tamY")
				.withDescription("Número de filas del "
						+ "tablero (sólo para Gravity). "
						+ "Por defecto, 10." )
						.hasArg()
						.withArgName("rowNumber")
						.create("y"));
		return options;
	}
	
	/*
	 * Metodo que dado los argumentos del programa, devuelve la factoria corrrespondiente
	 * si hay errores en los argumentos muestra un error por medio de una excepcion y sale
	 * del programa. Se encarga de elegir el modo de ejecución
	 */
	private static FactoriaTipoJuego AnalisisArgs(String[] args) {
		final int COL_DEF = TipoJuego.GRAVITY.getColsPorDefecto();
		final int FIL_DEF = TipoJuego.GRAVITY.getFilasPorDefecto();
		FactoriaTipoJuego f;
		String juego;
		CommandLineParser parser;
		CommandLine cmdLine;
		int columnasGr;
		int filasGr;
		String []argNoEntendidos;
			
			f = new FactoriaConecta4();
			juego = "c4";
			parser = null;
			cmdLine = null;
			columnasGr = COL_DEF;
			filasGr = FIL_DEF;
			
		// FASE 1: Configuramos las opciones de validación de entrada.
		Options options = configurarOpciones();
			
		// FASE 2: Parseamos la entrada con la configuración introducida.
		try {
			parser = new BasicParser();
			cmdLine = parser.parse(options, args);
			argNoEntendidos = cmdLine.getArgs();
			
		    // FASE 3: Analizamos los resultados y realizamos las tareas pertinentes.
			if (argNoEntendidos.length == 0) {
				
				if (cmdLine.hasOption("h")) {
					HelpFormatter formatter = new HelpFormatter();
					formatter.printHelp(Main.class.getCanonicalName(), options, true); 
					System.exit(0);
					
				} else {
					if (cmdLine.hasOption("g")) {
						juego = cmdLine.getOptionValue("g");
					
						if (juego.equalsIgnoreCase("co")) {
							f = new FactoriaComplica();
						} else if (juego.equalsIgnoreCase("rv")) {
							f = new FactoriaReversi();
						} else if (juego.equalsIgnoreCase("gr")) {
							if (cmdLine.hasOption("x") && cmdLine.hasOption("y")) {
								columnasGr = Integer.parseInt(cmdLine.getOptionValue("x"));
								filasGr = Integer.parseInt(cmdLine.getOptionValue("y"));
							}
							f = new FactoriaGravity(columnasGr, filasGr);
						} else if (!juego.equalsIgnoreCase("c4")) {
							throw new Exception("Juego '" + juego + "' incorrecto.");
						}
					}
					if (cmdLine.hasOption("u")) {
						modo = cmdLine.getOptionValue("u");
						if (!modo.equals("console") && !modo.equals("window")) {
							throw new Exception("Modo de ejecucion '" + modo + "' incorrecto.");
						}	
					}
				}
			} else {
				String errorEnArgs = "";
				for (int i = 0; i < argNoEntendidos.length; i++) {
					errorEnArgs += " " + argNoEntendidos[i];
				}
				throw new Exception("Argumentos no entendidos:" + errorEnArgs);
			}
			
		} catch (java.lang.NumberFormatException e) {
			System.err.print("Las dimensiones tienen que ser numericas.\n");
			System.exit(1);
		}  catch (Exception ex){  
            System.err.print("Uso incorrecto: " + ex.getMessage() + "\n");
            System.err.print("Use -h|--help para más detalles.\n");
            System.exit(1);
		}		
		return f;
	}

}