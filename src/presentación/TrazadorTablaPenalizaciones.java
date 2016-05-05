package presentación;

import java.awt.Component;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import dominio.Estado;

public class TrazadorTablaPenalizaciones extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object valor,
			boolean isSelected, boolean hasFocus, int fila, int columna) {
		
		super.getTableCellRendererComponent(table, valor, isSelected, hasFocus, fila, columna);
		switch (columna ){
		case 0: super.setHorizontalAlignment(LEFT);break;
		case 1: super.setHorizontalAlignment(CENTER);break;
		case 2: super.setText(formatearFecha(valor));
	    		super.setHorizontalAlignment(CENTER);break;
		case 3: super.setText(formatearFecha(valor));
				super.setHorizontalAlignment(CENTER);break;
		}
		
		
		return this;
	}

	private String formatearFecha(Object valor) {
		GregorianCalendar grCa =(GregorianCalendar) valor;
		ZonedDateTime zdt = grCa.toZonedDateTime();
		LocalDateTime fecha = zdt.toLocalDateTime();
		DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	    return fecha.format(formatter);
		
	}


}
