package persistencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import dominio.Prestamo;

public class DAOPrestamos
{
  static public boolean eliminarPrestamo(Prestamo p) throws Exception
  {
    String strSQL;
    Statement stmt;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="DELETE FROM Prestamo "+
             "WHERE idPrestamo ="+p.getId()+';';
      stmt.executeUpdate(strSQL);
      return true;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();;
      return false;
    }
  }
  
  static public boolean addPrestamo(Prestamo p) throws Exception
  {
    Statement stmt;
    boolean ok=false;
    String strSQL,strFechaInicio,strFechaFin;
    String strFechaDev = "null";
    String strFechaUltNot = "null";
    Calendar calendario;
    int dia,mes,año,hora,min;
    
    try
    {
      calendario=p.getFechaInicio();
      dia=calendario.get(Calendar.DAY_OF_MONTH);
      mes=calendario.get(Calendar.MONTH)+1;
      año=calendario.get(Calendar.YEAR);
      hora=calendario.get(Calendar.HOUR_OF_DAY);
      min=calendario.get(Calendar.MINUTE);
      strFechaInicio=año+"-"+mes+'-'+dia+' '+hora+':'+min;
      calendario=p.getFechaFin();
      dia=calendario.get(Calendar.DAY_OF_MONTH);
      mes=calendario.get(Calendar.MONTH)+1;
      año=calendario.get(Calendar.YEAR);
      hora=calendario.get(Calendar.HOUR_OF_DAY);
      min=calendario.get(Calendar.MINUTE);
      strFechaFin=año+"-"+mes+'-'+dia+' '+hora+':'+min;
      calendario=p.getFechaDevolucion();
      if(calendario != null){
    	  dia=calendario.get(Calendar.DAY_OF_MONTH);
          mes=calendario.get(Calendar.MONTH)+1;
          año=calendario.get(Calendar.YEAR);
          hora=calendario.get(Calendar.HOUR_OF_DAY);
          min=calendario.get(Calendar.MINUTE);
          strFechaDev=año+"-"+mes+'-'+dia+' '+hora+':'+min;
      }
      
      calendario=p.getFechaUltimaNotificicacion();
      if(calendario != null){
    	  dia=calendario.get(Calendar.DAY_OF_MONTH);
          mes=calendario.get(Calendar.MONTH)+1;
          año=calendario.get(Calendar.YEAR);
          hora=calendario.get(Calendar.HOUR_OF_DAY);
          min=calendario.get(Calendar.MINUTE);
          strFechaUltNot=año+"-"+mes+'-'+dia+' '+hora+':'+min;
      }
      
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="INSERT INTO Prestamo(fechaInicio,fechaFin,fechaDevolucion,fechaUltimaNotificacion,idRecurso,dniPrestatario) "+
        "VALUES ('"+strFechaInicio+"','"+strFechaFin+"','"+strFechaDev+"','"+strFechaUltNot+"',"+
                  p.getIdRecurso()+"," +p.getIdPrestatario()+")";
      stmt.executeUpdate(strSQL);
      return true;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();;
      return false;
    }
  }
  
  static public Prestamo buscarPorId(int idPrestamo) throws Exception
  {
    
    Statement stmt;
    ResultSet result;
    String strSQL;
    Prestamo p;
    Calendar calIni,calFin,calUlt,calDev;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT fechaInicio,fechaFin,fechaDevolucion,fechaUltimaNotificacion,idRecurso,dniPrestatario,idPrestamo"+
             " FROM Prestamo"+
             " WHERE idPrestamo="+idPrestamo;
      result = stmt.executeQuery(strSQL);
      if(!result.next()) return null;
      calIni=Calendar.getInstance();
      calFin=Calendar.getInstance();
      calUlt=Calendar.getInstance();
      calDev=Calendar.getInstance();
      calIni.setTime(result.getDate("fechaInicio",calIni));
      calFin.setTime(result.getTimestamp("fechaFin",calFin));
      try{
    	  calUlt.setTime(result.getTimestamp("fechaUltimaNotificacion",calUlt));
      }catch(NullPointerException e){
    	  calUlt.set(Calendar.YEAR, 2001);
    	  calUlt.set(Calendar.MONTH, Calendar.SEPTEMBER);
    	  calUlt.set(Calendar.DAY_OF_MONTH, 11);  	  
      }
      try{
    	  calDev.setTime(result.getTimestamp("fechaDevolucion",calDev));
      }catch(NullPointerException e){
    	  calDev = null;  
      }
      p=new Prestamo(calIni,calFin,calDev,calUlt,
                     result.getInt("idPrestamo"),
                     result.getInt("dniPrestatario"),
                     result.getInt("idRecurso"));
      result.close();
      return p;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  
  static public Prestamo buscarPorIdRecurso(int idRecurso) throws Exception
  {
    Statement stmt;
    ResultSet result;
    String strSQL;
    Prestamo p;
    Calendar calIni,calFin,calUlt,calDev;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT fechaInicio,fechaFin,fechaUltimaNotificacion,fechaDevolucion ,idRecurso,dniPrestatario,idPrestamo"+
             " FROM Prestamo"+
             " WHERE idRecurso="+idRecurso+';';
      result = stmt.executeQuery(strSQL);
      if(!result.next()) return null;
      
      calIni=Calendar.getInstance();
      calFin=Calendar.getInstance();
      calUlt=Calendar.getInstance();
      calDev=Calendar.getInstance();
      
      calIni.setTime(result.getDate("fechaInicio",calIni));
      calFin.setTime(result.getTimestamp("fechaFin",calFin));
      try{
    	  calUlt.setTime(result.getTimestamp("fechaUltimaNotificacion",calUlt));
      }catch(NullPointerException e){
    	  calUlt.set(Calendar.YEAR, 2001);
    	  calUlt.set(Calendar.MONTH, Calendar.SEPTEMBER);
    	  calUlt.set(Calendar.DAY_OF_MONTH, 11);  	  
      }
      try{
    	  calDev.setTime(result.getTimestamp("fechaDevolucion",calDev));
      }catch(NullPointerException e){
    	  calDev = null;  
      }
     
      p=new Prestamo(calIni,calFin,calDev,calUlt,
                     result.getInt("idPrestamo"),
                     result.getInt("dniPrestatario"),
                     result.getInt("idRecurso"));
      result.close();
      return p;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }

  //
	public static boolean estaPrestado(int id, LocalDateTime ahora) {
		Statement stmt;
	    ResultSet result;
	    String strSQL;
	    String strDateTime;
	    boolean resultado = false;
	    try
	    {
	      stmt=PoolConexiones.getConexion().createStatement();
	      DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	      strDateTime = ahora.format(formatter);
	      strSQL="SELECT count(*)" +
	             " FROM Prestamo"+
	             " WHERE (idRecurso="+id+") and ('"+
	             strDateTime +"' BETWEEN fechaInicio AND fechaFin) and (fechaDevolucion is not null)";
	      result = stmt.executeQuery(strSQL);
	      if(!result.next()) throw new Exception("sentencia errónea: " + strSQL);
	      resultado= (result.getInt(1) != 0);
	     
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    }
	      return resultado;
	}

	public static void eliminarPrestamosRecurso(int id) throws SQLException {
		Statement stmt;
		int result;
		String strSQL;
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="DELETE FROM  PRESTAMO "+
	             " WHERE idRecurso = "+ id;
	    result = stmt.executeUpdate(strSQL);
		
	}
	
	public static boolean actualizarFechasPrestamo(Prestamo p){
		Statement stmt;
		String strSQL;
		String strFechaDev = "NULL";
	    String strFechaUltNot = "NULL";
	    Calendar calendario;
	    int dia,mes,año,hora,min;
	    
	    calendario=p.getFechaDevolucion();
	      if(calendario != null){
	    	  dia=calendario.get(Calendar.DAY_OF_MONTH);
	          mes=calendario.get(Calendar.MONTH)+1;
	          año=calendario.get(Calendar.YEAR);
	          hora=calendario.get(Calendar.HOUR_OF_DAY);
	          min=calendario.get(Calendar.MINUTE);
	          strFechaDev="'" +año+"-"+mes+"-"+dia+" "+hora+":"+min + "'";
	      }
	      
	      calendario=p.getFechaUltimaNotificicacion();
	      if(calendario != null){
	    	  dia=calendario.get(Calendar.DAY_OF_MONTH);
	          mes=calendario.get(Calendar.MONTH)+1;
	          año=calendario.get(Calendar.YEAR);
	          hora=calendario.get(Calendar.HOUR_OF_DAY);
	          min=calendario.get(Calendar.MINUTE);
	          strFechaUltNot="'" +año+"-"+mes+"-"+dia+" "+hora+":"+min + "'";
	      }
	    
		try {
			stmt=PoolConexiones.getConexion().createStatement();
			strSQL="UPDATE PRESTAMO"+
					" SET fechaUltimaNotificacion = "+strFechaUltNot+","+
					" fechaDevolucion = " + strFechaDev +
		             " WHERE idPrestamo = "+ p.getId();
		    stmt.executeUpdate(strSQL);
		    return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ArrayList<Prestamo> buscarPrestamosNoDevueltos() {
		Statement stmt;
	    ResultSet result;
	    String strSQL;
	    ArrayList<Prestamo> resultado = new ArrayList<>();
	    try
	    {
	      stmt=PoolConexiones.getConexion().createStatement();
	      strSQL="SELECT idPrestamo" +
	             " FROM Prestamo"+
	             " WHERE fechaDevolucion is null";
	      result = stmt.executeQuery(strSQL);
	      if(!result.next()) throw new Exception("sentencia errónea: " + strSQL);
				do {
					resultado.add(buscarPorId(result.getInt("idPrestamo")));
				} while (result.next());
			
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    }
	      return resultado;
	}
}
