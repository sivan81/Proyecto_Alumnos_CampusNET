import com.campus.Util;
import com.campus.model.Alumno;
import com.campus.model.Asignatura;
import com.campus.model.CampusNet;
import com.campus.model.Credencial;
import com.campus.parser.CampusNetParser;
import com.campus.parser.CredencialesParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args)
			throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {

		Scanner s = new Scanner(System.in);

		System.out.println("Introduza usuario:");
		String usuario = s.next();

		System.out.println("Introduza password:");
		String password = s.next();

		if (credencialesValidas(usuario, password)) {
			// Si todo esta correcto la aplicaci�n se abre y muestra el menu al usuario
			// Abrimos ( o creamos el fichero) y los transformamos en un objeto CampusNet
			CampusNet campusNet = CampusNetParser.parse();
			ejecucion(campusNet);
		}
	}

	private static void ejecucion(CampusNet campusNet) throws IOException {
		int opcion = mostrarMenu();
		if (opcion == 1) { // Si selecciona la opci�n 1 se introduce asignatura
			introducirAsignatura(campusNet);
		} else if (opcion == 2) { // Si selecciona la opci�n 2 se introduce alumno
			introducirAlumno(campusNet);
		} else if (opcion == 3) { // Si selecciona la opci�n 3 se busca un alumno
			buscarAlumno(campusNet);
		} else if (opcion == 4) { // Si selecciona la opci�n 4 se sale de la aplicaci�n
			// Salimos de la funci�n
			return;
		} else {
			System.out.println("No es una opcion valida");
		}

		// Ejecutamos la misma funci�n de nuevo hasta que el usuario seleccione 4
		// (Salir)
		ejecucion(campusNet);
	}

	private static int mostrarMenu() {
		System.out.println("Seleccione una opci�n:");
		System.out.println("1) Introducir asignatura");
		System.out.println("2) Introducir alumno");
		System.out.println("3) Buscar alumno");
		System.out.println("4) Salir");

		Scanner s = new Scanner(System.in);
		try {// Si el usuario introduce una opci�n inv�lida saltar� un error
			return s.nextInt();
		} catch (Exception ex) {// Capturamos el error, mostramos un mensaje y volvemos a empezar esta funci�n.
			System.out.println("Ha introducido una opci�n invalida");
			return mostrarMenu();
		}
	}

	private static void introducirAsignatura(CampusNet campusNet) throws IOException {
		System.out.println("Indique el id de la asignatura:");
		Scanner s = new Scanner(System.in);
		String idAsignatura = s.next();

		if (asignaturaYaCreada(idAsignatura, campusNet.getAsignaturas())) {
			System.out.println("El id de asignatura " + idAsignatura + " ya existe");
		} else {
			// Creamos la asignatura con el id introducido
			Asignatura asignatura = new Asignatura();
			asignatura.setId(idAsignatura);

			// La a�adimos al campusNet
			campusNet.getAsignaturas().add(asignatura);
		}

		guardarCampus(campusNet);
	}

	private static boolean asignaturaYaCreada(String idAsignatura, List<Asignatura> asignaturas) {
		// Miramos si la asignatura con el idAsignatura ya esta en al lista
		return obtenerAsignaturaPorId(idAsignatura, asignaturas) != null;
	}

	private static Asignatura obtenerAsignaturaPorId(String idAsignatura, List<Asignatura> asignaturas) {
		for (Asignatura asignatura : asignaturas) {
			if (idAsignatura.equals(asignatura.getId())) {
				return asignatura;
			}
		}
		return null;
	}

	private static void introducirAlumno(CampusNet campusNet) throws IOException {
		Alumno alumno = crearAlumno();
		subMenuAlumno(alumno, campusNet);
	}

	// Pedimos los datos del alumno
	private static Alumno crearAlumno() {
		Scanner s = new Scanner(System.in);
		System.out.println("Nombre: ");
		String nombre = s.next();
		System.out.println("Apellidos: ");
		String apellidos = s.next();

		Alumno alumno = new Alumno();
		alumno.setNombre(nombre);
		alumno.setApellidos(apellidos);

		return alumno;
	}

	// Como he pensado que el alumno puede estar en una, ninguna o varias
	// asignaturas, hago un men� como el siguiente
	private static void subMenuAlumno(Alumno alumno, CampusNet campusNet) throws IOException {
		Scanner s = new Scanner(System.in);
		System.out.println("Seleccione una opci�n: ");
		System.out.println("1) Introducir el alumno a una asignatura");
		System.out.println("2) Introducir el alumno al campus");
		System.out.println("3) Salir al men� principal (el alumno no ser� guardado)");

		try {// Si el usuario introduce algo que no es un entero saltar� un error
			int opcion = s.nextInt();
			if (opcion == 1) {
				introducirAlumnoEnAsignatura(alumno, campusNet);
			} else if (opcion == 2) {
				introducirAlumnoEnCampus(alumno, campusNet);
			} else if (opcion == 3) {
				return;
			} else {
				System.out.println("No es una opci�n v�lida");
			}

		} catch (Exception ex) {// Capturamos el error, mostramos un mensaje y volvemos a empezar esta funci�n.
			System.out.println("Ha introducido una opci�n inv�lida");
		}

		subMenuAlumno(alumno, campusNet);
	}

	private static void introducirAlumnoEnAsignatura(Alumno alumno, CampusNet campusNet) throws IOException {
		// Pedimos el id de la asignatura y la nota
		Scanner s = new Scanner(System.in);
		System.out.println("Id de la asignatura:");
		String idAsignatura = s.next();
		System.out.println("Nota:");
		int nota = s.nextInt();

		// Buscamos la asignatura en el campus
		Asignatura asignatura = obtenerAsignaturaPorId(idAsignatura, campusNet.getAsignaturas());
		if (asignatura == null) {
			// Si la asignatura no est� dada de alta mostrar mensaje que la asignatura
			// indicada no existe
			System.out.println("La asignatura " + idAsignatura + " no se encuentra en el campus");
		} else {
			// Si la asignatura s� est� dada de alta buscar el alumno
			if (alumnoYaEnAsignatura(alumno, asignatura)) {
				// si el alumno ya est� en la asignatura mostrar mensaje que ese alumno ya ha
				// sido introducido para esa asignatura
				System.out.println("El alumno " + alumno.getNombre() + " " + alumno.getApellidos()
						+ " ya se encuentra dado de alta en la asignatura");
			} else {
				// si el alumno no est� en la asignatura
				// le ponemos la nota
				alumno.setNota(nota);
				// lo a�adimos a la asignatura
				asignatura.getAlumnos().add(alumno);
				guardarCampus(campusNet);
			}
		}
	}

	private static boolean alumnoYaEnAsignatura(Alumno alumno, Asignatura asignatura) {
		return obtenerAlumnoPorAsignatura(alumno, asignatura) != null;
	}

	private static Alumno obtenerAlumnoPorAsignatura(Alumno alumno, Asignatura asignatura) {
		for (Alumno alumnoAsignatura : asignatura.getAlumnos()) {
			if (alumnoAsignatura.getNombre().equals(alumno.getNombre())
					&& alumnoAsignatura.getApellidos().equals(alumno.getApellidos())) {
				return alumnoAsignatura;
			}
		}
		return null;
	}

	private static void introducirAlumnoEnCampus(Alumno alumno, CampusNet campusNet) throws IOException {
		
		// Caso cuando el alumno no est� en ninguna asignatura
		if (alumnoYaEnCampus(alumno, campusNet)) {
			// si el alumno ya est� en el campus mostrar mensaje que ese alumno ya ha sido
			// introducido para esa asignatura
			System.out.println("El alumno " + alumno.getNombre() + " " + alumno.getApellidos()
					+ " ya se encuentra dado de alta en el campus");
		} else {
			// si el alumno no est� en el campus a�adirlo al campus
			// Nos creamos un alumno igual al que tenemos pero sin nota, porque al ser un
			// nodo del campus y no de una asignatura no tiene sentido que tenga noda
			Alumno alumnoCampus = new Alumno();
			alumnoCampus.setNombre(alumno.getNombre());
			alumnoCampus.setApellidos(alumno.getApellidos());
			campusNet.getAlumnos().add(alumnoCampus);
			guardarCampus(campusNet);
		}
	}

	private static boolean alumnoYaEnCampus(Alumno alumno, CampusNet campusNet) {
		for (Alumno alumnoCampus : campusNet.getAlumnos()) {
			if (alumnoCampus.getNombre().equals(alumno.getNombre())
					&& alumnoCampus.getApellidos().equals(alumno.getApellidos())) {
				return true;
			}
		}
		return false;
	}

	private static void buscarAlumno(CampusNet campusNet) {
		// Preguntar el nombre y apellidos del alumno
		Alumno alumno = crearAlumno();
		boolean alumnoEncontrado = false;

		System.out.println("Informaci�n del alumno " + alumno.getNombre() + " " + alumno.getApellidos() + ":");
		// Buscarlo en las asignaturas
		for (Asignatura asignatura : campusNet.getAsignaturas()) {
			Alumno alumnoAsignatura = obtenerAlumnoPorAsignatura(alumno, asignatura);
			if (alumnoAsignatura != null) {
				// Por cada vez que lo encontremos mostramos el id de la asignatura y la nota.
				System.out.println("Asignatura: " + asignatura.getId() + " Nota: " + alumnoAsignatura.getNota());
				// Nos guardamos una marca para saber que s� que est� en las asignaturas y no
				// hacer el siguiente punto
				alumnoEncontrado = true;
			}
		}
		if (!alumnoEncontrado) {
			// Si no est� en las asignaturas buscarlo en el campus si esta mostramos un
			// mensaje que el alumno est� en el campus pero no est� dado de alta en ninguna
			// asignatura
			if (alumnoYaEnCampus(alumno, campusNet)) {
				System.out
						.println("El alumno se encuentra en el campus pero no esta dado de alta en ninguna asignatura");
			} else {
				// Si no est� en el campus mostrar un mensaje que el alumno no esta en el campus
				System.out.println("El alumno no se encuentra en el campus");
			}
		}
	}

	private static boolean credencialesValidas(String usuario, String password)
			throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		// Obtenemos las credenciales que tenemos preconfiguradas en el fichero
		List<Credencial> credenciales = CredencialesParser.parse();

		// Las recorremos y cuando encontremos una en al que el usuario y el password
		// sea el que el usuario ha introducido devolvemos que son v�lidas
		for (Credencial credencial : credenciales) {
			if (credencial.getUsuario().equals(usuario) && credencial.getPassword().equals(password)) {
				System.out.println("Credenciales v�lidas");
				return true;
			}
		}
		System.out.println("Error: Credenciales  inv�lidas");
		return false;
	}

	private static void guardarCampus(CampusNet campusNet) throws IOException {
		// Guardamos el fichero. Para ello convertimos el objeto campusNet en un String
		// llamando a su funci�n .toString y el resultado se puede guardar directamente
		// en el fichero
		Util.writeInFile(CampusNetParser.RUTA_FICHERO, campusNet.toString());
	}

}