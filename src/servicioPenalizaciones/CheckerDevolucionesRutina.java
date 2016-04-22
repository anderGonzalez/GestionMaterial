package servicioPenalizaciones;

import java.util.Timer;
import java.util.TimerTask;

public class CheckerDevolucionesRutina {
	static final int MIN_TO_MILIS = 60000;
	long periodo;
	Timer rutina;

	public CheckerDevolucionesRutina(long periodo) {
		this.periodo = periodo;
		startRutina();
	}

	public void startRutina() {

		rutina = new Timer();
		rutina.schedule(new TimerTask() {

			@Override
			public void run() {

				CheckerDevoluciones.checkAll();

			}
		}, 0, periodo * MIN_TO_MILIS);
	}

	public void stopRutina() {
		rutina.cancel();
	}
}
