package tp.pr5.Vista;

import java.awt.*;
import javax.swing.*;

import tp.pr5.control.ControladorGUI;

@SuppressWarnings("serial")
public class VistaGUI extends JFrame {
	
	private ControladorGUI controlGUI;
	private Container panelPrincipal;
	
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;

	public VistaGUI (ControladorGUI c) {
		super("Práctica 5 - TP");
		
		this.controlGUI = c;
		
		configurarComponentes();
		//configurarManejadoresEventos();
		
		/* Este método nos inicializará el tablero con el tamaño
		 *  correspondiente y nos pondrá el turno inicial
		 *  de tal manera que en el comboBox aparecera seleccionado 
		 *  el juego al que estamos jugando 
		 */
		this.controlGUI.comienzaPartida();
		
		this.pack(); // Ajustamos los componentes a la ventana
		this.setLocation(0,0); 
		this.setVisible(true); 
		this.setResizable(false); // Evitamos que el usuario redimensione la ventana
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("Imagenes/aplicacion.png")).getImage());
	}
	
	// Configura los componentes del panel principal (excepto el tablero y turno).
	public void configurarComponentes() {
		panelPrincipal = this.getContentPane();
		panelPrincipal.setLayout(new BorderLayout());
		
		panelIzquierdo = new PanelIzquierdo(this.controlGUI, this);
		panelDerecho = new PanelDerecho(this.controlGUI, this);
		
		panelPrincipal.add(panelIzquierdo, BorderLayout.WEST); // Al oeste
		panelPrincipal.add(panelDerecho, BorderLayout.EAST); // Al este
	}

}