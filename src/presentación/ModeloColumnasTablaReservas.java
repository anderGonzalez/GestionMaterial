package presentación;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaReservas extends DefaultTableColumnModel{
	
	TrazadorTablaReservas trazador;
	
	public ModeloColumnasTablaReservas(TrazadorTablaReservas trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("Nombre",0,100));
		this.addColumn(crearColumna("Desde",1,100));
		this.addColumn(crearColumna("Hasta",2,100));
		this.addColumn(crearColumna("Prioirdad",3,20));
	}

	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}

}