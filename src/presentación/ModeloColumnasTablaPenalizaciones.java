package presentación;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnasTablaPenalizaciones extends DefaultTableColumnModel{
	
	TrazadorTablaPenalizaciones trazador;
	
	public ModeloColumnasTablaPenalizaciones(TrazadorTablaPenalizaciones trazador){
		super();
		this.trazador = trazador;
		this.addColumn(crearColumna("ID Alumno",0,50));
		this.addColumn(crearColumna("ID Recurso",1,50));
		this.addColumn(crearColumna("Desde",2,100));
		this.addColumn(crearColumna("Hasta",3,100));
	}

	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice,ancho);
		
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(trazador);
		
		return columna;
	}

}