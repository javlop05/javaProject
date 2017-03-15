package tp.pr5.logica;

public enum Direccion {
	NORTE(-1,0),
	SUR(1,0),
	ESTE(0,1),
	OESTE(0,-1),
	NORDESTE(-1,1),
	NOROESTE(-1,-1),
	SUDESTE(1,1),
	SUROESTE(1,-1);
		
	
	private Direccion(int contFilas, int contColumnas){
		this.contFilas = contFilas;
		this.contColumnas = contColumnas;
	}
	 
	public int getContFilas() {
		return this.contFilas;
	} 
	
	public int getContColumnas () {
		return this.contColumnas;
	}
	
	/* Atributos para indicar que hay que hacer a 
	 * las filas y columnas para moverse en esa direccion */
	private int contFilas;
	private int contColumnas;
	
}
