package persistencia;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import dominio.Recurso;

public class DAORecursos
{ 
  public static Recurso[] getListaRecursos() throws Exception
  {
    Statement stmt;
    ResultSet result;
    String strSQL;
    int n,i;
    Recurso[] listaRec=null;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT COUNT(*) as cuenta"+
             " FROM Recurso";
      result = stmt.executeQuery(strSQL);
      result.next();
      n=result.getInt("cuenta");
      result.close();
      if(n!=0)
      {
        listaRec=new Recurso[n];
        stmt=PoolConexiones.getConexion().createStatement();
        strSQL="SELECT idRecurso, nombre, descripcion, ubicacion,dniResponsable"+
               " FROM Recurso";
        result = stmt.executeQuery(strSQL);
        result.next();
        for(i=0;i<n;i++)
        {
          listaRec[i]=new Recurso(result.getInt("idRecurso"),result.getString("nombre"),
                   result.getString("descripcion"),result.getString("ubicacion"),
                   result.getInt("dniResponsable"));
          result.next();          
        }
        result.close();
      }
      return listaRec;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  public static void InsertarRecurso ( String nombre, String ubicación, String descripción, int dniResponsable ){
	  
	  Statement stmt;
	  int result;
	  String strSQL;
	  try
	    {
	      stmt=PoolConexiones.getConexion().createStatement();
	      strSQL="INSERT INTO RECURSO "+
	             " VALUES (0,'"+nombre+"','"+descripción+"','"+ubicación+"',"+dniResponsable+")";
	      result = stmt.executeUpdate(strSQL);
	      
	    
	    }catch(SQLException e) {
	      e.printStackTrace();
	      return;
	    }
  }
	public static void eliminarRecurso(int id) throws SQLException {
		Statement stmt;
		int result;
		String strSQL;
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="DELETE FROM  RECURSO "+
	             " WHERE idRecurso = " +id;
	    result = stmt.executeUpdate(strSQL);
	}
	public static void modificarRecurso(int id, String nombre, String ubicación, 
			String descripción, int idResponsable) throws SQLException {
		
		Statement stmt;
		int result;
		String strSQL;
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="UPDATE  RECURSO "+
		       "SET NOMBRE = '"+nombre+"',"+
				"DESCRIPCION='"+descripción+"',"+
		        "UBICACION='"+ubicación+"',"+
				"DNIRESPONSABLE = " + idResponsable+
	           " WHERE idRecurso = " +id ;
	             
	    result = stmt.executeUpdate(strSQL);
	}
	
	
}

