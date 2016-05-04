package servicioPenalizaciones;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.mail.MessagingException;

import dominio.Penalizacion;
import dominio.Persona;
import dominio.Prestamo;
import dominio.Recurso;
import persistencia.DAOPenalizaciones;
import persistencia.DAOPersonas;
import persistencia.DAOPrestamos;
import persistencia.DAORecursos;

public class CheckerDevoluciones {
	
	public static void checkDevolucionRealizada(Prestamo p){
		long diferencia = p.getFechaDevolucion().getTimeInMillis() - p.getFechaFin().getTimeInMillis();
		if(diferencia/86400000 > 0){
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MILLISECOND, (int) diferencia);
			Penalizacion penalizacion = new Penalizacion(	p.getIdPrestatario(), 
															Calendar.getInstance(), 
															c,
															p.getId());
			try {
				DAOPenalizaciones.addPenalizacion(penalizacion);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void checkAll() {

		ArrayList<Prestamo> lista;
		lista = DAOPrestamos.buscarPrestamosNoDevueltos();

		for (Prestamo p : lista) {
			check(p);
		}

	}
	
	public static boolean isPenalizado(Persona p){
		ArrayList<Penalizacion> penaliz = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		try {
			penaliz = DAOPenalizaciones.buscarPorDni("" + p.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Penalizacion pen : penaliz){
			if(c.getTimeInMillis() >= pen.getfInicio().getTimeInMillis() 
				&& c.getTimeInMillis() <= (pen.getfFinal().getTimeInMillis() + 86400000)) return true; 
		}
		return false;
		
		
	}

	private static void check(Prestamo p) {
		if (checkUltimaNotif(p)) {
			Calendar fechaFin = p.getFechaFin();
			int diasDiferencia;
			diasDiferencia = (int) ((fechaFin.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 86400000); //ms a dias
			
			if (diasDiferencia <= -1) {
	
				devolucionPendienteAccion(p);
	
			} else if (diasDiferencia <= 1 && diasDiferencia >= 0) {
	
				devolucionProximaAccion(p);
	
			}
		}

	}

	private static void devolucionProximaAccion(Prestamo p) {

		enviarMailDevolucionProxima(p);

	}

	private static void devolucionPendienteAccion(Prestamo p) {
		enviarMailDevolucionPendiente(p);
		crearPenalizacionDiaria(p);
		
		

	}

	private static void crearPenalizacionDiaria(Prestamo p) {		
		
		Penalizacion penaliz = new Penalizacion(p.getIdPrestatario(), 
												Calendar.getInstance(), 
												Calendar.getInstance(),
												p.getId());
		
		try {
			DAOPenalizaciones.addPenalizacion(penaliz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void enviarMailDevolucionPendiente(Prestamo p) {
			Persona prestatario = null;
			try {
				prestatario = DAOPersonas.buscarPorId(p.getIdPrestatario());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			
			Recurso recurso = DAORecursos.buscarRecursoPorID(p.getIdRecurso());
			String mensaje = "Saludos,\n\nUsted, " + prestatario.getNombre() + ", ha realizado un prestamo de "
					+  recurso.getNombre() + ", " +recurso.getDescripción() + " en la fecha " + f.format(p.getFechaInicio().getTime())
					+ " que tenia como fecha limite para su devolucion " + f.format(p.getFechaFin().getTime()) + ". Por"
					+ " favor realize la devolucion de este recurso lo antes posible para evitar una mayor penalizacion.\n\nGracias";
			try {
				Mailer.Send(prestatario.getEmail(), "Devlolucion Proxima", mensaje);
				System.out.println("Se ha enviado un mail");
				p.setFechaUltimaNotificicacion(Calendar.getInstance());
				DAOPrestamos.actualizarFechasPrestamo(p);
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		
	}

	private static void enviarMailDevolucionProxima(Prestamo p) {
		if (checkUltimaNotif(p)) {

			Persona prestatario = null;
			try {
				prestatario = DAOPersonas.buscarPorId(p.getIdPrestatario());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Recurso recurso = DAORecursos.buscarRecursoPorID(p.getIdRecurso());
			SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
			String mensaje = "Saludos\n\nUsted, " + prestatario.getNombre() + ", ha realizado un prestamo de "
					+ recurso.getNombre() + ", " +recurso.getDescripción() + " en la fecha " + f.format(p.getFechaInicio().getTime())
					+ " que tiene como fecha limite para su devolucion " + f.format(p.getFechaFin().getTime()) + ".\n\nGracias";

			try {
				Mailer.Send(prestatario.getEmail(), "Devlolucion Proxima", mensaje);
				
				p.setFechaUltimaNotificicacion(Calendar.getInstance());
				DAOPrestamos.actualizarFechasPrestamo(p);
				
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

	}

	private static boolean checkUltimaNotif(Prestamo p) {
		int dif = 0;
		if(p.getFechaUltimaNotificicacion().equals(null)) return true;
		dif = (int) (( Calendar.getInstance().getTimeInMillis() - p.getFechaUltimaNotificicacion().getTimeInMillis())/86400000);
		return dif >= 1;
	}

}
