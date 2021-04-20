package ut7.agenda.modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Personal extends Contacto {

	private LocalDate fecha_nacimiento;
	private Relacion relacion;
	public Personal(String nombre, String apellidos, String telefono, String email, String fecha, Relacion relacion) {
		super(nombre, apellidos, telefono, email);
		String[] fechaNa = fecha.split("/");
		String nuevaFecha = fechaNa[2].trim() + "-" + fechaNa[1].trim() + "-" + fechaNa[0].trim();
		fecha_nacimiento = LocalDate.parse(nuevaFecha);	
		this.setRelacion(relacion);
	}

	// accesores y mutadores

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public Relacion getRelacion() {
		return relacion;
	}

	public void setRelacion(Relacion relacion) {
		this.relacion = relacion;
	}

	@Override
	public String getFirmaEmail() {
		return "Un abrazo!!";
	}
	
	@Override
	public String toString() {
		String str = super.toString();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
		str += "\nFecha nacimiento: " + fecha_nacimiento.format(formatter)
		+ "\nRelacion: " + relacion.toString() + "\n";
		return str;
	}
	
	/**
	 * metodo para ver si es su cumpleaños
	 * @return true o false si es su cumpleaños
	 */
	
	public boolean esCumpleanos() {
		LocalDate fecha = LocalDate.now();
		if(fecha_nacimiento.getDayOfMonth() == fecha.getDayOfMonth()) {
			if(fecha_nacimiento.getMonthValue() == fecha.getMonthValue()) {
				return true;
			}
		}
		return false;
	}
	
	
}
