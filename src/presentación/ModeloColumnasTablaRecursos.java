package presentación;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaRecursos extends DefaultTableColumnModel{
	
	TrazadorTablaRecursos trazador;
	
	public ModeloColumnasTablaRecursos(TrazadorTablaRecursos trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID",0,50));
		this.addColumn(crearColumna("Nombre",1,100));
		this.addColumn(crearColumna("Descripción",2,200));
		this.addColumn(crearColumna("Ubicación",3,100));
		this.addColumn(crearColumna("Responsable",4,100));
		this.addColumn(crearColumna("Estado",5,50));
	}

	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}

}

