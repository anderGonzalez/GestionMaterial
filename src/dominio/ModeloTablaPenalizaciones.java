package dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import persistencia.DAOPenalizaciones;
import persistencia.DAORecursos;
import persistencia.DAOReservas;
import presentación.ModeloColumnasTablaReservas;

public class ModeloTablaPenalizaciones extends AbstractTableModel {
	
	final int ADMINISTRADOR = 1;
	ModeloColumnasTablaReservas columnas;
	Persona persona;
	ArrayList<Penalizacion> listaPenalizacion;
	
	public ModeloTablaPenalizaciones (ModeloColumnasTablaReservas columnas, Persona persona) throws Exception{
		super();
		this.columnas = columnas;
		this.persona = persona;
		if(persona.idTipoUsuario == ADMINISTRADOR){
			listaPenalizacion = DAOPenalizaciones.obtenerPenalizaciones();
		}else{
			listaPenalizacion =  DAOPenalizaciones.buscarPorDni(""+persona.id);

		}
		
	}


	public Penalizacion getPenalizacionAt(int indice){
		return listaPenalizacion.get(indice);
	}
	@Override
	public int getColumnCount() {
		
		return columnas.getColumnCount();
	}

	@Override
	public int getRowCount() {
		
		return listaPenalizacion.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Reserva a = listaPenalizacion.get(fila);
		return getFieldAt(a,columna);
		
	}
		
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		return getValueAt(0,columnIndex).getClass();
	}

	public void actualizar() throws Exception {
		
		listaPenalizacion = DAOReservas.getReservasRecurso(recurso);
		
		this.fireTableDataChanged();
	}
	public void actualizarPorCambioDeFechas(LocalDateTime desde, LocalDateTime hasta){
		listaPenalizacion = DAOReservas.getReservasRecursoEntreFechas(recurso, desde, hasta);
		this.fireTableDataChanged();
	}
	public Object getFieldAt(Reserva reserva,int columna) {
		switch (columna){
		case 0: return reserva.getPersona().getNombre();
		case 1: return reserva.getDesde();
		case 2: return reserva.getHasta();
		case 3: return new Integer(reserva.getUrgencia());
	
		}
		return null;
	}
}
