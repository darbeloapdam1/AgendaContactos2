package agenda.modelo;

public class RelacionException extends Exception{
	
	private String valorErroneo;
	
	public RelacionException(String valorErroneo) {
		this.valorErroneo = valorErroneo;
	}
	
	public String toString() {
		return "Error en la relacion del contacto " + valorErroneo;
	}
}
