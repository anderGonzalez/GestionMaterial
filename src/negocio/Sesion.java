package negocio;
import dominio.Persona;

public class Sesion {
	
  protected Persona p;
  private Sesion() {}
  private static Sesion instance;
  public static Sesion getInstance()
  {
    if(instance==null)
      instance=new Sesion();
    return instance;
  }
  public void setUsuario(Persona p){this.p=p;}
  public Persona getUsuario() {return p;}
}