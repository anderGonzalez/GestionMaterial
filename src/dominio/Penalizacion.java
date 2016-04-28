package dominio;

import java.util.Calendar;

public class Penalizacion {
	
	protected Integer dni;
	protected Calendar fInicio;
	protected Calendar fFinal;
	protected Integer idPrestamo;
	
	
	 public Penalizacion(Integer dni, Calendar fInicio, Calendar fFinal, Integer idPrestamo){
	 this.dni = dni;
	 this.fInicio = fInicio;
	 this.fFinal = fFinal;
	 this.idPrestamo = idPrestamo; 
	 }
	
	public Integer getDni() {
		return dni;
	}
	public void setDni(Integer dni) {
		this.dni = dni;
	}
	public Calendar getfInicio() {
		return fInicio;
	}
	public void setfInicio(Calendar fInicio) {
		this.fInicio = fInicio;
	}
	public Calendar getfFinal() {
		return fFinal;
	}
	public Integer getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(Integer idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public void setfFinal(Calendar fFinal) {
		this.fFinal = fFinal;
	}	

}
