package tp.pr5.logica;

public class MovimientoInvalido extends java.lang.Exception{

	private static final long serialVersionUID = -5001298266428398926L;

	// Constructor sin parámetros.
	public MovimientoInvalido() {
		super();
	}
	
	// Constructor con un parámetro para el mensaje.
	public MovimientoInvalido(java.lang.String msg) {
		super(msg);
	}
	
	// Constructor con un parámetro para el mensaje y otro para la causa.
	public MovimientoInvalido(java.lang.String msg, java.lang.Throwable arg) {
		super(msg, arg);
	}
	
	// Constructor con un parámetro para la causa inicial que provocó la excepción.
	public MovimientoInvalido(java.lang.Throwable arg) {
		super(arg);
	}
	
}