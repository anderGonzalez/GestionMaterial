package persistencia;

import java.sql.*;
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

			lista = new ArrayList<>();
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin, idTipoUsuario FROM penalizaciones";
			result = stmt.executeQuery(strSQL);
			while (result.next()) {
				penalizacion = new Penalizacion(result.getInt("dniPenalizado"), result.getDate("fechaInicio"),
						result.getDate("fechaFin"), result.getInt("idPrestamo"));
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
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin, idTipoUsuario"
					+ " FROM Penalizaciones WHERE idPrestamo=" + idPenalizacion;
			result = stmt.executeQuery(strSQL);
			if (!result.next())
				return null;
			p = new Penalizacion(result.getInt("dniPenalizado"), result.getDate("fechaInicio"),
					result.getDate("fechaFin"), result.getInt("idPrestamo"));
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
		ArrayList<Penalizacion> lista = null;

		try {
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "SELECT dniPenalizado, idPrestamo, fechaInicio, fechaFin, idTipoUsuario"
					+ " FROM Penalizaciones WHERE dniPenalizado=" + dni;
			result = stmt.executeQuery(strSQL);
			while (result.next()) {
				penalizacion = new Penalizacion(result.getInt("dniPenalizado"), result.getDate("fechaInicio"),
						result.getDate("fechaFin"), result.getInt("idPrestamo"));
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
			stmt = PoolConexiones.getConexion().createStatement();
			strSQL = "INSERT INTO Penalizaciones (dniPenalizado, idPrestamo,fechaInicio,fechaFin)" + " VALUES ('"
					+ p.getDni() + "','" + p.getIdPrestamo() + "','" + p.getfInicio() + "'," + p.getfFinal()+ ")";
			stmt.executeUpdate(strSQL);
			return true;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}