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
		String idUsuario = null;
		HashMap<Libro, LocalDate> LibrosUsuarios = new HashMap<Libro, LocalDate>();
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
				añadirLibroUsuario(fichU, fichL, idUsuario, LibrosUsuarios);
				break;
			case 3:
				borrarUsuario(fichU);
				break;
			case 4:
				break;
			case 5:
				listarUsuariosConSusLibros(fichU);
				break;
			case 6:
				listarLibros(fichL);
				break;
			case 7:
				buscarUsuarioXId(fichU);
				break;
			case 8:
				
				break;
			case 9:
				System.out.println("Saliendo del programa...");
				break;
			}
		} while (opcion != 9);
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
		System.out.println("9- Salir");
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
			if (!fichU.exists()) {
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
				Libro libroF1 = new LFisico("El Quijote","1234567890123", "Miguel de Cervantes", Genero.AVENTURAS, true, 5);
				Libro libroF2 = new LFisico("Dracula","3216549876543", "Bram Stoker", Genero.TERROR, false, 3);
				Libro libroF3 = new LFisico("Tin Tin","9263282332482", "Georges Remi", Genero.AVENTURAS, true, 8);
				Libro libroF4 = new LFisico("One Piece","2637272883232", "Eiichiro Oda", Genero.AVENTURAS, false, 7);
				Libro libroF5 = new LFisico("Orgullo y prejuicio","8462835007841", "Jane Austen", Genero.ROMANCE, true, 4);
				Libro libroD1 = new LDigital("1984", "9876543210123", "George Orwell", Genero.FICCION, Formato.PDF, 2.5);
				Libro libroD2 = new LDigital("El Principito", "4567891230123", "Antoine de Saint-Exupéry", Genero.AVENTURAS, Formato.TXT, 1.2);
				Libro libroD3 = new LDigital("Fahrenheit 451", "1192820576467", "Ray Bradbury", Genero.DISTOPICO, Formato.PDF, 2.5);
				Libro libroD4 = new LDigital("It", "0000000483628", "Stephen King", Genero.TERROR, Formato.EPUB, 1.2);
				Libro libroD5 = new LDigital("Dune", "4863766767676", "Frank Herbert", Genero.FICCION, Formato.TXT, 2.5);

				oos.writeObject(libroF1);
				oos.writeObject(libroF2);
				oos.writeObject(libroF3);
				oos.writeObject(libroF4);
				oos.writeObject(libroF5);
				oos.writeObject(libroD1);
				oos.writeObject(libroD2);
				oos.writeObject(libroD3);
				oos.writeObject(libroD4);
				oos.writeObject(libroD5);
				oos.close();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private static void buscarLibroUsuario(File fichU, File fichL, String idUsuario, HashMap<Libro, LocalDate> LibrosUsuarios) {
		String isbnLibro;		
		new HashMap<Libro, LocalDate>();
		boolean encontrado = false;
		boolean finArchivo = false;
		System.out.println("Introduce el ISBN del libro a añadir:");
		isbnLibro = Utilidades.introducirCadena();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichL))) {
			while (!finArchivo || !encontrado) {
				try {
					Libro libro = (Libro) ois.readObject();
					if (libro.getIsbn().equals(isbnLibro)) {
						System.out.println("Se ha encontrado el libro: " + libro.getTitulo());
						LibrosUsuarios.put(libro, LocalDate.now().plusWeeks(2));
						encontrado = true;
					}
				} catch (EOFException e) {
					finArchivo = true;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error procesando el fichero de libros");
		}
	}
	
	private static void buscarUsuarioXId(File fichU) {
		String idUsuario;		
		boolean encontrado = false;
		boolean finArchivo = false;
		System.out.println("Introduce el Id del usuario:");
		idUsuario = Utilidades.introducirCadena();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichU))) {
			while (!finArchivo || !encontrado) {
				try {
					Usuario usuarios = (Usuario) ois.readObject();
					if (usuarios.getIdUsuario() == idUsuario) {
						usuarios.toString();
						encontrado = true;
					}
				} catch (EOFException e) {
					if (!encontrado) {
						System.out.println("No se ha encontrado el usuario con ID: " + idUsuario);
					}
					finArchivo = true;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error procesando el fichero de libros");
		}
	}
	
	private static void añadirLibroUsuario(File fichU, File fichL, String idUsuario, HashMap<Libro, LocalDate> LibrosUsuarios) {
		//Comprobar que el fichero usuario existe
		//Comprobar que el el usuario existe
		//Comprobar que el libro existe
		boolean finArchivo = false;
		boolean encontrado = false;
		if (!fichU.exists()) {
			System.out.println("El fichero no existe");
		} else {
			File tempFile = new File("temp.dat");
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichU));
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) { 
				while (!finArchivo || !encontrado) {
					try {
						System.out.println("Introduce el ID del usuario al que desea añadir un libro:");
						idUsuario = Utilidades.introducirCadena();
						Usuario usuario = (Usuario) ois.readObject();
						if (!usuario.getIdUsuario().equals(idUsuario)) {
							oos.writeObject(usuario);
						} else {
							buscarLibroUsuario(fichU, fichL, idUsuario, LibrosUsuarios);
							usuario.setLibrosPrestados(LibrosUsuarios);
							oos.writeObject(usuario);
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				if (!fichU.delete()) {
					System.out.println("No se pudo borrar el archivo original");
					return;
				}
				if (!tempFile.renameTo(fichU)) {
					System.out.println("No se pudo renombrar el archivo temporal");
					return;
				}
				if (encontrado) {
					System.out.println("Libro se ha añadido al Usuario correctamente");
				} else {
					System.out.println("Usuario no encontrado");
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error procesando el fichero");
			}
		}
	}

	private static void eliminarLibroDeUsuario(File fichU) {
		
		if (!fichU.exists()) {
            System.out.println("No hay usuarios.");
            return;

        }



        System.out.println("Introduce el ID del usuario:");
        String id = Utilidades.introducirCadena();
        File temp = new File("temp.dat");

        boolean encontrado = false;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(fichU));
            oos = new ObjectOutputStream(new FileOutputStream(temp));
            boolean finArchivo = false;
            
            while (!finArchivo) {

                try {

                    Usuario u = (Usuario) ois.readObject();
                    if (u.getIdUsuario().equals(id)) {
                        if (!u.getLibrosPrestados().isEmpty()) {
                            System.out.println("Introduce ISBN del libro a eliminar:");
                            String isbn = Utilidades.introducirCadena();
                            Libro libroEliminar = null;
                            for (Libro l : u.getLibrosPrestados().keySet()) {

                                if (l.getIsbn() == isbn) {

                                    libroEliminar = l;

                                    break;

                                }

                            }

                            if (libroEliminar != null) {

                                u.getLibrosPrestados().remove(libroEliminar);

                                System.out.println("Libro eliminado correctamente");

                                encontrado = true;

                            } else {

                                System.out.println("Libro no encontrado en este usuario");

                            }

                        } else {

                            System.out.println("El usuario no tiene libros prestados");

                        }

                    }

                    oos.writeObject(u);

                } catch (EOFException e) {

                    finArchivo = true;

                }

            }



            ois.close();

            oos.close();

            fichU.delete();

            temp.renameTo(fichU);



            if (!encontrado) System.out.println("Usuario no encontrado o no tenía el libro");



        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error procesando usuarios");

        }


	}

	
	private static void borrarUsuario(File fichU) {
		boolean finArchivo = false;
		boolean encontrado = false;
		String idUsuario;
		if (!fichU.exists()) {
			System.out.println("El fichero no existe");
		} else {
			File tempFile = new File("temp.dat");
			System.out.println("Introduce el ID del usuario a dar de baja:");
			idUsuario = Utilidades.introducirCadena();
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichU));
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) { 
				while (!finArchivo) {
					try {
						Usuario usuario = (Usuario) ois.readObject();
						if (!usuario.getIdUsuario().equals(idUsuario)) {
							oos.writeObject(usuario);
						} else {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}
				if (!fichU.delete()) {
					System.out.println("No se pudo borrar el archivo original");
					return;
				}
				if (!tempFile.renameTo(fichU)) {
					System.out.println("No se pudo renombrar el archivo temporal");
					return;
				}
				if (encontrado) {
					System.out.println("Usuario eliminado correctamente");
				} else {
					System.out.println("Usuario no encontrado");
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error procesando el fichero");
			}
		}
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
	private static void buscarUsuarioId(File fichU) {
		if (!fichU.exists()) {
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
}