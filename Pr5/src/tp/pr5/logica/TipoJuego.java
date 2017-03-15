package tp.pr5.logica;

// Enumerado del tipo de juego
public enum TipoJuego {
	CONECTA4 (6,7,false,false,false), COMPLICA (7,4,false,false,false),
	GRAVITY(10,10,true,false,false), REVERSI (8,8, false,true,true);
	
	TipoJuego (int f, int c, boolean redimens, boolean m, boolean s) {
		this.filsPorDef = f;
		this.colsPorDef = c;
		this.redimensionable = redimens;
		this.marcador = m;
		this.sugerencias = s;
	}
	
	// Devuelve si el juego es redimensionable (usado en vistaGUI)
	public boolean isRedimensionable () {
		return this.redimensionable;
	}
	
	// Devuelve el número de filas originales.
	public int getFilasPorDefecto() {
		return filsPorDef;
	}
	
	// Devuelve el número de columnas originales.
	public int getColsPorDefecto() {
		return colsPorDef;
	}
	
	// Devuelve si el juego admite marcador (usado en vistaGUI)
	public boolean admiteMarcador () {
		return this.marcador;
	}
	
	// Devuelve si el juego admite sugerencias (usado en vistaGUI)
	public boolean admiteSugerencias () {
		return this.sugerencias;
	}
	
	private int filsPorDef;
	private int colsPorDef;
	private boolean redimensionable;
	private boolean marcador;
	private boolean sugerencias;
}