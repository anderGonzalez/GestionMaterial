package presentación;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import dominio.Estado;

public class TrazadorTablaRecursos extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object valor,
			boolean isSelected, boolean hasFocus, int fila, int columna) {
		
		super.getTableCellRendererComponent(table, valor, isSelected, hasFocus, fila, columna);
		switch (columna ){
		case 0: super.setHorizontalAlignment(CENTER);break;
		case 1: super.setHorizontalAlignment(LEFT);break;
		case 2: super.setHorizontalAlignment(LEFT);break;
		case 3: super.setHorizontalAlignment(CENTER);break;
		case 4: super.setHorizontalAlignment(CENTER);break;
		case 5: return getImageEstado(valor); 
				
		}
		
		
		return this;
	}

	private Component getImageEstado(Object valor) {
		Estado estado = (Estado) valor;
		JLabel imagen= null;
		switch (estado){
		case DISPONIBLE: imagen =new JLabel("", new ImageIcon("iconos/agt_action_success.png"),CENTER);break;
		case PRESTADO: imagen =new JLabel("", new ImageIcon("iconos/cancel.png"),CENTER);break;
		case RESERVADO:imagen =new JLabel("", new ImageIcon("iconos/kopeteaway.png"),CENTER);break;
		}
		return imagen;
	}
}
