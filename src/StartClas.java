import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import presentación.FormLogin;
import servicioPenalizaciones.CheckerDevolucionesRutina;

public class StartClas
{
  public static void main(String args[])
  {
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (UnsupportedLookAndFeelException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    new FormLogin();
    new CheckerDevolucionesRutina(15);
  }
}