package agenda.io;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import agenda.modelo.AgendaContactos;
import agenda.modelo.Contacto;
import agenda.modelo.Personal;
import agenda.modelo.Profesional;
import agenda.modelo.Relacion;

/**
 * Utilidades para cargar la agenda
 * @author Diego Arbeloa - Carla Barberia
 */
public class AgendaIO {

	public static int importar(AgendaContactos agenda, String nombre) {
		int errores = 0;
		Scanner entrada = null;		
		try {
			entrada = new Scanner(AgendaIO.class.getClassLoader()
					 .getResourceAsStream(nombre));
			while(entrada.hasNextLine()) {
				try {
					Contacto con = parsearLinea(entrada.nextLine()); // crear el contacto 
					agenda.añadirContacto(con); // añadir el contacto a la agenda
				}catch(IndexOutOfBoundsException e) {
					errores++;
				}catch(DateTimeParseException e) {
					errores++;
				}catch(IllegalArgumentException e) {
					errores++;
				}catch(NullPointerException e) {
					errores++;
				}
		}
		}catch(NullPointerException e) {
			System.out.println("Error parametro con valor " + e.getMessage());
		}catch(IllegalStateException e) {
			System.out.println("Error si el escáner está cerrado " + e.getMessage());
		}catch(NoSuchElementException e) {
			System.out.println("Error no es una excepción de elemento " + e.getMessage());
		}finally {
			if(entrada != null) {
				entrada.close();
			}
		}
		
		return errores;
	}

	private static Contacto parsearLinea(String linea) throws IndexOutOfBoundsException, DateTimeParseException, PatternSyntaxException, IllegalArgumentException{
		String[] tokens = linea.split(","); // guardar cada dato de la linea
		String nombre = tokens[1].trim();
		String apellidos = tokens[2].trim();
		String telefono = tokens[3].trim();
		String email = tokens[4].trim();
		

		int num = tokens[0].trim().charAt(0);
		if (num == '1') { // ver de que tipo es el contacto (1 = profesional, 2 = personal)
			String empresa = tokens[5].trim();
			return new Profesional(nombre, apellidos, telefono, email, empresa);
		} else {
			String fecha_nacimiento = tokens[5];
			return new Personal(nombre, apellidos, telefono, email, fecha_nacimiento, Relacion.valueOf(tokens[6].trim().toUpperCase()));
		}
			
	}

	/**
	 * 
	 * @return un array de String con todas las líneas de información de todos
	 *         los contactos. 1 significa contacto profesional, 2 significa
	 *         contacto personal
	 */
	
	public static void exportarPersonales(AgendaContactos agenda, String nombre_fichero) throws NullPointerException, IOException {
		Map<Relacion,List<String>> personales = agenda.personalesPorRelacion();
		File f = new File(nombre_fichero);
		PrintWriter salida = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		Set<Relacion> claves = personales.keySet();
		for(Relacion clave : claves) {
			List<String> entradas = personales.get(clave);
			salida.println(clave.getRelacion().toUpperCase());
			salida.println("    " + entradas);
		}
		salida.close();
	}

}
