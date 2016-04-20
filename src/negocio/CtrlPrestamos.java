package negocio;
import persistencia.*;
import dominio.*;
import java.util.Calendar;

public class CtrlPrestamos
{  
  public Recurso[] getListaRecursos()
  {
    Recurso[] lista;
    
    try
    {
      return DAORecursos.getListaRecursos();
    }
    catch (Exception e) {return null;}
  }
  
  public Persona getPrestatario(Prestamo prestamo)
  {
    try
    {
      return DAOPersonas.buscarPorId(prestamo.getIdPrestatario());
    }
    catch(Exception e) {return null;}
  }
  
  public Prestamo getPrestamo(int idRecurso)
  {
    Prestamo prest;
     
    try
    {
      return DAOPrestamos.buscarPorIdRecurso(idRecurso);
    }
    catch (Exception e) {return null;}
  }
  
  public boolean coger(int idRecurso, Calendar calIni, Calendar calFin)
  {
    Prestamo prest;
   
    try
    {
      if((prest=getPrestamo(idRecurso))!=null) return false;
      prest=new Prestamo(calIni,calFin,
              Sesion.getInstance().getUsuario().getId(),idRecurso);
      DAOPrestamos.addPrestamo(prest);
    }
    catch (Exception e) {return false;}
    return true;
  }
  
  public boolean devolver(int idRecurso)
  {
    Prestamo prest;
     
    try
    {
      if((prest=DAOPrestamos.buscarPorIdRecurso(idRecurso))==null) return false;
      if(prest.getIdPrestatario()!=Sesion.getInstance().getUsuario().getId())
        return false;
      if(!DAOPrestamos.eliminarPrestamo(prest)) return false;
    }
    catch (Exception e) {return false;}
    return true;
  }
}