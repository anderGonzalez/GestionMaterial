package presentación;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dominio.ModeloTablaPenalizaciones;
import dominio.ModeloTablaReservas;
import dominio.Penalizacion;
import dominio.Persona;
import dominio.RecursoExtendido;
import dominio.Reserva;
import negocio.Sesion;
import persistencia.DAOPrestamos;
import persistencia.DAORecursos;
import persistencia.DAOReservas;


public class DialogoPenalizaciones extends JDialog {
	

	private static final long serialVersionUID = 1L;
	final int ADMINISTRADOR = 1;
	JMenuBar barra;
	JMenu	menuPenalizaciones,  menuSalir;
	JMenuItem opcionMenu;

	AbstractAction accEdit;
	JTable vTabla;
	ModeloColumnasTablaPenalizaciones columnas;
	TrazadorTablaPenalizaciones trazador;
	ModeloTablaPenalizaciones tabla;
	
	public DialogoPenalizaciones (JFrame ventana){
		super (ventana,"Penalizaciones",true);
		this.setLocation(200,100);
		this.setSize(600, 450);
		this.crearAcciones();
		this.darpermisos();
		this.setJMenuBar(crearBarraMenu());
		this.setContentPane(crearPanelVentana());
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	
	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout(0,10));
		panel.add(crearToolBar(),BorderLayout.NORTH);
		panel.add(crearPanelDatos(),BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		return panel;
	}

	private Container crearPanelDatos() {
		JPanel panel = new JPanel (new BorderLayout(0,20));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		panel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.CYAN), "Datos Recurso"));
		panel.add(crearPanelTabla(),BorderLayout.CENTER);
		return panel;
	}
	private void darpermisos() {
		if (Sesion.getInstance().getUsuario().getIdTipoUsuario() != ADMINISTRADOR ){
			accEdit.setEnabled(false);
		}
		
	}

	private void crearAcciones() {
		
		accEdit = new MiAccion ("Editar",new ImageIcon("iconos/edit.png"),"Editar",KeyEvent.VK_E);
	}

	private JToolBar crearToolBar() {
		JToolBar toolBar = new JToolBar();
		JButton boton;
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		
		toolBar.add(accEdit);
		
		toolBar.addSeparator(new Dimension (20,0));
				
		toolBar.add(Box.createHorizontalGlue());
		
		boton =(JButton) toolBar.add(new JButton (new ImageIcon("iconos/exit.png")));
		boton.setActionCommand("salir");
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				DialogoPenalizaciones.this.dispose();
			}
			
		});
		return toolBar;
	}

	private JMenuBar crearBarraMenu() {
		barra = new JMenuBar();
		barra.add (crearMenuPenalizaciones());
		barra.add(Box.createHorizontalGlue());
		barra.add (crearMenuSalir());
		
		return barra;
		
	}

	private JMenu crearMenuSalir() {
		JMenuItem op;
		menuSalir = new JMenu ("Salir");
		
		op=menuSalir.add("Salir");
		op.setIcon(new ImageIcon("iconos/exit.png"));
		op.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e){
				DialogoPenalizaciones.this.dispose();
			}
		});
		return menuSalir;
	}

	private JMenu crearMenuPenalizaciones() {
		
		menuPenalizaciones = new JMenu ("Penalizaciones");
		menuPenalizaciones.setMnemonic(KeyEvent.VK_R);
		
		opcionMenu = menuPenalizaciones.add(accEdit);
		
		menuPenalizaciones.addSeparator();	
		
		return menuPenalizaciones;
		
	}
	private Component crearPanelTabla() {
		JPanel panel = new JPanel(new BorderLayout(10,0));
		panel.add(crearPanelTitulo(),BorderLayout.NORTH);
		panel.add(crearScrollTabla(),BorderLayout.CENTER);
		return panel;
	}

	private Component crearScrollTabla() {
		JScrollPane panelS = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		crearTabla();
		panelS.setViewportView(vTabla);
		return panelS;
	}
	private void crearTabla() {
		trazador = new TrazadorTablaPenalizaciones();
		columnas = new ModeloColumnasTablaPenalizaciones(trazador);
		try {
			tabla = new ModeloTablaPenalizaciones(columnas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vTabla = new JTable(tabla,columnas);
		vTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vTabla.setFillsViewportHeight(true);
		vTabla.getTableHeader().setReorderingAllowed(false);
		if (tabla.getRowCount()>0)
		     vTabla.setRowSelectionInterval(0, 0);
		vTabla.setFillsViewportHeight(true);
		
	}
	private Component crearPanelTitulo() {
		JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER));
		panel.setBorder (BorderFactory.createRaisedBevelBorder());
		panel.add(new JLabel("Reservas recurso"));
		return panel;
	}


	private class MiAccion extends AbstractAction {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MiAccion (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
			this.putValue(ACTION_COMMAND_KEY, texto);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			tratarOpciónEditar();
	
		}
	}

		private void tratarOpciónEditar() {
			int index = vTabla.getSelectedRow();
			Penalizacion penalizacion = tabla.getPenalizacionAt(index);
			System.out.println(penalizacion.getDni() + " " + penalizacion.getfInicio());
			DialogoPenalizacionesEditar dialogo = new DialogoPenalizacionesEditar (DialogoPenalizaciones.this 
									,penalizacion, true);
			if (dialogo.isCambioRealizado()){
				 try {
					tabla.actualizar();
					vTabla.setRowSelectionInterval(index, index);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		
	}
}
