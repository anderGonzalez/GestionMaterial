package presentación;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import negocio.LogCtrl;
import negocio.Sesion;

public class FormLogin extends JFrame implements ActionListener
{
  protected JButton btnOK,btnSalir;
  protected JTextField txtUserName;
  protected JPasswordField txtPassword;
  protected JLabel lbUserName,lbPassword;


  
  public FormLogin()
   {
    super("Login");
    
    this.setSize(320,210);
    this.setLocation(200,200);
    
    this.setContentPane(crearPanelVentana());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
   }
    
    
    private Container crearPanelVentana() {
    	JPanel panel = new JPanel(new BorderLayout(0,10));
    	panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    	panel.add(crearPanelCampos(), BorderLayout.CENTER);
    	panel.add(crearPanelBotones(), BorderLayout.SOUTH);
    	
	 
	   return panel;
    }
	private Component crearPanelBotones() {
		JPanel panel = new JPanel (new FlowLayout(FlowLayout.CENTER, 50,0));
		
		btnOK=new JButton(" OK ");
	    btnOK.addActionListener(this);
	    panel.add(btnOK);
	    
	    btnSalir=new JButton("Salir");
	   
	    btnSalir.addActionListener(this);
	    panel.add(btnSalir);
		return panel;
	}
	private Component crearPanelCampos() {
		JPanel panel = new JPanel (new GridLayout(2,1));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.CYAN),
				BorderFactory.createEmptyBorder()));
		panel.add(crearPanelUsername());
		panel.add(crearPanelPassword());
		return panel;
	}
	private Component crearPanelPassword() {
		JPanel panel = new JPanel (new GridLayout(1,2,30,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		lbPassword=new JLabel("password:");
	    lbPassword.setHorizontalAlignment(JLabel.RIGHT);
	    panel.add(lbPassword);
	    txtPassword=new JPasswordField(10);
	    txtPassword.addActionListener(this);
	    txtPassword.setBorder(BorderFactory.createLoweredBevelBorder());
	    panel.add(txtPassword);
		return panel;
	}
	private Component crearPanelUsername() {
		JPanel panel = new JPanel (new GridLayout(1,2,30,0));
		panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	    lbUserName=new JLabel("usuario:");
	    lbUserName.setHorizontalAlignment(JLabel.RIGHT);
	    panel.add(lbUserName);
	    txtUserName=new JTextField(10);
	    txtUserName.setBorder(BorderFactory.createLoweredBevelBorder());
	    panel.add(txtUserName);
		return panel;
	}
	
    
    
  
  public void actionPerformed(ActionEvent e)
  {
    if((e.getSource()==btnOK)||(e.getSource() == this.txtPassword))
    {
      LogCtrl logador=new LogCtrl();
      if(logador.validarUser(txtUserName.getText(),String.valueOf(txtPassword.getPassword())))
      {
        new FormRecursos(Sesion.getInstance().getUsuario());
        this.setVisible(false);
        this.dispose();
      }
      else {
    	  JOptionPane.showMessageDialog(this,"Identificación no válida", "Error de identificación",
    			    JOptionPane.ERROR_MESSAGE);
      }
    }
    else if(e.getSource()==btnSalir)
    {
       System.exit(0);
    }
  }
}