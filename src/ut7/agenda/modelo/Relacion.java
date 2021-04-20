package ut7.agenda.modelo;

public enum Relacion {
	PADRE("padre"), MADRE("madre"), AMIGOS("amigos"), PAREJA("pareja"), HIJO("hijo"), HIJA("hija");
	
	private String relacion;

	Relacion(String string) {
		this.relacion = string;
	}
	
	// accesor para la relacion en String
	public String getRelacion() {
		return relacion;
	}
	
}
