package dominio;

import java.time.LocalDateTime;

public class Reserva {
	int id;
	

	Persona persona;
	RecursoExtendido recurso;
	LocalDateTime desde;
	LocalDateTime hasta;
	int urgencia;
	
	public Reserva (int id,Persona p,RecursoExtendido rec, LocalDateTime desde, LocalDateTime hasta,
			         int urgencia){
		this.id = id;
		this.persona = p;
		this.recurso = rec;
		this.desde = desde;
		this.hasta = hasta;
		this.urgencia = urgencia;
	}
	public int getId() {
		return id;
	}
	public Persona getPersona() {
		return persona;
	}

	public RecursoExtendido getRecurso() {
		return recurso;
	}

	public LocalDateTime getDesde() {
		return desde;
	}

	public LocalDateTime getHasta() {
		return hasta;
	}

	public int getUrgencia() {
		return urgencia;
	}
	
}
