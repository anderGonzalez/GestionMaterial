package presentación;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dominio.Persona;
import dominio.RecursoExtendido;
import persistencia.DAOPersonas;
import persistencia.DAORecursos;

public class DialogoLlevar extends JDialog implements ActionListener,PropertyChangeListener{
	
	final static String  TITULO = "Llevar recurso";
	private Boolean cambioHecho = false;
	private Calendar fFin;
	private JDateChooser dchFin;
	
	public DialogoLlevar(JFrame frame, boolean modo){
		super (frame,TITULO,modo);
		crearVentana();
		this.setVisible(true);
	}

	private void crearVentana() {
		this.setLocation(280,200);
		this.setSize(300, 380);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private Container crearPanelDialogo() {
		JPanel pDialogo = new JPanel(new BorderLayout());
		pDialogo.add(fFin = crearCampoFecha("Fecha devolución", LocalDate.now()),BorderLayout.CENTER);
		return pDialogo;
	}
	
	private JDateChooser crearCampoFecha(String titulo,LocalDate fecha) {
		
		Date date = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String dateFormat ="yyyy-MM-dd  HH:mm";
		JDateChooser campoFecha = new JDateChooser(date,dateFormat);
		
		campoFecha.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.CYAN),titulo));
		campoFecha.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,18));
		return campoFecha;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if ("date".equals(e.getPropertyName())) {
			Instant instant = Instant.ofEpochMilli(dchFin.getDate().getTime());
			instant.toEpochMilli()
			fFin = Calendar.getInstance().setTimeInMillis(instant.toEpochMilli());
		}
	}

	public Calendar getFechaFinal() {
		if(!cambioHecho)return null;
		return fFin;
	}
}
