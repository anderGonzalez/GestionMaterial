package negocio;
import persistencia.DAOPersonas;
import dominio.Persona;

public class LogCtrl
{
  public boolean validarUser(String nombre, String password)
  {
    Persona p;
    
    try
    {
      p=DAOPersonas.buscarPorNombre(nombre);
      if((p==null)||(!p.validarPassword(password))) return false;
      Sesion.getInstance().setUsuario(p);
      return true;
    }
    catch (Exception e) {e.printStackTrace(); return false;}
  }
}