package dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import persistencia.DAOPenalizaciones;
import persistencia.DAORecursos;
import persistencia.DAOReservas;
import presentación.ModeloColumnasTablaPenalizaciones;
import presentación.ModeloColumnasTablaReservas;

public class ModeloTablaPenalizaciones extends AbstractTableModel {
	
	final int ADMINISTRADOR = 1;
	ModeloColumnasTablaPenalizaciones columnas;
	Persona persona;
	ArrayList<Penalizacion> listaPenalizacion;
	
	public ModeloTablaPenalizaciones (ModeloColumnasTablaPenalizaciones columnas, Persona persona) throws Exception{
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
		Penalizacion a = listaPenalizacion.get(fila);
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
		
		if(persona.getIdTipoUsuario() == ADMINISTRADOR){
			listaPenalizacion = DAOPenalizaciones.buscarPorDni("" + persona.id);
			
		}else{
			listaPenalizacion = DAOPenalizaciones.obtenerPenalizaciones();
		}
		
		this.fireTableDataChanged();
	}
	
	public Object getFieldAt(Penalizacion penalizacion,int columna) {
		switch (columna){
		case 0: return new Integer(penalizacion.getDni());
		case 1: return new Integer(penalizacion.getIdPrestamo());
		case 2: return penalizacion.getfInicio();
		case 3: return penalizacion.getfFinal();
	
		}
		return null;
	}
}
