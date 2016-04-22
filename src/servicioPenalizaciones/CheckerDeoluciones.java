package servicioPenalizaciones;

import java.util.ArrayList;
import java.util.Calendar;

import dominio.Prestamo;
import persistencia.DAOPrestamos;

public class CheckerDeoluciones {

	public static void checkAll() {
		
		ArrayList<Prestamo> lista;
		lista = DAOPrestamos.buscarPrestamosNoDevueltos();
		
		for(Prestamo p : lista){
			check(p);
		}
		
		
	}

	private static void check(Prestamo p) {		
		Calendar fechaFin = p.getFechaFin();
		int diasDiferencia;
		diasDiferencia = fechaFin.compareTo(Calendar.getInstance())/86400000;
		
		if(diasDiferencia <= -1){
			
			enviarMailDevolucionPendiente(p);
			
		}else if(diasDiferencia <= 1 && diasDiferencia >= 0){
			
			enviarMailDevolucionProxima(p);
			
		}
		
	}

	private static void enviarMailDevolucionPendiente(Prestamo p) {
		// TODO Auto-generated method stub
		
	}

	private static void enviarMailDevolucionProxima(Prestamo p) {
		// TODO Auto-generated method stub
		
	}

}
