package agenda.modelo;

public class Profesional extends Contacto {
	
	public String empresa;

	public Profesional(String nombre, String apellidos, String telefono, String email, String empresa) {
		super(nombre, apellidos, telefono, email); // hereda de la clase super (Contacto)
		this.empresa = capitalizar(empresa);
	}
	
	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = capitalizar(empresa);
	}
	
	public String capitalizar(String str) {	
		boolean primera = true; 
        String resul = "";
        for(int i = 0; i < str.length(); i++) {
            if(primera) {
            	resul += str.toUpperCase().substring(i,i + 1).charAt(0); // devuelve la letra en mayuscula
                primera = false;
            }
            else{
            	resul += str.substring(i,i + 1).charAt(0); // añade el resto de la palabra a resul
            }
            if (str.substring(i,i+1).equals(" ")) // comprueba cuando acaba el primer apellido
                primera = true;
        }
        return resul;
	}

	@Override
	public String getFirmaEmail() {
		String[] firma = {"Atentamente", "Saludos", "Saludos cordiales", "Mis mejores deseos"}; // array donde estan las diferentes firmas
		return firma[(int) (Math.random() * 3)]; // elegir la firma con un numero aleatorio
	}
	
	@Override
	public String toString() {
		String str = ("\n" + super.toString() + "\nEmpresa: " + empresa + "\n");
		return str;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if(super.equals(obj)) {
			Profesional pro = (Profesional) obj;
			if(this.getEmpresa().equals(pro.getEmpresa())) {
				return true;
			}
		}
		return false;
	}

}
