package tp.pr5.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import tp.pr5.control.ControladorGUI;
import tp.pr5.control.TipoTurno;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.MovimientoReversi;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;

public class PanelDerecho extends JPanel implements Observador {
	
	private static final long serialVersionUID = 6505223552487420541L;
	private ControladorGUI controlGUI;
	private TipoJuego juegoActual;
	private JFrame panelPrincipal;
	
	// 1. Panel de la derecha superior
	private JPanel panelSuperiorDerecho;
		// 1.1. Panel de acciones
		private JPanel acciones;
			private JButton reiniciar;
				private ImageIcon img_reiniciar;
			private JButton deshacer;
				private ImageIcon img_deshacer;
		// 1.2. Panel cambio de jugadores
		private JPanel jugadores;
			private JLabel jugador1;
			private JLabel jugador2;
			private JComboBox <TipoTurno> tipoJugador1;
			private JComboBox <TipoTurno> tipoJugador2;
		// 1.3. Panel de cambio de juego
		private JPanel cambioJuego;
			private JComboBox <TipoJuego> tipoJuego;
				// 1.3.1. Panel de configuración
				private JPanel configuracion;
					// 1.3.1.1. Panel de medidas
					private JPanel medidas;
						private JLabel filas;
						private JTextField getFilas;
						private JLabel columnas;
						private JTextField getColumnas;
				// 1.3.2 Panel del botón cambiar
				private JPanel panelBotonCambiar;
					private JButton cambiar;
					private ImageIcon img_cambiar;
		//1.4 Panel contadores para reversi
		private JPanel contadores;
			private JLabel infoBlancas;
			private JLabel contBlancas;
			private JLabel infoNegras;
			private JLabel contNegras;
	// 2. Panel de la derecha inferior
	private JPanel panelInferiorDerecho;
		private JButton salir;
		private ImageIcon img_salir;
	
	public PanelDerecho(ControladorGUI c, JFrame f) {
		super();
		this.panelPrincipal = f;
		this.controlGUI = c;
		this.controlGUI.addObservador(this);
		this.setLayout(new BorderLayout());
	
		// Configuramos elementos de los subpaneles y los manejadores de eventos
		configuracionPanelSuperiorDerecho();
		configuracionPanelInferiorDerecho();
		configurarManejadoresEventos();
			
		// Añadimos los dos subpaneles al panel derecho
		this.add(panelSuperiorDerecho, BorderLayout.NORTH);
		this.add(panelInferiorDerecho, BorderLayout.SOUTH);
	}
	
	// Configura los componentes del panel superior derecho
	private void configuracionPanelSuperiorDerecho() {
		panelSuperiorDerecho = new JPanel();
		panelSuperiorDerecho.setLayout(new BoxLayout(panelSuperiorDerecho, BoxLayout.Y_AXIS));
			
		// 1.1. Configuración del panel de acciones
		acciones = new JPanel();
		acciones.setBorder(BorderFactory.createTitledBorder("Partida"));
		acciones.setLayout(new FlowLayout());
			
		// Configuración del botón "Deshacer"
		deshacer = new JButton("Deshacer");
		img_deshacer =  new ImageIcon(getClass().getResource("Imagenes/deshacer.png"));
		deshacer.setIcon(img_deshacer);
		deshacer.setEnabled(false);
			
		// Configuración del botón "Reiniciar"
		reiniciar = new JButton("Reiniciar");
		img_reiniciar =  new ImageIcon(getClass().getResource("Imagenes/reiniciar.png"));
		reiniciar.setIcon(img_reiniciar);
			
		// Añadimos los botones al panel de acciones
		acciones.add(deshacer);
		acciones.add(reiniciar);
		
		//1.2. Configuracion del panel cambio de jugadores
		jugadores = new JPanel();
		jugadores.setBorder(BorderFactory.createTitledBorder("Gestion de jugadores"));
		jugadores.setLayout(new GridLayout(2,2));
		jugador1 = new JLabel("Jugador de blancas");
		jugador2 = new JLabel("J. Negras");
		TipoTurno[] tjugador = TipoTurno.values();
		tipoJugador1 = new JComboBox <TipoTurno> (tjugador);
		tipoJugador2 = new JComboBox <TipoTurno> (tjugador);
		jugadores.add(jugador1);
		jugadores.add(tipoJugador1);
		jugadores.add(jugador2);
		jugadores.add(tipoJugador2);
		
		// 1.3. Configuración del panel cambio de juego
		cambioJuego = new JPanel();
		cambioJuego.setBorder(BorderFactory.createTitledBorder("Cambio de Juego"));
		cambioJuego.setLayout(new BorderLayout());
			
		// Configuración del comboBox tipoJuego
		TipoJuego[] juegos = TipoJuego.values();
		tipoJuego = new JComboBox <TipoJuego> (juegos);
					
		// 1.3.1. Configuración del panel de configuración
		configuracion = new JPanel();
		configuracion.setLayout(new BorderLayout()); 
			
		// 1.3.1.1. Configuración del panel de medidas
		medidas = new JPanel();
		medidas.setLayout(new FlowLayout());
		filas = new JLabel("Filas ");
		columnas = new JLabel("Columnas ");
		getFilas = new JTextField(5);
		getColumnas = new JTextField(5);
		getFilas.setText(Integer.toString(TipoJuego.GRAVITY.getFilasPorDefecto()));
		getColumnas.setText(Integer.toString(TipoJuego.GRAVITY.getColsPorDefecto()));
		medidas.add(filas);
		medidas.add(getFilas);
		medidas.add(columnas);
		medidas.add(getColumnas);
		configuracion.add(medidas, BorderLayout.NORTH);
		configuracion.setVisible(false);
		
		// 1.3.2. Configuración del panel del botón "Cambiar"
		panelBotonCambiar = new JPanel();
		cambiar = new JButton("Cambiar");
		img_cambiar =  new ImageIcon(getClass().getResource("Imagenes/cambiar.png"));
		cambiar.setIcon(img_cambiar);
		cambiar.setHorizontalAlignment(SwingConstants.CENTER);
		panelBotonCambiar.add(cambiar);
			
		// Añadimos los dos subpaneles al panel de cambio de juego
		cambioJuego.add(tipoJuego, BorderLayout.NORTH);
		cambioJuego.add(configuracion, BorderLayout.CENTER);
		cambioJuego.add(panelBotonCambiar, BorderLayout.SOUTH);
		
		//1.4. Configuración del panel contadores
		contadores = new JPanel();
		contadores.setBorder(BorderFactory.createTitledBorder("Marcador"));
		contadores.setLayout(new FlowLayout());
		infoBlancas = new JLabel("Blancas: ");
		contBlancas = new JLabel();
		contBlancas.setFont(new Font("Tahoma", 0, 15));
		infoNegras = new JLabel ("Negras: ");
		contNegras = new JLabel();
		contNegras.setFont(new Font("Tahoma", 0, 15));
		contadores.add(infoBlancas);
		contadores.add(contBlancas);
		contadores.add(infoNegras);
		contadores.add(contNegras);
		contadores.setVisible(false);
		
		// Añadimos los dos subpaneles al panel superior derecho 
		panelSuperiorDerecho.add(acciones);
		panelSuperiorDerecho.add(jugadores);
		panelSuperiorDerecho.add(cambioJuego);
		panelSuperiorDerecho.add(contadores);
	}
		
	// Configura los componentes del panel inferior derecho
	private void configuracionPanelInferiorDerecho () {
		panelInferiorDerecho = new JPanel();
		salir = new JButton("Salir");
		img_salir =  new ImageIcon(getClass().getResource("Imagenes/salir.png"));
		salir.setIcon(img_salir);
		salir.setHorizontalAlignment(SwingConstants.CENTER);
		panelInferiorDerecho.add(salir);
	}
		
	private void configurarManejadoresEventos() {
		deshacer.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controlGUI.undo();
					}
				});
		
		// Clase anónima que gestiona el botón "Salir".
		salir.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals("Salir")) {
							// Añadimos una ventana emergente con opciones "Si" o "No"
							int confirmarSalir = JOptionPane.showConfirmDialog(null,
									"¿Realmente desea salir?",
									"Salir",
									JOptionPane.YES_OPTION,
									JOptionPane.QUESTION_MESSAGE);
							if (confirmarSalir == JOptionPane.YES_OPTION)
									System.exit(0);
						}
					}
				});
		
		/* Clase anónima que gestiona el comboBox del tipo de juego, para 
		   activar o desactivar la visibilidad de las dimensiones del Gravity. */
		tipoJuego.addActionListener(
				new ActionListener() {
					public void actionPerformed (ActionEvent e) {
						TipoJuego game = (TipoJuego)tipoJuego.getSelectedItem();
						if (game.isRedimensionable()) {
							getFilas.setText(Integer.toString(game.getFilasPorDefecto()));
							getColumnas.setText(Integer.toString(game.getColsPorDefecto()));
							configuracion.setVisible(true); // lo hacemos visible
							panelPrincipal.pack(); // para que no se nos desajuste la ventana
						} else {
							configuracion.setVisible(false);
						}
					}
				});
		
		// Clase anónima que gestiona el botón "Cambiar".
		cambiar.addActionListener(
				new ActionListener() {
					public void actionPerformed (ActionEvent e) {
						int filas, columnas;
						TipoJuego juegoSeleccionado;
						
							juegoSeleccionado = (TipoJuego)tipoJuego.getSelectedItem();
							
						if (juegoSeleccionado.isRedimensionable()) {
							try {
								filas = Integer.parseInt(getFilas.getText());
								columnas = Integer.parseInt(getColumnas.getText());
								controlGUI.cambiarJuego(juegoSeleccionado, filas, columnas);
							} catch (java.lang.Exception ex) {
								java.awt.Toolkit.getDefaultToolkit().beep();
								JOptionPane.showMessageDialog(null, 
										"Las dimensiones deben ser numéricas.", "Error", 
										JOptionPane.ERROR_MESSAGE);
								/*Como ha salido un error y el comboBox tiene que mostrar
								 * el juego al que se esta jugando
								 */
								tipoJuego.setSelectedItem(juegoActual);
							}
							
						} else {
							filas = juegoSeleccionado.getFilasPorDefecto();
							columnas = juegoSeleccionado.getColsPorDefecto();
							controlGUI.cambiarJuego(juegoSeleccionado, filas, columnas);
						}
						panelPrincipal.pack();
					}
				});
		
		// Clase anónima que gestiona el botón "Reiniciar".
		reiniciar.addActionListener(
				new ActionListener() {
					public void actionPerformed (ActionEvent e) {
						controlGUI.reset();
						panelPrincipal.pack(); // para que no se nos desajuste la ventana
					}
				});
		
		// Clase anónima que gestiona el comboBox de tipoDeJugador1
		tipoJugador1.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						TipoTurno j = (TipoTurno) tipoJugador1.getSelectedItem();
						controlGUI.cambiarJugador(j, Ficha.BLANCA);
					}
				});
		
		// Clase anónima que gestiona el comboBox de tipoDeJugador2
		tipoJugador2.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						TipoTurno j = (TipoTurno) tipoJugador2.getSelectedItem();
						controlGUI.cambiarJugador(j, Ficha.NEGRA);
					}
				});
			
	}
		
	// Notifica que se ha reiniciado la partida, mostrando el tablero inicial y el turno.
	public void onReset(TableroInmutable tab, Ficha turno,ArrayList<Movimiento> movsPosibles) {
		habilitaCompParaNewPartida(tab);
		if (this.juegoActual.admiteMarcador()) {
			actualizarContadores(tab);
		}
		tipoJugador1.setEnabled(true);
		tipoJugador2.setEnabled(true);
	}

	// Habilita o deshabilita los componentes necesarios para empezar una nueva partida.
	private void habilitaCompParaNewPartida(TableroInmutable tab) {
		if (deshacer.isEnabled())
			deshacer.setEnabled(false); // deshabilitamos el botón "Deshacer"
		cambiar.setEnabled(true); // habilitamos el boton "Cambiar"
		tipoJuego.setEnabled(true); // habilitamos el comboBox del tipo de juego
	}
		
	// Notifica que ha terminado la partida, mostrando el tablero y el ganador.
	public void onPartidaTerminada(final TableroInmutable tablero, Ficha ganador) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() { 
				deshacer.setEnabled(false); // deshabilitamos el botón "Deshacer"
				cambiar.setEnabled(false); // deshabilitamos el botón "Cambiar"
				tipoJuego.setEnabled(false); // deshabilitamos el comboBox de cambio de juego
				tipoJugador1.setEnabled(false);
				tipoJugador2.setEnabled(false);
				if (juegoActual.admiteMarcador()) {
					actualizarContadores(tablero);
				}
			}
			
		});
	}

	/* Notifica que ha empezado una nueva partida, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	public void onComienzaPartida(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles) {
		this.juegoActual = juego;
		tipoJuego.setSelectedItem(this.juegoActual);
		configuracionMarcador(tab);
	}

	/* Notifica que se ha deshecho un movimiento, proporcionando el tablero,
		el turno y si hay más movimivientos a deshacer o no. */
	public void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas, ArrayList<Movimiento> movsPosibles) {
		if (!hayMas)
			deshacer.setEnabled(false);
		if (this.juegoActual.admiteMarcador()) {
			actualizarContadores(tablero);
		}
	}

	// Notifica que se ha terminado de realizar un movimiento.
	public void onMovimientoEnd(final TableroInmutable tablero, final Ficha turno, ArrayList<Movimiento> movsPosibles) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// Si el botón "Deshacer" no está habilitado, lo habiltamos
				if (turno.getTipoTurno() == TipoTurno.HUMANO) {
					deshacer.setEnabled(true);
				}
				if (juegoActual.admiteMarcador()) {
					actualizarContadores(tablero);
				}
			}
			
		});
		
	}

	/* Notifica que se ha cambiado el juego, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	public void onCambioJuego(TableroInmutable tab, Ficha turno, TipoJuego juego,ArrayList<Movimiento> movsPosibles) {
		this.juegoActual = juego;
		configuracionMarcador(tab);
	}

	// (Uso solo en consola) No requiere implementación
	public void onUndoNotPossible(TableroInmutable tablero, Ficha turno) {}

	@Override
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {}

	// Prepara a la interfaz para realizar un movimiento
	public void onMovimientoStart(TableroInmutable tab, final Ficha turno) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				if (turno.getTipoTurno() == TipoTurno.AUTOMATICO) {
					deshacer.setEnabled(false);
				}
			}
			
		});
	}
	
	// Configura el marcador haciendolo o no visible dependiendo del juegoActual
	private void configuracionMarcador (TableroInmutable tab) {
		if (this.juegoActual.admiteMarcador()) {
			contadores.setVisible(true);
			actualizarContadores(tab);
		} else {
			contadores.setVisible(false);
		}
	}
	
	// Actualiza el marcador
	private void actualizarContadores(TableroInmutable tab) {
		contBlancas.setText("" + tab.getCont(Ficha.BLANCA));
		contNegras.setText("" + tab.getCont(Ficha.NEGRA));
	}
	
}