package dominio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import persistencia.DAORecursos;
import persistencia.DAOReservas;
import presentación.ModeloColumnasTablaRecursos;

public class ModeloTablaRecursos extends AbstractTableModel {
	
	ModeloColumnasTablaRecursos columnas;
	
	ArrayList<RecursoExtendido> listaRecursos;
	
	public ModeloTablaRecursos (ModeloColumnasTablaRecursos columnas) throws Exception{
		super();
		this.columnas = columnas;
		DAOReservas.cleanReservas();
		listaRecursos = new ArrayList<>();
		Recurso recursos[] = DAORecursos.getListaRecursos();
		crearListaRecursos(recursos);
	}

	private void crearListaRecursos(Recurso[] recursos) throws Exception {
		for (Recurso rec : recursos){
			listaRecursos.add(new RecursoExtendido(rec));
		}
		
	}
	public RecursoExtendido getRecursoAt(int indice){
		return listaRecursos.get(indice);
	}
	@Override
	public int getColumnCount() {
		
		return columnas.getColumnCount();
	}

	@Override
	public int getRowCount() {
		
		return listaRecursos.size();
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		RecursoExtendido a = listaRecursos.get(fila);
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
		
		listaRecursos = new ArrayList<>();
		Recurso recursos[] = DAORecursos.getListaRecursos();
		crearListaRecursos(recursos);
		this.fireTableDataChanged();
	}
	public Object getFieldAt(RecursoExtendido rec,int columna) {
		switch (columna){
		case 0: return new Integer (rec.getId());
		case 1: return rec.getNombre();
		case 2: return rec.getDescripción();
		case 3: return rec.getUbicación();
		case 4: return rec.getNombreResponsable();
		case 5: return rec.getEstado();
		}
		return null;
	}
}