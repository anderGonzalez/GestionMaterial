package dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import persistencia.DAORecursos;
import persistencia.DAOReservas;
import presentación.ModeloColumnasTablaReservas;

public class ModeloTablaReservas extends AbstractTableModel {
	
	ModeloColumnasTablaReservas columnas;
	RecursoExtendido recurso;
	ArrayList<Reserva> listaReservas;
	
	public ModeloTablaReservas (ModeloColumnasTablaReservas columnas,RecursoExtendido recurso) throws Exception{
		super();
		this.columnas = columnas;
		this.recurso = recurso;
		listaReservas =  DAOReservas.getReservasRecurso(recurso);
		
	}

	
	public ModeloTablaReservas(ModeloColumnasTablaReservas columnas, RecursoExtendido recurso, LocalDateTime desde,
			LocalDateTime hasta) {
		super();
		this.columnas = columnas;
		this.recurso = recurso;
		listaReservas =  DAOReservas.getReservasRecursoEntreFechas(recurso,desde,hasta);
	}


	public Reserva getReservaAt(int indice){
		return listaReservas.get(indice);
	}
	@Override
	public int getColumnCount() {
		
		return columnas.getColumnCount();
	}

	@Override
	public int getRowCount() {
		
		return listaReservas.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Reserva a = listaReservas.get(fila);
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
		
		listaReservas = DAOReservas.getReservasRecurso(recurso);
		
		this.fireTableDataChanged();
	}
	public void actualizarPorCambioDeFechas(LocalDateTime desde, LocalDateTime hasta){
		listaReservas = DAOReservas.getReservasRecursoEntreFechas(recurso, desde, hasta);
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
