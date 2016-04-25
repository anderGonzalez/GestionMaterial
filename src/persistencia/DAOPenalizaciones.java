package persistencia;
import java.sql.*;
import java.util.*;
import java.io.*;
import dominio.Persona;
import dominio.Recurso;

public class DAOPenalizaciones
{
  static public ArrayList<Persona> obtenerPersonas() throws Exception {
	Statement stmt;
    ResultSet result;
    String strSQL;
    int n,i;
    ArrayList<Persona> lista = null;
    Persona persona;
    try
    {
    	
      lista = new ArrayList<>();
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT userName,nombre,password,dni, idTipoUsuario"+
               " FROM Persona";
      result = stmt.executeQuery(strSQL);
      while (result.next()){
         persona = new Persona (result.getString("userName"),
        		 				result.getString("nombre"),
        		 				result.getString("password"),
        		 				result.getInt("dni"),
        		 				result.getInt("idTipoUsuario"));
         lista.add(persona);
      }
      result.close();
    } catch (Exception e ){
    	e.printStackTrace();
    }
      return lista;
  }
  static public Persona buscarPorId(int idPersona) throws Exception
  {
    Statement stmt;
    ResultSet result;
    String strSQL;
    Persona p;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT userName, nombre, password, dni,idTipoUsuario"+
             " FROM Persona"+
             " WHERE dni="+idPersona;
      result = stmt.executeQuery(strSQL);
      if(!result.next()) return null;
      p=new Persona(result.getString("userName"),result.getString("nombre"),
                    result.getString("password"),result.getInt("dni"),result.getInt("idTipoUsuario"));
      result.close();
      return p;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  
  static public Persona buscarPorNombre(String nombre) throws Exception
  {
    Statement stmt;
    ResultSet result;
    String strSQL;
    Persona p;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="SELECT userName, nombre, password, dni,idTipoUsuario"+
             " FROM Persona"+
             " WHERE userName='"+nombre+"'";
      result = stmt.executeQuery(strSQL);
      if(!result.next()) return null;
      p=new Persona(result.getString("userName"),result.getString("nombre"),
                    result.getString("password"),result.getInt("dni"),result.getInt("idTipoUsuario"));
      result.close();
      return p;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();
      return null;
    }
  }
  
  static public boolean addPersona(Persona p) throws Exception
  {
    Statement stmt;
    boolean ok=false;
    String strSQL;
    
    try
    {
      if(buscarPorNombre(p.getUserName())==null)
      {
        stmt=PoolConexiones.getConexion().createStatement();
        strSQL="INSERT INTO Persona (userName, nombre,password,dni,idTipoUsuario)"+
          " VALUES ('"+p.getUserName()+"','"+p.getNombre()+"','"+
                      p.getPassword()+"',"+p.getId()+"','"+p.getIdTipoUsuario()+")";
        stmt.executeUpdate(strSQL);
        return true;
      }
      else return false;
    }
    
    catch(SQLException e)
    {
      e.printStackTrace();;
      return false;
    }
  }
    
  static public boolean store(Persona p) throws Exception
  {    
    Statement stmt;
    boolean ok=false;
    String strSQL;
    
    try
    {
      stmt=PoolConexiones.getConexion().createStatement();
      strSQL="UPDATE Persona "+
        " SET userName = '"+p.getUserName()+
          "', nombre   = '"+p.getNombre()+
          "', password = '"+p.getPassword()+
          "', idTipoUsuario = '"+p.getIdTipoUsuario()+
         "' WHERE dni="+p.getId();
      return (stmt.executeUpdate(strSQL)>0);
    }
    catch(SQLException e)
    {
      e.printStackTrace();;
      return false;
    }
  }
}