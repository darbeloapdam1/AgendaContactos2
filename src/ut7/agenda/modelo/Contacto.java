package ut7.agenda.modelo;

public abstract class Contacto implements Comparable<Contacto> {
	private String nombre;
	private String apellidos;
	private String telefono;
	private String email;

	public Contacto(String nombre, String apellidos, String telefono,
			String email) {
		this.nombre = nombre.toUpperCase();
		this.apellidos = apellidos.toUpperCase();
		this.telefono = telefono;
		this.email = email.toLowerCase();
	}
	
	// getters y setters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public char getPrimeraLetra() {
		return apellidos.toUpperCase().charAt(0);
	}
	
	public abstract String getFirmaEmail();
	
	/*
	 * @return representacion del objeto
	 */
	
	@Override
	public String toString() {
		String str = (apellidos + "," + nombre + " (" + this.getClass().getSimpleName() + ")\nTfno: " + telefono + " | " + "email: " + email);
		return str;
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Contacto c = (Contacto) obj;
		return c.getNombre().equals(this.nombre) && c.getApellidos().equals(this.apellidos) && 
				c.getTelefono().equals(this.telefono) && c.getEmail().equals(this.email);

	}
	
	@Override
	public int compareTo(Contacto c1) {
		if (this.getApellidos().compareToIgnoreCase(c1.getApellidos()) == 0) {
			if (this.getNombre().compareToIgnoreCase(c1.getNombre()) == 0) {
				return 0;
			} else if (this.getNombre().compareToIgnoreCase(c1.getNombre()) > 0) {
				return 1;
			} else {
				return -1;
			}
		}
		if(this.getApellidos().compareToIgnoreCase(c1.getApellidos()) > 0) {
			return 1;
		}
		return -1;
	}
}
