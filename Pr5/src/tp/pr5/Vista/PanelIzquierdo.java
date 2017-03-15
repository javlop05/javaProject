package tp.pr5.Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import tp.pr5.control.ControladorGUI;
import tp.pr5.control.TipoTurno;
import tp.pr5.logica.Ficha;
import tp.pr5.logica.Movimiento;
import tp.pr5.logica.MovimientoInvalido;
import tp.pr5.logica.MovimientoReversi;
import tp.pr5.logica.ReglasReversi;
import tp.pr5.logica.TableroInmutable;
import tp.pr5.logica.TipoJuego;

public class PanelIzquierdo extends JPanel implements Observador {

	private static final long serialVersionUID = -8726121586458696802L;
	private ControladorGUI controlGUI;
	private JFrame panelPrincipal;
	private TipoJuego juegoActual;
	//private ArrayList<MovimientoReversi> movsPosibles;
	
	// 1. Panel de la izquierda superior
	private JPanel panelSuperiorIzquierdo;
		// 1.1. Informacion de juego
		private JPanel panelInfoJuego;
		private JLabel infoJuego;
		// 1.2 Panel tablero y turno
		private JPanel panelTableroYturno;
		// 1.2.1 Panel de tablero
		private JPanel panelTablero;
			private JButton[][] tablero;
		// 1.2.2 Para dejar un hueco
		private JLabel espacio;
		// 1.2.3 Panel de turno
		private JPanel panelTurno;
			private JLabel turno;
	// 2. Panel de la izquierda inferior
	private JPanel panelInferiorIzquierdo;
		private JButton aleatorio;
		private ImageIcon img_aleatorio;
				
	public PanelIzquierdo(ControladorGUI c, JFrame f) {
		super();
		this.panelPrincipal = f;
		this.controlGUI = c;
		this.controlGUI.addObservador(this);
		this.setLayout(new BorderLayout());
		
		// Configuramos los componentes de los subpaneles
		configuracionPanelSuperiorIzquierdo();
		configuracionPanelInferiorIzquierdo();
		
		configurarManejadoresEventos();

		// Añadimos los dos subpaneles ya configurados al panel izquierdo
		this.add(panelSuperiorIzquierdo, BorderLayout.NORTH);
		this.add(panelInferiorIzquierdo, BorderLayout.SOUTH);
	}

	// 1. Configuración del panel superior izquierdo
	private void configuracionPanelSuperiorIzquierdo() {
		panelSuperiorIzquierdo = new JPanel();
		panelSuperiorIzquierdo.setLayout(new BorderLayout());
		/*panelSuperiorIzquierdo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE), 
				BorderFactory.createLineBorder(Color.BLACK)));*/
		
		// 1.1. Configuración panel info de juego
		panelInfoJuego = new JPanel();
		infoJuego = new JLabel(" ");
		infoJuego.setForeground(Color.BLUE);
		infoJuego.setFont(new Font("Tahoma", 0, 20));
		infoJuego.setHorizontalAlignment(SwingConstants.CENTER);
		panelInfoJuego.add(infoJuego);
		
		// 1.2. Configuración del panel tablerYturno
		
		panelTableroYturno = new JPanel();
		panelTableroYturno.setLayout(new BorderLayout());
		panelTableroYturno.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE), 
				BorderFactory.createLineBorder(Color.BLACK)));
		
		// 1.2.1 Configuración del panelTablero
		panelTablero = new JPanel();
		/* La matriz de botones la configuraremos cuando hayamos configurado
		 * todo lo demás, y hayamos añadido los eventos
		 */
		
		espacio = new JLabel(" ");
		
		// 1.2.2 Configuración del panelTurno
		panelTurno = new JPanel();
		panelTurno.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.WHITE), 
				BorderFactory.createLineBorder(Color.BLACK)));

		panelTableroYturno.add(panelTablero, BorderLayout.NORTH);
		panelTableroYturno.add(espacio, BorderLayout.CENTER);
		panelTableroYturno.add(panelTurno, BorderLayout.CENTER);
		
		// Añadimos los dos subpaneles al panel superior izquierdo
		panelSuperiorIzquierdo.add(panelInfoJuego, BorderLayout.NORTH);
		panelSuperiorIzquierdo.add(panelTableroYturno, BorderLayout.CENTER);
	}
		
	// 2. Configuración del panel inferior izquierdo
	private void configuracionPanelInferiorIzquierdo() {
		panelInferiorIzquierdo = new JPanel();

		aleatorio = new JButton("Poner Aleatorio");
		img_aleatorio = new ImageIcon(getClass().getResource("Imagenes/aleatorio.png"));
		aleatorio.setIcon(img_aleatorio);
		aleatorio.setHorizontalAlignment(SwingConstants.CENTER);
		panelInferiorIzquierdo.add(aleatorio);
	}
	
	private void configurarManejadoresEventos() {
		aleatorio.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						controlGUI.ponerAleatorio();
					}
				});
	}
	
	// Notifica que se ha reiniciado la partida, mostrando el tablero inicial y el turno.
	public void onReset(TableroInmutable tab, Ficha turno, ArrayList<Movimiento> movsPosibles) {
		habilitaCompParaNewPartida(tab);
		pintaTablero(tab);
		this.turno.setText("Juegan " + turno.colorDeFicha());
		if (this.juegoActual.admiteSugerencias()) {
			pintarSugerencias(movsPosibles);
		}
	}
		
	// Inicializa el color de las fichas del tablero que se muestra en la ventana.
	private void pintaTablero(TableroInmutable tab) {
		for (int i = 0; i < tab.getAlto(); i++) {
			for (int j = 0; j < tab.getAncho(); j++) {
				tablero[i][j].setBackground(tab.getCasilla(j+1, i+1).getColorFondo());
			}
		}
	}

	// Habilita o deshabilita los componentes necesarios para empezar una nueva partida.
	private void habilitaCompParaNewPartida(TableroInmutable tab) {
		habilitarTablero(tab); // habilitamos el tablero
		aleatorio.setEnabled(true); // habilitamos el botón "Aleatorio"
	}

	// Activa los botones del tablero.
	private void habilitarTablero (TableroInmutable tab) {
		for(int i = 0; i < tab.getAlto(); i++) {
			for (int j = 0; j < tab.getAncho(); j++) {
				if (!tablero[i][j].isEnabled()) {
					tablero[i][j].setEnabled(true);
				}
			}
		}
	}
		
	// Notifica que ha terminado la partida, mostrando el tablero y el ganador.
	public void onPartidaTerminada(TableroInmutable tablero, Ficha ganador) {
		if (ganador != Ficha.VACIA) {
			turno.setText("Ganan las " + ganador.colorDeFicha());
		} else {
			turno.setText("Partida terminada en tablas");
			panelPrincipal.pack(); // reajustamos la ventana
		}
		pintaTablero(tablero); // pintamos el último tablero
		deshabilitarTablero(tablero); // deshabilitamos el tablero
		aleatorio.setEnabled(false); // deshabilitamos el botón "Aleatorio"
	}
		
	// Desactiva los botones del tablero.
	private void deshabilitarTablero(TableroInmutable tab) {
		for (int i = 0; i < tab.getAlto(); i++) {
			for (int j = 0; j < tab.getAncho(); j++) {
				tablero[i][j].setEnabled(false);
			}
		}
	}

	/* Notifica que ha empezado una nueva partida, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	public void onComienzaPartida(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles) {
		this.juegoActual = juego;
		infoJuego.setText(juego.toString());
		inicializarTableroYTurno(tab, turno);
		if (this.juegoActual.admiteSugerencias()) {
			pintarSugerencias(movsPosibles);
		}
		panelPrincipal.pack();
	}
	 	
	/* Notifica que se ha cambiado el juego, proporcionando el tablero inicial, el turno.
	 * y juego al que se ha cambiado.*/
	public void onCambioJuego(TableroInmutable tab, Ficha turno, TipoJuego juego, ArrayList<Movimiento> movsPosibles) {
		this.juegoActual = juego;
		infoJuego.setText(juego.toString());
		// Borramos el panel
		panelTablero.removeAll();
		// Creamos de nuevo el panel, y escribimos en la etiqueta del turno quien empieza
		inicializarTableroYTurno(tab, turno);
		if (this.juegoActual.admiteSugerencias()) {
			pintarSugerencias(movsPosibles);
		}
		panelTablero.revalidate();
		panelPrincipal.pack();
	}

	// Configura el tablero y el turno al inicio de la partida.
	private void inicializarTableroYTurno(TableroInmutable tab, Ficha turno) {
		int filas = tab.getAlto(), columnas = tab.getAncho();
		panelTablero.setLayout(new GridLayout(filas,columnas));
		tablero = new JButton[filas][columnas];
		
		// Configuración de la matriz de botones
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				tablero[i][j] = crearBotonTablero(i,j, tab.getCasilla(i+1, j+1));
				panelTablero.add(tablero[i][j]);
			}
		}
		
		panelTurno.removeAll();
		this.turno = new JLabel("Juegan " + turno.colorDeFicha());
		this.turno.setForeground(Color.BLUE);
		this.turno.setFont(new Font("Tahoma", 0, 20));
		panelTurno.add(this.turno);
	}
	
	// Configuración del botón "Tablero".
	private JButton crearBotonTablero (final int fila, final int col, Ficha color) {
		JButton boton = new JButton();
		boton.setPreferredSize(new Dimension(30,30));
		boton.setBackground(color.getColorFondo());
		
		// Clase anónima que gestiona la pulsación en el tablero.
		boton.addActionListener(
				new ActionListener () {
					public void actionPerformed (ActionEvent e) {
						controlGUI.poner(fila + 1, col + 1);
					}
				});
		
		return boton;	
	}


	/* Notifica que se ha deshecho un movimiento, proporcionando el tablero,
		el turno y si hay más movimivientos a deshacer o no. */
	public void onUndo(TableroInmutable tablero, Ficha turno, boolean hayMas, ArrayList<Movimiento> movsPosibles) {
		pintaTablero(tablero);
		this.turno.setText("Juegan " + turno.colorDeFicha());
		if (this.juegoActual.admiteSugerencias()) {
			pintarSugerencias(movsPosibles);
		}
	}

	// Notifica que se ha terminado de realizar un movimiento.
	public void onMovimientoEnd(TableroInmutable tablero, Ficha turno, ArrayList<Movimiento> movsPosibles) {
		// Pintamos el botón del ultimo movimiento
		pintaTablero(tablero);
		this.turno.setText("Juegan " + turno.colorDeFicha());
		if (this.juegoActual.admiteSugerencias()) {
			pintarSugerencias(movsPosibles);
		}
	}

	// Notifica que se ha intentado realizar un movimiento incorrecto.
	public void onMovimientoIncorrecto(MovimientoInvalido movimientoException) {
		java.awt.Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(null, 
				movimientoException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}

	// (Uso solo en consola) No requiere implementación
	public void onUndoNotPossible(TableroInmutable tablero, Ficha turno) {}
	
	// (Para Reversi) pinta los lugares del tablero donde puede poner
	private void pintarSugerencias (ArrayList<Movimiento> movsPosibles) {
		//ArrayList<MovimientoReversi> movsPosibles = ReglasReversi.getMovsPosibles();
		for (Movimiento mov: movsPosibles) {
			tablero[mov.getFila()-1][mov.getColumna()-1].setBackground(Color.cyan);
		}
	}

	// Prepara a la interfaz para realizar un movimiento
	public void onMovimientoStart(TableroInmutable tab, Ficha turno) {
		if (turno.getTipoTurno() == TipoTurno.AUTOMATICO) {
			deshabilitarTablero(tab);
			aleatorio.setEnabled(false);
		} else {
			habilitarTablero(tab);
			aleatorio.setEnabled(true);
		}
	}
}