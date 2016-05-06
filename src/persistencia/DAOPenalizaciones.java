package persistencia;

import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import dominio.Penalizacion;

public class DAOPenalizaciones {
	static public ArrayList<Penalizacion> obtenerPenalizaciones() throws Exception {
		Statement stmt;
		ResultSet result;
		String strSQL;
		ArrayList<Penalizacion> lista = null;
		Penalizacion penalizacion;
		try {

			lista = new ArrayList<Penalizacion>();
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin FROM penalizaciones";
			result = stmt.executeQuery(strSQL);
			while (result.next()) {
				
				Calendar fechaInicio = Calendar.getInstance();
				Calendar fechaFin = Calendar.getInstance();

				fechaInicio.setTimeInMillis(result.getDate("fechaInicio").getTime());
				fechaFin.setTimeInMillis(result.getDate("fechaFin").getTime());
				
				penalizacion = new Penalizacion(result.getInt("dniPenalizado"), fechaInicio,
						fechaFin, result.getInt("idPrestamo"));
				lista.add(penalizacion);
			}
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	static public Penalizacion buscarPorId(int idPenalizacion) throws Exception {
		Statement stmt;
		ResultSet result;
		String strSQL;
		Penalizacion p;

		try {
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin"
					+ " FROM Penalizaciones WHERE idPrestamo=" + idPenalizacion;
			result = stmt.executeQuery(strSQL);
			if (!result.next())
				return null;
			Calendar fechaInicio = Calendar.getInstance();
			Calendar fechaFin = Calendar.getInstance();

			fechaInicio.setTimeInMillis(result.getDate("fechaInicio").getTime());
			fechaFin.setTimeInMillis(result.getDate("fechaFin").getTime());
			
			
			p = new Penalizacion(result.getInt("dniPenalizado"), fechaInicio,
					fechaFin, result.getInt("idPrestamo"));
			result.close();
			return p;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("null")
	static public ArrayList<Penalizacion> buscarPorDni(String dni) throws Exception {
		Statement stmt;
		ResultSet result;
		String strSQL;
		Penalizacion penalizacion;
		ArrayList<Penalizacion> lista = new ArrayList<>();

		try {
			lista = new ArrayList<Penalizacion>();
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin"
					+ " FROM Penalizaciones WHERE dniPenalizado=" + dni;
			result = stmt.executeQuery(strSQL);
			while (result.next()) {
				
				Calendar fechaInicio = Calendar.getInstance();
				Calendar fechaFin = Calendar.getInstance();

				fechaInicio.setTimeInMillis(result.getDate("fechaInicio").getTime());
				fechaFin.setTimeInMillis(result.getDate("fechaFin").getTime());
				
				penalizacion = new Penalizacion(result.getInt("dniPenalizado"), fechaInicio,
						fechaFin, result.getInt("idPrestamo"));
				lista.add(penalizacion);
			}
			result.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;

	}

	static public boolean addPenalizacion(Penalizacion p) throws Exception {
		Statement stmt;
		String strSQL;

		try {
			String fInicio, fFin;
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			fInicio = f.format(p.getfInicio().getTime());
			fFin = f.format(p.getfFinal().getTime());
			
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "INSERT INTO Penalizaciones (dniPenalizado, idPrestamo,fechaInicio,fechaFin)" + " VALUES ("
					+ p.getDni() + "," + p.getIdPrestamo() + ",'" + fInicio + "','" + fFin + "')";
			stmt.executeUpdate(strSQL);
			return true;
		}

		catch (SQLException e) {
			return false;
		}
	}
	static public boolean deletePenalizacion(Penalizacion pdelete) throws Exception {
		Statement stmt;
		String strSQL;

		try {
			String fInicio;
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			fInicio = f.format(pdelete.getfInicio().getTime());
		
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "DELETE FROM Penalizaciones WHERE dniPenalizado=" + pdelete.getDni()+" and idPrestamo="
					 + pdelete.getIdPrestamo() + " and fechaInicio= '" + fInicio + "'";
			stmt.executeUpdate(strSQL);
			return true;
		}

		catch (SQLException e) {
			return false;
		}
	}
	static public void updatePenalizacion(Penalizacion antes, Penalizacion despues) {
		try {
			deletePenalizacion(antes);
			addPenalizacion(despues);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}


}