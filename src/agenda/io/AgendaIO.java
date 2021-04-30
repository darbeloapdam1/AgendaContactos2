package agenda.io;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
 */
public class AgendaIO {

	public static void importar(AgendaContactos agenda, String nombre) {
		Scanner entrada = null;		
		try {
			File f = new File(nombre);
			entrada = new Scanner(f);
			while(entrada.hasNextLine()) {
				Contacto con = parsearLinea(entrada.next()); // crear el contacto 
				agenda.añadirContacto(con); // añadir el contacto a la agenda
		}
		}catch(NullPointerException e) {
			System.out.println("Error parametro con valor null " + e.getMessage());
		}catch(FileNotFoundException e) {
			System.out.println("Error archivo no encontrado " + e.getMessage());
		}catch(IllegalStateException e) {
			System.out.println("Error si el escáner está cerrado " + e.getMessage());
		}catch(NoSuchElementException e) {
			System.out.println("Error no es una excepción de elemento " + e.getMessage());
		}
	}

	private static Contacto parsearLinea(String linea) {
		try {
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
			String relacion = tokens[6];
			return new Personal(nombre, apellidos, telefono, email, fecha_nacimiento, queRelacion(relacion));
		}
		}catch(PatternSyntaxException e) { // excepción de split
			System.out.println("Error si la expresión es inválida " + e.getMessage());
		} catch (IndexOutOfBoundsException e) { // excepción de charAt
			System.out.println("Error si el índice es negativo o no menor que la longitud " + e.getMessage());
		}
		return null;
	}
	
	/*
	 * @return rel la relacion del contacto
	 */
	
	private static Relacion queRelacion(String relacion2) {
		Relacion[] relaciones = Relacion.values();
		for(Relacion rel: relaciones) {
			if(rel.getRelacion().equalsIgnoreCase(relacion2.trim())) {
				return rel;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return un array de String con todas las líneas de información de todos
	 *         los contactos. 1 significa contacto profesional, 2 significa
	 *         contacto personal
	 */
	
	public static void exportarPersonales(AgendaContactos agenda, String nombre_fichero) {
		Map<Relacion,List<String>> personales = agenda.personalesPorRelacion();
		PrintWriter salida = null;
		try {
			File f = new File(nombre_fichero);
			salida = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			Set<Relacion> claves = personales.keySet();
			for(Relacion clave : claves) {
				List<String> entradas = personales.get(clave);
				salida.println(clave.getRelacion());
				for(String entrada : entradas) {
					salida.println("  " + entrada);
				}
			}
		}catch(NullPointerException e) {
			System.out.println("Error parametro con valor null " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error en el fichero " + e.getMessage());
		}catch(ClassCastException e) {
			System.out.println("Error clave inapropiada " + e.getMessage());
		}finally {
			salida.close();
		}
	}

}
