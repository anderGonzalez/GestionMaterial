package dominio;

public class Persona
{
  protected String userName;
  protected String nombre;
  protected String password;
  protected int id;
  protected int idTipoUsuario;
  
  public Persona(String userName, String nombre,String password,int id,int tipo)
  {
    this.userName=userName;
    this.nombre=nombre;
    this.password=password;
    this.id=id;
    this.idTipoUsuario = tipo;
  }
  
  public String getUserName(){return userName;}
  public String getNombre(){return nombre;}
  public String getPassword(){return password;}
  public int getId(){return id;}
  public int getIdTipoUsuario(){return idTipoUsuario;}
  public void setNombre(String nombre){ this.nombre=nombre;}
  public boolean validarPassword(String password)
  {
    return password.equals(this.password);
  }
}