package tp.pr5.control;

public class ModoAutomatico implements ModoJugador {

	private Thread hebra;
	private ControladorGUI control;
	
	public ModoAutomatico (ControladorGUI c) {
		this.control = c;
	}
	
	// Crea la hebra y la arranca
	public void comenzar() {
		this.hebra = new Thread() {
			public void run() {
				try {
					sleep(2000);
					control.ponerAleatorio();
				} catch (InterruptedException e) {}
			}
		};
		this.hebra.start();
	}

	// Si la hebra existe se detiene
	public void terminar() {
		if (this.hebra != null) {
			this.hebra.interrupt();
		}
	}
}
