package persistencia;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
//import java.util.*;
//import java.io.*;

class PoolConexiones
{
  //protected static String nombreBBDD="jdbc:odbc:gestionMaterial_01";
	final static String SGBDURL = "jdbc:mysql://localhost:3306/material";
	private static Connection instance;
 
  /* versión basada en JODBC
   * 
   *
  public static Connection getConexion() throws Exception
  {
    if(instance==null)
    {
      try
      {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        instance = DriverManager.getConnection(nombreBBDD);
      }
      catch(ClassNotFoundException e)
      {
        throw new Exception("Driver");
      }
      catch(SQLException e)
      {
        throw new Exception("fuentes ODBC");
      }
    }
    return instance;
  }
  */
  public static Connection getConexion() throws SQLException {

	  if (instance == null){
	    Properties connectionProps = new Properties();
	    try {
			connectionProps.load(new FileInputStream("mysql_Login.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   instance = DriverManager.getConnection(SGBDURL, connectionProps);
	  }
	  return instance;
	}
} 