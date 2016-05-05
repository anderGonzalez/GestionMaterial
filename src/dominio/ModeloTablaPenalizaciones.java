package dominio;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import negocio.Sesion;
import persistencia.DAOPenalizaciones;
import persistencia.DAOPersonas;
import presentación.ModeloColumnasTablaPenalizaciones;

public class ModeloTablaPenalizaciones extends AbstractTableModel {
	
	final int ADMINISTRADOR = 1;
	ModeloColumnasTablaPenalizaciones columnas;
	ArrayList<Penalizacion> listaPenalizacion;
	
	public ModeloTablaPenalizaciones (ModeloColumnasTablaPenalizaciones columnas) throws Exception{
		super();
		listaPenalizacion = new ArrayList<Penalizacion>();
		this.columnas = columnas;
		if(Sesion.getInstance().getUsuario().idTipoUsuario == ADMINISTRADOR){
			listaPenalizacion = DAOPenalizaciones.obtenerPenalizaciones();
		}else{
			listaPenalizacion =  DAOPenalizaciones.buscarPorDni(""+Sesion.getInstance().getUsuario().getId());

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
		if(listaPenalizacion != null){
			return listaPenalizacion.size();
		}
		return 0;
	}

	@Override
	public Object getValueAt(int fila, int columna) {
		Penalizacion a = listaPenalizacion.get(fila);
		try {
			return getFieldAt(a,columna);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		
		if(Sesion.getInstance().getUsuario().getIdTipoUsuario() == ADMINISTRADOR){
			listaPenalizacion = DAOPenalizaciones.buscarPorDni("" + Sesion.getInstance().getUsuario().getId());
			
		}else{
			listaPenalizacion = DAOPenalizaciones.obtenerPenalizaciones();
		}
		
		this.fireTableDataChanged();
	}
	
	public Object getFieldAt(Penalizacion penalizacion,int columna) throws Exception {
		switch (columna){
		case 0: return DAOPersonas.buscarPorId( penalizacion.getDni()).getNombre();
		case 1: return penalizacion.getIdPrestamo();
		case 2: return penalizacion.getfInicio();
		case 3: return penalizacion.getfFinal();
	
		}
		return null;
	}
}
