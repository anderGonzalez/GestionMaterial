package dominio;
import java.util.Calendar;

public class Prestamo
{
  protected Calendar fechaInicio;
  protected Calendar fechaFin;
  protected Calendar fechaUltimaNotificicacion;
  protected Calendar fechaDevolucion;
  protected int id;
  protected int dniPrestatario;
  protected int idRecurso;
  
  
  public Prestamo(Calendar fechaInicio,Calendar fechaFin,Calendar fechaDevolucion,Calendar fechaUltimaNotificicacion,
                  int id, int dniPrestatario, int idRecurso)
  {
    this.fechaInicio=fechaInicio;
    this.fechaFin=fechaFin;
    this.id=id;
    this.dniPrestatario=dniPrestatario;
    this.idRecurso=idRecurso;
    this.fechaUltimaNotificicacion = fechaUltimaNotificicacion;
    this.fechaDevolucion = fechaDevolucion;
  }
  
  public Prestamo(Calendar fechaInicio,Calendar fechaFin,
                  int dniPrestatario, int idRecurso)
  {
    this.fechaInicio=fechaInicio;
    this.fechaFin=fechaFin;
    this.dniPrestatario=dniPrestatario;
    this.idRecurso=idRecurso;
  }
  
  public Calendar getFechaInicio(){return fechaInicio;}
  public Calendar getFechaFin(){return fechaFin;}
  public Calendar getFechaUltimaNotificicacion(){return fechaUltimaNotificicacion; }
  public Calendar getFechaDevolucion(){return fechaDevolucion; }
  public int getId(){return id;}
  public int getIdPrestatario(){return dniPrestatario;}
  public int getIdRecurso(){return idRecurso;}
  
  public void setFechaDevolucion(Calendar fechaDevolucion){
	  this.fechaDevolucion = fechaDevolucion;
  }
  
  public void setFechaUltimaNotificicacion(Calendar fechaUltimaNotificicacion){
	  this.fechaUltimaNotificicacion = fechaUltimaNotificicacion;
  }
}