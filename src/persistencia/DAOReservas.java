package persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dominio.Persona;
import dominio.RecursoExtendido;
import dominio.Reserva;

public class DAOReservas {

	public static ArrayList<Reserva> getReservasRecurso(RecursoExtendido recurso){
		Statement stmt;
	    ResultSet result;
	    String strSQL;
	    ArrayList<Reserva> resultado = null;
	    Persona persona;
	    Reserva reserva;
	    try
	    {
	      stmt=PoolConexiones.getConexion().createStatement();
	      strSQL="SELECT P.DNI,P.NOMBRE,P.PASSWORD,P.USERNAME, P.EMAIL,P.IDTIPOUSUARIO," +
	                     "R.IDRESERVA,R.FECHAINICIO,R.FECHAFIN,URGENCIA"+
	             " FROM Reserva AS R, PERSONA AS P"+
	             " WHERE R.dniPeticionario = P.dni AND"+
	             " idRecurso="+recurso.getId();
	      result = stmt.executeQuery(strSQL);
	      resultado = new ArrayList<>();
	      
	      while(result.next()){
	    	 persona = new Persona(result.getString("username"),result.getString("nombre"),
	    			              result.getString("password"),result.getString("email"),
	    			              result.getInt("dni"),result.getInt("IdTipoUsuario"));
	    	 reserva = new Reserva(result.getInt("idReserva"),persona,recurso,result.getTimestamp("fechaInicio").toLocalDateTime(),
	    			 		result.getTimestamp("fechafin").toLocalDateTime(),
	    			 		result.getInt("urgencia"));
	    	 resultado.add(reserva);
	      }
	     
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    }
	      return resultado;
	}
	
	

	public static boolean estaReservado(int id, LocalDateTime ahora) {
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
	             " FROM Reserva"+
	             " WHERE idRecurso="+id+" and '"+
	             strDateTime +"' BETWEEN fechaInicio AND fechaFin";
	      result = stmt.executeQuery(strSQL);
	      if(!result.next()) throw new Exception("sentencia errónea: " + strSQL);
	      resultado= (result.getInt(1) != 0);
	     
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    }
	      return resultado;
	}

	public static void eliminarReservasRecurso(int id) throws SQLException {
		Statement stmt;
		int result;
		String strSQL;
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="DELETE FROM  RESERVA "+
	             " WHERE idRecurso = "+id;
	    result = stmt.executeUpdate(strSQL);
		
	}
	public static void insertarReserva(Reserva reserva){
		 Statement stmt;
		  int result;
		  String strSQL;
		  DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	      String strFechaDesde = reserva.getDesde().format(formatter);
	      String strFechaHasta = reserva.getHasta().format(formatter);
		  try
		    {
		      stmt=PoolConexiones.getConexion().createStatement();
		      strSQL="INSERT INTO RESERVA "+
		             " VALUES (0,'"+strFechaDesde+"','"+strFechaHasta+"',"+reserva.getUrgencia()+
		             ","+reserva.getPersona().getId()+","+reserva.getRecurso().getId()+")";
		      result = stmt.executeUpdate(strSQL);
		    }catch(SQLException e) {
		      e.printStackTrace();
		      return;
		    }
	}
	public static void cleanReservas() throws SQLException {
		Statement stmt;
		int result;
		String strSQL;
		String strDateTime;
		LocalDateTime ahora = LocalDateTime.now();
		
		DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	    strDateTime = ahora.format(formatter);
	    
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="DELETE FROM  RESERVA "+
	             " WHERE FECHAFIN < '" +strDateTime+"'";
	    result = stmt.executeUpdate(strSQL);
	}

	public static void modificarReserva(int id, LocalDateTime desde,LocalDateTime hasta,int urgencia ) throws SQLException {
		
		Statement stmt;
		int result;
		String strSQL;
		stmt=PoolConexiones.getConexion().createStatement();
		DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	    String strDateTimeDesde = desde.format(formatter);
	    String strDateTimeHasta = hasta.format(formatter);
	    
		strSQL="UPDATE  RESERVA "+
		       "SET FECHAINICIO = '"+strDateTimeDesde+"',"+
				"FECHAFIN='"+strDateTimeHasta+"',"+
		        "URGENCIA="+urgencia+
	            " WHERE idReserva = " +id ;
	             
	    result = stmt.executeUpdate(strSQL);
	}
	

	public static ArrayList<Reserva> getReservasRecursoEntreFechas(RecursoExtendido recurso, LocalDateTime desde,
			LocalDateTime hasta) {
		Statement stmt;
	    ResultSet result;
	    String strSQL;
	    ArrayList<Reserva> resultado = null;
	    Persona persona;
	    Reserva reserva;
	    try
	    {
	      stmt=PoolConexiones.getConexion().createStatement();
	      DateTimeFormatter formatter =   DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm");
	      String strFechaDesde = desde.format(formatter);
	      String strFechaHasta = hasta.format(formatter);
	      strSQL="SELECT P.DNI,P.NOMBRE,P.PASSWORD,P.USERNAME,P.IDTIPOUSUARIO, P.EMAIL," +
	                     "R.IDRESERVA,R.FECHAINICIO,R.FECHAFIN,URGENCIA"+
	             " FROM Reserva AS R, PERSONA AS P"+
	             " WHERE R.dniPeticionario = P.dni AND"+
	             " idRecurso="+recurso.getId()+" AND "+
	             " ('" +strFechaDesde+"' BETWEEN fechaInicio AND fechaFin OR "+
	             " '" +strFechaHasta+"' BETWEEN fechaInicio AND fechaFin)";
   
	      
	      result = stmt.executeQuery(strSQL);
	      resultado = new ArrayList<>();
	      
	      while(result.next()){
	    	 persona = new Persona(result.getString("username"),result.getString("nombre"),
	    			              result.getString("password"),result.getString("email"),
	    			              result.getInt("dni"), result.getInt("IdTipoUsuario"));
	    	 reserva = new Reserva(result.getInt("idReserva"),persona,recurso,result.getTimestamp("fechaInicio").toLocalDateTime(),
	    			 		result.getTimestamp("fechafin").toLocalDateTime(),
	    			 		result.getInt("urgencia"));
	    	 resultado.add(reserva);
	      }
	     
	    } catch (Exception e){
	    	System.out.println(e.getMessage());
	    	e.printStackTrace();
	    }
	      return resultado;
	}



	public static void eliminarReserva(int id) throws SQLException {
		Statement stmt;
		int result;
		String strSQL;
		
	    
		stmt=PoolConexiones.getConexion().createStatement();
		strSQL="DELETE FROM  RESERVA "+
	             " WHERE IDRESERVA = " +id;
	    result = stmt.executeUpdate(strSQL);
	}
}
