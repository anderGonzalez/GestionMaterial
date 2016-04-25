package servicioPenalizaciones;

import java.util.ArrayList;
import java.util.Calendar;

import javax.mail.MessagingException;

import dominio.Persona;
import dominio.Prestamo;
import dominio.Recurso;
import dominio.Reserva;
import persistencia.DAOPersonas;
import persistencia.DAOPrestamos;
import persistencia.DAORecursos;

public class CheckerDevoluciones {

	public static void checkAll() {

		ArrayList<Prestamo> lista;
		lista = DAOPrestamos.buscarPrestamosNoDevueltos();

		for (Prestamo p : lista) {
			check(p);
		}

	}

	private static void check(Prestamo p) {
		Calendar fechaFin = p.getFechaFin();
		int diasDiferencia;
		diasDiferencia = fechaFin.compareTo(Calendar.getInstance()) / 86400000;

		if (diasDiferencia <= -1) {

			devolucionPendienteAccion(p);

		} else if (diasDiferencia <= 1 && diasDiferencia >= 0) {

			devolucionProximaAccion(p);

		}

	}

	private static void devolucionProximaAccion(Prestamo p) {

		enviarMailDevolucionProxima(p);

	}

	private static void devolucionPendienteAccion(Prestamo p) {
		// TODO Auto-generated method stub

	}

	private static void enviarMailDevolucionPendiente(Prestamo p) {
		if (checkUltimaNotif(p)) {
			Persona prestatario = null;
			try {
				prestatario = DAOPersonas.buscarPorId(p.getIdPrestatario());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Recurso recurso = DAORecursos.buscarRecursoPorID(p.getIdRecurso());
			String mensaje = "Usted, " + prestatario.getNombre() + ", ha realizado un prestamo de "
					+ recurso.getDescripción() + " en la fecha " + p.getFechaInicio()
					+ " que tenia como fecha limite para su devolucion " + p.getFechaFin().toString() + ". Por"
					+ " favor realize la devolucion de este recurso lo antes posible para evitar una mayor penalizacion.\n\tGracias";
			try {
				Mailer.Send(prestatario.getEmail(), "Devlolucion Proxima", mensaje);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void enviarMailDevolucionProxima(Prestamo p) {
		if (checkUltimaNotif(p)) {

			Persona prestatario = null;
			try {
				prestatario = DAOPersonas.buscarPorId(p.getIdPrestatario());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Recurso recurso = DAORecursos.buscarRecursoPorID(p.getIdRecurso());

			String mensaje = "Usted, " + prestatario.getNombre() + ", ha realizado un prestamo de "
					+ recurso.getDescripción() + " en la fecha " + p.getFechaInicio()
					+ " que tiene como fecha limite para su devolucion " + p.getFechaFin().toString() + ".\n\tGracias";

			try {
				Mailer.Send(prestatario.getEmail(), "Devlolucion Proxima", mensaje);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static boolean checkUltimaNotif(Prestamo p) {
		int dif = 0;
		dif = p.getFechaUltimaNotifIcacion().compareTo(Calendar.getInstance())/86400000;
		return dif >= 1;
	}

}
