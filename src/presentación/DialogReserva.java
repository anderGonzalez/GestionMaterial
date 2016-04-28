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

import dominio.ModeloTablaReservas;
import dominio.RecursoExtendido;
import dominio.Reserva;
import negocio.Sesion;
import persistencia.DAOPrestamos;
import persistencia.DAORecursos;
import persistencia.DAOReservas;


public class DialogReserva extends JDialog implements ListSelectionListener {
	
	JMenuBar barra;
	JMenu	menuReservas,  menuSalir;
	JMenuItem opcionMenu;

	AbstractAction accAdd,accDelete,accEdit,accTake;
	RecursoExtendido recurso;
	JTable vTabla;
	ModeloColumnasTablaReservas columnas;
	TrazadorTablaReservas trazador;
	ModeloTablaReservas tabla;
	
	public DialogReserva (JFrame ventana, RecursoExtendido recurso){
		super (ventana,"Reservas",true);
		this.recurso = recurso;
		this.setLocation(200,100);
		this.setSize(600, 450);
		this.crearAcciones();
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
		panel.add(crearPanelRecurso(),BorderLayout.NORTH);
		panel.add(crearPanelTabla(),BorderLayout.CENTER);
		return panel;
	}

	private void crearAcciones() {
		accAdd = new MiAccion ("Añadir", new ImageIcon("iconos/edit_add.png"), "Añadir", KeyEvent.VK_A);
		accDelete = new MiAccion ("Borrar",new ImageIcon("iconos/edit_remove.png"),"Borrar",KeyEvent.VK_D);
		accEdit = new MiAccion ("Editar",new ImageIcon("iconos/edit.png"),"Editar",KeyEvent.VK_E);
		accTake = new MiAccion ("Llevar",new ImageIcon("iconos/agt_login.png"),"Llevar",KeyEvent.VK_P);
	}

	private JToolBar crearToolBar() {
		JToolBar toolBar = new JToolBar();
		JButton boton;
		toolBar.setBorder(BorderFactory.createRaisedBevelBorder());
		toolBar.add(accAdd);
		toolBar.add(accDelete);
		toolBar.add(accEdit);
		
		toolBar.addSeparator(new Dimension (20,0));
		
		toolBar.add(accTake);
		
		toolBar.add(Box.createHorizontalGlue());
		
		boton =(JButton) toolBar.add(new JButton (new ImageIcon("iconos/exit.png")));
		boton.setActionCommand("salir");
		boton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				DialogReserva.this.dispose();
			}
			
		});
		return toolBar;
	}

	private JMenuBar crearBarraMenu() {
		barra = new JMenuBar();
		barra.add (crearMenuReservas());
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
				DialogReserva.this.dispose();
			}
		});
		return menuSalir;
	}

	private JMenu crearMenuReservas() {
		
		menuReservas = new JMenu ("Reservas");
		menuReservas.setMnemonic(KeyEvent.VK_R);
		
		opcionMenu = menuReservas.add (accAdd);
		opcionMenu = menuReservas.add (accDelete);
		opcionMenu = menuReservas.add(accEdit);
		
		menuReservas.addSeparator();
		
		
		opcionMenu = menuReservas.add(accTake);
	
		
		return menuReservas;
		
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
		trazador = new TrazadorTablaReservas();
		columnas = new ModeloColumnasTablaReservas (trazador);
		try {
			tabla = new ModeloTablaReservas(columnas,recurso);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vTabla = new JTable(tabla,columnas);
		vTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vTabla.getSelectionModel().addListSelectionListener(this);
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

	private Component crearPanelRecurso() {
		JPanel panel = new JPanel (new GridLayout(2,2,20,20));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10,10,0,10),
				BorderFactory.createLoweredBevelBorder()));
		panel.add(crearCampo("Nombre",recurso.getNombre()));
		panel.add(crearCampo("Descripción",recurso.getDescripción()));
		panel.add(crearCampo("Ubicación",recurso.getUbicación()));
		panel.add(crearCampo("Responsable",recurso.getNombreResponsable()));
		return panel;
	}
	
	private JPanel crearCampo(String etiqueta, String valor) {
		JPanel panel = new JPanel (new FlowLayout(FlowLayout.LEFT,20,0));
		JLabel labelEtiqueta = new JLabel (etiqueta);
		JLabel labelValor = new JLabel(valor);
		labelValor.setFont (new Font("Arial",Font.BOLD|Font.ITALIC,16));
		labelValor.setForeground(Color.DARK_GRAY);
		panel.add(labelEtiqueta);
		panel.add(labelValor);
		return panel;
	}

	private class MiAccion extends AbstractAction {
		
		public MiAccion (String texto, Icon imagen, String descrip, Integer nemonic){
			super(texto,imagen);
			
			this.putValue( Action.SHORT_DESCRIPTION ,descrip);
			this.putValue(Action.MNEMONIC_KEY, nemonic);
			this.putValue(ACTION_COMMAND_KEY, texto);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch (e.getActionCommand()){
			case "Añadir": tratarOpciónAñadir(); break;
			case "Borrar": tratarOpciónBorrar(); break;
			case "Editar": tratarOpciónEditar();break;
			case "Llevar": System.out.println("Ha elegido Llevar");break;
			}
	
		}

		private void tratarOpciónEditar() {
			int index = vTabla.getSelectedRow();
			Reserva reserva = tabla.getReservaAt(index);
			DialogoDatosReserva dialogo = new DialogoDatosReserva (DialogReserva.this,"Modificar Reserva", Sesion.getInstance().getUsuario(),
												recurso, tabla.getReservaAt(index), true);
			if (dialogo.isCambioRealizado()){
				 try {
					tabla.actualizar();
					vTabla.setRowSelectionInterval(index, index);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
		    }
		
		}

		private void tratarOpciónBorrar() {
			Reserva reserva = tabla.getReservaAt(vTabla.getSelectedRow());
			   int opcion =JOptionPane.showConfirmDialog(DialogReserva.this, "Vas a eliminar la reserva de: "+recurso.getNombre(),
					   "Eliminar reserva", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			   if (opcion == JOptionPane.OK_OPTION){
				   try{
					   DAOReservas.eliminarReserva(reserva.getId());
					  
					   tabla.actualizar();
					   if (tabla.getRowCount()>0)
						   vTabla.setRowSelectionInterval(0, 0);
				   }catch(Exception e2){
					   e2.printStackTrace();
				   }
			   }
		
		}

		private void tratarOpciónAñadir() {
			DialogoDatosReserva dialogo = new DialogoDatosReserva (DialogReserva.this,"Añadir nueva reserva",Sesion.getInstance().getUsuario(), recurso, true);
			if (dialogo.isCambioRealizado()){
				try {
					tabla.actualizar();
					if (tabla.getRowCount()>0)
						vTabla.setRowSelectionInterval(0, 0);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		int index = vTabla.getSelectedRow();
		if (index == -1){
			accDelete.setEnabled(false);
			accEdit.setEnabled(false);
		}else if (tabla.getReservaAt(index).getPersona().getId()!=Sesion.getInstance().getUsuario().getId()){
			accDelete.setEnabled(false);
			accEdit.setEnabled(false);
		}else{
			accDelete.setEnabled(true);
			accEdit.setEnabled(true);
		}
		
	}
	
}
