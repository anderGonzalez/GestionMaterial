package dominio;

public class Recurso
{
  protected int id;
  protected String nombre;
  protected String descripción;
  protected String ubicación;
  protected int idResponsable;
  
  public Recurso(int id, String nombre, String descripción,String ubicación, int idResponsable)
  {
    this.ubicación=ubicación;
    this.nombre=nombre;
    this.descripción=descripción;
    this.id=id;
    this.idResponsable = idResponsable;
  }
  public Recurso (Recurso r){
	  this.id = r.id;
	  this.nombre = r.nombre;
	  this.descripción = r.descripción;
	  this.ubicación = r.ubicación;
	  this.idResponsable = r.idResponsable;
  }
  public String getNombre(){return nombre;}
  public String getDescripción(){return descripción;}
  public int getId(){return id;}
  public String getUbicación(){return ubicación;}
  public int getIdResponsable(){return idResponsable;}
}