package mainGestionBiblioteca;
import java.time.LocalDate;
import java.util.HashMap;


import utilidades.Utilidades;
import clasesBiblioteca.Libro;
import clasesBiblioteca.Usuario;
import clasesBiblioteca.Genero;
import clasesBiblioteca.LDigital;
import clasesBiblioteca.LFisico;
import clasesBiblioteca.Formato;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import utilidades.MyObjectOutputStream;


public class GestionarBiblioteca {
	public static void main(String[] args) {
		File fichU = new File("usuarios.dat");
		File fichL = new File("libros.dat");
		FillData(fichL);
		int opcion;
		do {
			opcion = mostrarMenu();
			System.out.println("---MENU PRINCIPAL DE LA GESTION DE LA BIBLIOTECA---");
			switch (opcion) {
			case 1:
				crearUsuario(fichU);
				break;
			case 2:

				break;
			case 3:

				break;
			case 4:

				break;

			case 5:
				break;

			case 6:
				break;

			case 7:
				break;
			case 8:

				break;
			case 9:
				System.out.println("Saliendo del programa...");
				break;
			}
		} while (opcion != 8);
	}

	public static int mostrarMenu() {
		System.out.println("----- Gestion de Biblioteca -----");
		System.out.println("1- Crear Usuario");
		System.out.println("2- Añadir Libro a usuario");
		System.out.println("3- Borrar usuario");
		System.out.println("4- Eliminar libro de usuario");
		System.out.println("5- Listar usuarios con sus respectivos libros");
		System.out.println("6- Listar catalogo de libros");
		System.out.println("7- Buscar usuario por ID");
		System.out.println("8- Modificar datos de libros");

		return Utilidades.leerInt(1, 9);
	}

	private static void crearUsuario(File fichU) {
		LocalDate fechaNacimiento;
		String nombre,contraseña;
		Usuario nuevoUsuario;

		System.out.println("Introduce el nombre del usuario:");
		nombre = Utilidades.introducirCadena(); //El nombre no puede tener menos de 2 caracteres   

		try {
			while (nombre.length() < 2) {
				System.out.println("El nombre debe tener al menos 2 caracteres. Introduce el nombre del usuario:");
				nombre = Utilidades.introducirCadena();
			}
		} catch (Exception e) {
			System.out.println("Error al introducir el nombre del usuario.");
		}

		System.out.println("Introduce la contraseña del usuario:");
		contraseña = Utilidades.introducirCadena();
		try {
			while (contraseña.length() < 8) {
				System.out.println("La contraseña debe tener al menos 8 caracteres o numeros. Introduce la contraseña del usuario:");
				contraseña = Utilidades.introducirCadena();
			}
		} catch (Exception e) {
			System.out.println("Error al introducir la contraseña del usuario.");
		}
		System.out.println("Introduzca su fecha de nacumiento (aaaa/mm/dd):");
		fechaNacimiento = Utilidades.leerFechaAMD();
		nuevoUsuario = new Usuario(nombre, contraseña, fechaNacimiento);
		MyObjectOutputStream moos;
		ObjectOutputStream oos;
		try {
			if (FicheroUsarioExiste(fichU)) {
				oos = new ObjectOutputStream(new FileOutputStream(fichU, true));
				oos.writeObject(nuevoUsuario);
				oos.close();
			} else {
				moos = new MyObjectOutputStream(new FileOutputStream(fichU, true));
				moos.writeObject(nuevoUsuario);
				moos.close();
			}
			System.out.println("Se ha creado el usuario '" + nuevoUsuario.getNombre() + "' correctamente. Este sera el ID del Usuario: " + nuevoUsuario.getIdUsuario());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void FillData(File fichL) {
		if (!fichL.exists()) {
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(new FileOutputStream(fichL, true));
				Libro libro1 = new LFisico("El Quijote","1234567890123", "Miguel de Cervantes", Genero.AVENTURAS, true, 5);
				Libro libro2 = new LFisico("Dracula","3216549876543", "Bram Stoker", Genero.TERROR, false, 3);
				Libro libro3 = new LDigital("1984", "9876543210123", "George Orwell", Genero.FICCION, Formato.PDF, 2.5);
				Libro libro4 = new LDigital("El Principito", "4567891230123", "Antoine de Saint-Exupéry", Genero.AVENTURAS, Formato.EPUB, 1.2);

				oos.writeObject(libro1);
				oos.writeObject(libro2);
				oos.writeObject(libro3);
				oos.writeObject(libro4);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean FicheroUsarioExiste(File fichU) {
		boolean finArchivo = false, existe = false;
		ObjectInputStream ois = null;

		if (!fichU.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichU));
				while (!finArchivo) {
					try {
						Usuario p = (Usuario) ois.readObject();
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Usuario no es válida");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero");
			}
		}
		return existe;
	}


	private static void añadirLibroAUsuario() {

	}

	private static void borrarUsuario(File fichU) {
		if (FicheroUsarioExiste(fichU)) {
			boolean finArchivo = false;
			File tempFile = new File("temp.dat");
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichU));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile));
				while (!finArchivo) {
					try {
						Usuario usuario = (Usuario) ois.readObject();
						System.out.println("Introduce el ID del usuario al dar de baja");
						String dniTrabajador = Utilidades.introducirCadena();
						oos.writeObject(usuario);
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
				oos.close();
				if (!fichU.delete()) {
					System.out.println("No se pudo borrar el archivo original");
					return;
				}
				if (!tempFile.renameTo(fichU)) {
					System.out.println("No se pudo renombrar el archivo temporal");
				}
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Usuario no es válida");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero");
			}
		} else {
			System.out.println("El fichero no existe");
		}
	}

	private static void eliminarLibroDeUsuario() {

	}

	private static void listarUsuariosConSusLibros(File fichU) {
		boolean finArchivo = false;
		ObjectInputStream ois = null;
		if (fichU.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichU));
				while (!finArchivo) {
					try {
						Usuario u = (Usuario) ois.readObject();
						System.out.println(u);
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Usuario no es válida");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero");
			}
		} else {
			System.out.println("El fichero no existe");
		}
	}

	private static void listarLibros(File fichL) {
		boolean finArchivo = false;
		ObjectInputStream ois = null;

		if (fichL.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(fichL));
				while (!finArchivo) {
					try {
						Libro l = (Libro) ois.readObject();
						System.out.println(l);
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("No se encontró el fichero");
			} catch (ClassNotFoundException e) {
				System.out.println("La clase Libro no es válida");
			} catch (IOException e) {
				System.out.println("Error leyendo el fichero");
			}
		} else {
			System.out.println("El fichero no existe");
		}
	}

}
