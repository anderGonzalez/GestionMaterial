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
import java.beans.PropertyChangeListener;
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
	private JDateChooser dchFin = null;
	
	public DialogoLlevar(JFrame frame, boolean modo){
		super (frame,TITULO,modo);
		crearVentana();
		this.setVisible(true);
	}
	
	public DialogoLlevar(JDialog frame, boolean modo){
		super (frame,TITULO,modo);
		crearVentana();
		this.setVisible(true);
	}

	private void crearVentana() {
		this.setLocation(280,200);
		this.setSize(300, 150);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private Container crearPanelDialogo() {
		JPanel pDialogo = new JPanel(new BorderLayout());
		pDialogo.add(crearCampoFecha("Fecha devolución", LocalDate.now()),BorderLayout.CENTER);
		pDialogo.add(crearPanelBotones(),BorderLayout.SOUTH);
		return pDialogo;
	}
	
	private Component crearPanelBotones() {
		JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER,30,0));
		JButton bOk = new JButton ("Validar");
		bOk.setActionCommand("OK");
		bOk.addActionListener(this);
		JButton bCancel = new JButton ("Cancelar");
		bCancel.setActionCommand("Cancelar");
		bCancel.addActionListener(this);
		
		panel.add(bOk);
		panel.add(bCancel);
		return panel;
	}

	private JDateChooser crearCampoFecha(String titulo,LocalDate fecha) {
		
		Date date = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String dateFormat ="yyyy-MM-dd  HH:mm";
		dchFin = new JDateChooser(date,dateFormat);
		
		dchFin.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.CYAN),titulo));
		dchFin.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,18));
		dchFin.addPropertyChangeListener(this);
		
		return dchFin;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if ("date".equals(e.getPropertyName())) {
			
			Instant instant = Instant.ofEpochMilli(dchFin.getDate().getTime());
			long millis = instant.toEpochMilli();
			fFin = Calendar.getInstance();
			fFin.setTimeInMillis(millis);
		}
	}

	public Calendar getFechaFinal() {
		if(!cambioHecho)return null;
		return fFin;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
			case "OK":
				cambioHecho = true;
				break;
			case "Cancelar":
				cambioHecho = false;
				break;
		}
		this.dispose();
	}
}
