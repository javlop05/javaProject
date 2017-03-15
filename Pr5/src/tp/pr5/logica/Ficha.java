package tp.pr5.logica;

import java.awt.Color;
import tp.pr5.control.ModoJugador;
import tp.pr5.control.TipoTurno;

public enum Ficha {
	
	VACIA("vacia", " ", Color.GREEN),
	BLANCA("blancas", "O", Color.WHITE),
	NEGRA("negras", "X", Color.BLACK);
	
	private Ficha(String color, String simbolo, Color fondo){
		this.colorDeFicha = color;
		this.simbolo = simbolo;
		this.fondo = fondo;
	}
	
	// Devuelve el nombre de la ficha
	public String colorDeFicha() {
		return this.colorDeFicha;
	} 
	
	// Devuelve el símbolo de la ficha (usado en tablero toString)
	public String colorASimbolo () {
		return this.simbolo;
	}
	
	// Devuelve el color que representa a la Ficha (
	public Color getColorFondo () {
		return this.fondo;
	}
	
	// Devuelve el tipo de turno (HUMANO o AUTOMATICO)
	public TipoTurno getTipoTurno() {
		return this.tipoTurno;
	}
	
	// Asigna el TipoTurno t 
	public void setTipoTurno (TipoTurno t) {
		this.tipoTurno = t;
	}
	
	// Devuelve el modo de juego 
	public ModoJugador getModoJuego () {
		return this.modo;
	}
	
	// Asigna el modo de juego
	public void setModoJuego (ModoJugador m) {
		this.modo = m;
	}
	
	private String colorDeFicha;
	private String simbolo;
	private Color fondo;
	private TipoTurno tipoTurno;
	private ModoJugador modo;
	
}