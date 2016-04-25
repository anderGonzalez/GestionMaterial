package dominio;

import java.sql.Date;

public class Penalizacion {
	
	protected String dni;
	protected Date fInicio;
	protected Date fFinal;
	protected Integer idPrestamo;
	
	
	 public Penalizacion(String dni, Date fInicio, Date fFinal, Integer idPrestamo){
	 this.dni = dni;
	 this.fInicio = fInicio;
	 this.fFinal = fFinal;
	 this.idPrestamo = idPrestamo; 
	 }
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public Date getfInicio() {
		return fInicio;
	}
	public void setfInicio(Date fInicio) {
		this.fInicio = fInicio;
	}
	public Date getfFinal() {
		return fFinal;
	}
	public Integer getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(Integer idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public void setfFinal(Date fFinal) {
		this.fFinal = fFinal;
	}	

}
