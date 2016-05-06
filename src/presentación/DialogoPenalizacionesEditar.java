package presentación;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dominio.Penalizacion;
import dominio.Persona;
import persistencia.DAOPenalizaciones;

public class DialogoPenalizacionesEditar extends JDialog implements ActionListener{
	
	final static String  TITULO = "EDITAR PENALIZACIONES";
	JComboBox<String> comboResponsable;
	JTextField txIdPersona,txIdPrestamo;
	JDateChooser txFInicio,txFFinal;
	ArrayList<Persona> listaPersonas= null; 
	boolean cambioRealizado = false;
	Penalizacion penalizacionAntes = null, penalizacionesDespues = null;
	
	private void crearVentana() {
		this.setLocation(280,200);
		this.setSize(300, 380);
		this.setContentPane(crearPanelDialogo());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public DialogoPenalizacionesEditar(JDialog dialog, Penalizacion penalizacion, boolean modo) {
		super(dialog, TITULO, modo);
		this.penalizacionAntes = penalizacion;

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		crearVentana();
		txIdPersona.setText("" + penalizacion.getDni());
		txIdPrestamo.setText("" + penalizacion.getIdPrestamo());
		
		this.setVisible(true);
	}

	private Container crearPanelDialogo() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.add(crearPanelCampos (), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		return panel;
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
		JPanel panel = new JPanel (new GridLayout(4,1,0,20));
		String dateFormat ="yyyy-MM-dd  HH:mm";
		txFInicio = new JDateChooser(penalizacionAntes.getfInicio().getTime(), dateFormat);
		txFFinal = new JDateChooser(penalizacionAntes.getfFinal().getTime(), dateFormat);

		panel.add(txIdPersona = crearCampo("Id Persona"));
		panel.add(txIdPrestamo = crearCampo("Id Prestamo"));
		panel.add(txFInicio );
		panel.add(txFFinal);
		
		return panel;
	}

	private JTextField crearCampo(String titulo) {
		JTextField campo = new JTextField();
		campo.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.CYAN),titulo));
		
		return campo;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()){
		case "OK" : if (camposIncompletos()){
						JOptionPane.showMessageDialog(this, "Es necesario rellenar todos los campos",
								"Error datos incompletos", JOptionPane.ERROR_MESSAGE);
					}else{
						penalizacionesDespues = new Penalizacion(this.getTxIdPersona()
								,this.getTxFInicio(), this.getTxFFinal(),  this.getTxIdPrestamo());
						DAOPenalizaciones.updatePenalizacion(penalizacionAntes, penalizacionesDespues);
						JOptionPane.showMessageDialog(this, "Recurso actualizado",
								"Accion realizada", JOptionPane.INFORMATION_MESSAGE);
						cambioRealizado = true;

					}
						
					this.dispose();
					break;
					
		case "Cancelar":
					this.dispose();
		}
		
	}

	public Calendar getTxFInicio() {
		return txFInicio.getCalendar();
	}

	
	public Calendar getTxFFinal() {
		return txFFinal.getCalendar();
	}

	public Integer getTxIdPersona() {
		return new Integer(txIdPersona.getText());
	}

	public Integer getTxIdPrestamo() {
		return new Integer(txIdPrestamo.getText());
	}



	private boolean camposIncompletos() {
		
		return txIdPersona.getText().length()==0  || txIdPrestamo.getText().length()==0;
	}

	public boolean isCambioRealizado() {
		return cambioRealizado;
	}

	
}
