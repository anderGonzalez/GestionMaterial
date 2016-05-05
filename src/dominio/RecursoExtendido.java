package dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

import persistencia.DAOPersonas;
import persistencia.DAOPrestamos;
import persistencia.DAORecursos;
import persistencia.DAOReservas;

public class RecursoExtendido extends Recurso {
	 
	String nombreResponsable;
	Estado estado;
	public RecursoExtendido(int id, String nombre, String descripción, String ubicación, int idResponsable) throws Exception {
		super(id, nombre, descripción, ubicación, idResponsable);
		nombreResponsable = DAOPersonas.buscarPorId(idResponsable).getNombre();
		estado = identificarEstadoActualRecurso(id);
	}
	public RecursoExtendido (Recurso recurso) throws Exception{
		super(recurso);
		nombreResponsable = DAOPersonas.buscarPorId(idResponsable).getNombre();
		estado = identificarEstadoActualRecurso(id);
	}
	private Estado identificarEstadoActualRecurso(int id) {
		LocalDateTime ahora = LocalDateTime.now();
		if (DAOPrestamos.estaPrestado(id,ahora)) return Estado.PRESTADO;
		if (DAOReservas.estaReservado(id,ahora)) return Estado.RESERVADO;
		
		return Estado.DISPONIBLE;
	}
	public String getNombreResponsable() {
		return nombreResponsable;
	}
	public Estado getEstado() {
		return estado;
	}
	public boolean isPrestado() {
		ArrayList<Prestamo> lista;
		try {
			lista = DAOPrestamos.buscarPorIdRecurso(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
