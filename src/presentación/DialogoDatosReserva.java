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
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dominio.ModeloTablaReservas;
import dominio.Persona;
import dominio.RecursoExtendido;
import dominio.Reserva;
import persistencia.DAOReservas;

public class DialogoDatosReserva extends JDialog implements ActionListener,PropertyChangeListener{
	
	JTextField txNombrePersona,txNombreRecurso,txUrgencia;
	JDateChooser txDesde,txHasta;
	Persona persona;
	RecursoExtendido recurso;
	Reserva reserva;
	boolean cambioRealizado = false;
	
	boolean editando = false;
	JTable vTabla;
	ModeloColumnasTablaReservas columnas;
	TrazadorTablaReservas trazador;
	ModeloTablaReservas tabla;
	LocalDateTime desde,hasta;
	
	public DialogoDatosReserva (JDialog frame,String titulo,Persona p, RecursoExtendido recurso,
			                     boolean modo){
		super ( frame,titulo,modo );
		this.persona = p;
		this.recurso = recurso;
		desde = LocalDateTime.now();
		hasta = LocalDateTime.now();
		crearVentana();
		txNombrePersona.setText(persona.getNombre());
		txNombrePersona.setEditable(false);
		txNombreRecurso.setText(recurso.getNombre());
		txNombreRecurso.setEditable(false);
		
		this.setVisible(true);
	}
	
	private void crearVentana() {
		this.setLocation(280,200);
		this.setSize(600, 450);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public DialogoDatosReserva(JDialog frame,String titulo, Persona p, 
			                  RecursoExtendido recurso, Reserva reserva, boolean modo) {
		super(frame,titulo,modo);
		this.persona = p;
		this.recurso = recurso;
		this.reserva = reserva;
		desde = reserva.getDesde();
		hasta = reserva.getHasta();
		crearVentana();
		txNombrePersona.setText(persona.getNombre());
		txNombrePersona.setEditable(false);
		txNombreRecurso.setText(recurso.getNombre());
		txNombreRecurso.setEditable(false);
		txUrgencia.setText(String.valueOf(reserva.getUrgencia()));
		editando = true;
		
		this.setVisible(true);
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelCentral (), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
	}

	private Component crearPanelCentral() {
		JPanel panel = new JPanel(new BorderLayout(0,10));
		panel.add(crearPanelCampos(),BorderLayout.NORTH);
		panel.add(crearPanelTablaReservas(),BorderLayout.CENTER);
		return panel;
	}

	private Component crearPanelTablaReservas() {
		JScrollPane panelS = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		crearTabla();
		panelS.setViewportView(vTabla);
		return panelS;
	}
	private void crearTabla() {
		trazador = new TrazadorTablaReservas();
		columnas = new ModeloColumnasTablaReservas (trazador);
		try {
			tabla = new ModeloTablaReservas(columnas,recurso,desde,hasta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vTabla = new JTable(tabla,columnas);
		
		vTabla.setFillsViewportHeight(true);
		vTabla.getTableHeader().setReorderingAllowed(false);
		
		vTabla.setFillsViewportHeight(true);
		
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

	private Component crearPanelCampos() {
		JPanel panel = new JPanel (new GridLayout(5,1,0,20));
		panel.add(txNombrePersona = crearCampo("Persona"));
		panel.add(txNombreRecurso = crearCampo("Recurso"));
		panel.add(txDesde = crearCampoFecha("Desde",desde.toLocalDate()));
		panel.add(txHasta = crearCampoFecha ("Hasta",hasta.toLocalDate()));
		panel.add(txUrgencia = crearCampo("Urgencia"));
		txDesde.addPropertyChangeListener(this);
		txHasta.addPropertyChangeListener(this);
		return panel;
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

	private JTextField crearCampo(String titulo) {
		JTextField campo = new JTextField();
		campo.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.CYAN),titulo));
		campo.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,18));
		return campo;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()){
		case "OK" : if (editando){
					try {
						
							DAOReservas.modificarReserva(reserva.getId(),desde, hasta,
									Integer.valueOf(Integer.valueOf(txUrgencia.getText()).intValue()));
									
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						JOptionPane.showMessageDialog(this, "Reserva actualizada",
								"Accion realizada", JOptionPane.INFORMATION_MESSAGE);
					}else{
						
						DAOReservas.insertarReserva(new Reserva(0,this.persona,this.recurso, 
								this.desde, this.hasta, Integer.valueOf(txUrgencia.getText())));
						JOptionPane.showMessageDialog(this, "Reserva añadida",
								"Accion realizada", JOptionPane.INFORMATION_MESSAGE);
				
					}
					this.cambioRealizado = true;
					this.dispose();
					break;
					
		case "Cancelar":
					this.dispose();
		}
		
	}

	
	public boolean isCambioRealizado() {
		return cambioRealizado;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		
		if ("date".equals(e.getPropertyName())) {
			if (e.getSource()==txDesde){
				Instant instant = Instant.ofEpochMilli(txDesde.getDate().getTime());
				desde = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
				
			}else{
				Instant instant = Instant.ofEpochMilli(txHasta.getDate().getTime());
				hasta = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			}
			tabla.actualizarPorCambioDeFechas(desde, hasta);
        }
		
	}

	

}
