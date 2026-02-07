package mainGestionBiblioteca;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import utilidades.Utilidades;
import clasesBiblioteca.Libro;
import clasesBiblioteca.Usuario;
import clasesBiblioteca.Genero;
import clasesBiblioteca.LDigital;
import clasesBiblioteca.LFisico;
import clasesBiblioteca.FechaNacimientoException;
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
		fillDataFich(fichL, fichU);
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
				eliminarLibroDeUsuario(fichU);
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
				modificarDatosLibros(fichL);
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
		LocalDate fechaNacimiento = null,fechaActual;
		String nombre, contraseña;
		Usuario nuevoUsuario;
		MyObjectOutputStream moos;
		ObjectOutputStream oos;
		boolean fechaCorrecta = false;
		fechaActual = LocalDate.now();

		System.out.println("Introduce el nombre del usuario:");
		nombre = Utilidades.introducirCadena(); // El nombre no puede tener menos de 2 caracteres
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
				System.out.println(
						"La contraseña debe tener al menos 8 caracteres o numeros. Introduce la contraseña del usuario:");
				contraseña = Utilidades.introducirCadena();
			}
		} catch (Exception e) {
			System.out.println("Error al introducir la contraseña del usuario.");
		}

		System.out.println("Introduce la fecha de nacimiento del usuario (formato: YYYY-MM-DD):");
		//Deber ser mayor de edad
		while (!fechaCorrecta) {
			try {
				fechaNacimiento = LocalDate.parse(Utilidades.introducirCadena());
				validarFechaNacimiento(fechaNacimiento, fechaActual);
				fechaCorrecta = true;
			} catch (DateTimeParseException e) {
				System.out.println("Formato de fecha no valido. Introduce la fecha de nacimiento del usuario (formato: YYYY-MM-DD):");
			} catch (FechaNacimientoException e) {
				System.out.println(e.getMessage() + " Introduce la fecha de nacimiento del usuario (formato: YYYY-MM-DD):");
			}
		}


		nuevoUsuario = new Usuario(nombre, contraseña, fechaNacimiento);

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
			System.out.println("Se ha creado el usuario '" + nuevoUsuario.getNombre()
			+ "' correctamente. Este sera el ID del Usuario: " + nuevoUsuario.getIdUsuario());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void validarFechaNacimiento(LocalDate fechaNacimiento, LocalDate fechaActual) {
	    if (fechaNacimiento.isAfter(fechaActual)) {
	        throw new FechaNacimientoException("La fecha no puede ser mayor a " + fechaActual + " .");
	    }
	    if (fechaNacimiento.isAfter(fechaActual.minusYears(18))) {
	        throw new FechaNacimientoException("Debes ser mayor de edad.");
	    }
	}

	private static void fillDataFich(File fichL, File fichU) {

	    if (!fichL.exists()) {
	        ObjectOutputStream oos;
	        try {
	            oos = new ObjectOutputStream(new FileOutputStream(fichL, true));

	            Libro libroF1 = new LFisico("El Quijote", "1234567890123", "Miguel de Cervantes", Genero.AVENTURAS, true, 5);
	            Libro libroF2 = new LFisico("Dracula", "3216549876543", "Bram Stoker", Genero.TERROR, false, 3);
	            Libro libroF3 = new LFisico("Tin Tin", "9263282332482", "Georges Remi", Genero.AVENTURAS, true, 8);
	            Libro libroF4 = new LFisico("One Piece", "2637272883232", "Eiichiro Oda", Genero.AVENTURAS, false, 7);
	            Libro libroF5 = new LFisico("Orgullo y prejuicio", "8462835007841", "Jane Austen", Genero.ROMANCE, true, 4);
	            Libro libroF6 = new LFisico("Los Juegos del Hambre", "9780439023528", "Suzanne Collins", Genero.DISTOPICO, true, 6);
	            Libro libroF7 = new LFisico("El Hobbit", "9780547928227", "J.R.R. Tolkien", Genero.FICCION, true, 10);
	            Libro libroF8 = new LFisico("Sherlock Holmes", "9788491050293", "Arthur Conan Doyle", Genero.DISTOPICO, false, 4);
	            Libro libroF9 = new LFisico("La Historia Interminable", "9788420435737", "Michael Ende", Genero.FICCION, true, 5);
	            Libro libroF10 = new LFisico("Romeo y Julieta", "9788497941021", "William Shakespeare", Genero.ROMANCE, false, 2);

	            Libro libroD1 = new LDigital("1984", "9876543210123", "George Orwell", Genero.FICCION, Formato.PDF, 2.5);
	            Libro libroD2 = new LDigital("El Principito", "4567891230123", "Antoine de Saint-Exupéry", Genero.AVENTURAS, Formato.TXT, 1.2);
	            Libro libroD3 = new LDigital("Fahrenheit 451", "1192820576467", "Ray Bradbury", Genero.DISTOPICO, Formato.PDF, 2.5);
	            Libro libroD4 = new LDigital("It", "0000000483628", "Stephen King", Genero.TERROR, Formato.EPUB, 1.2);
	            Libro libroD5 = new LDigital("Dune", "4863766767676", "Frank Herbert", Genero.FICCION, Formato.TXT, 2.5);
	            Libro libroD6 = new LDigital("La Metamorfosis", "9788420674204", "Franz Kafka", Genero.FICCION, Formato.PDF, 1.1);
	            Libro libroD7 = new LDigital("Ready Player One", "9780307887443", "Ernest Cline", Genero.FICCION, Formato.EPUB, 3.0);
	            Libro libroD8 = new LDigital("El Código Da Vinci", "9780307474278", "Dan Brown", Genero.DISTOPICO, Formato.PDF, 2.8);
	            Libro libroD9 = new LDigital("La Sombra del Viento", "9788408172177", "Carlos Ruiz Zafón", Genero.DISTOPICO, Formato.EPUB, 2.6);
	            Libro libroD10 = new LDigital("El Alquimista", "9780061122415", "Paulo Coelho", Genero.AVENTURAS, Formato.TXT, 1.0);

	            oos.writeObject(libroF1);
	            oos.writeObject(libroF2);
	            oos.writeObject(libroF3);
	            oos.writeObject(libroF4);
	            oos.writeObject(libroF5);
	            oos.writeObject(libroF6);
	            oos.writeObject(libroF7);
	            oos.writeObject(libroF8);
	            oos.writeObject(libroF9);
	            oos.writeObject(libroF10);

	            oos.writeObject(libroD1);
	            oos.writeObject(libroD2);
	            oos.writeObject(libroD3);
	            oos.writeObject(libroD4);
	            oos.writeObject(libroD5);
	            oos.writeObject(libroD6);
	            oos.writeObject(libroD7);
	            oos.writeObject(libroD8);
	            oos.writeObject(libroD9);
	            oos.writeObject(libroD10);

	            oos.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    HashMap<Libro, LocalDate> librosPrestados = new HashMap<Libro, LocalDate>();
	    librosPrestados.put(
	        new LFisico("El Quijote", "1234567890123", "Miguel de Cervantes", Genero.AVENTURAS, true, 5),
	        LocalDate.now().plusWeeks(2)
	    );
	    librosPrestados.put(
	        new LDigital("1984", "9876543210123", "George Orwell", Genero.FICCION, Formato.PDF, 2.5),
	        LocalDate.now().plusWeeks(2)
	    );

	    if (!fichU.exists()) {
	        ObjectOutputStream oos;
	        try {
	            oos = new ObjectOutputStream(new FileOutputStream(fichU, true));

	            Usuario manolo69 = new Usuario("Manolo", "MAN-8236", "contrasena",
	                    LocalDate.of(1990, 5, 15), librosPrestados);
	            Usuario vagabundo33 = new Usuario("Enio", "ENI-3628", "lasArepasSonVenezolanas",
	                    LocalDate.of(2007, 1, 1), librosPrestados);
	            Usuario elPrimoEustaquio = new Usuario("Primo Eustaquio", "PRI-9556", "00000000",
	                    LocalDate.of(1985, 10, 20), librosPrestados);

	            oos.writeObject(manolo69);
	            oos.writeObject(vagabundo33);
	            oos.writeObject(elPrimoEustaquio);

	            oos.close();

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


	private static void buscarLibroUsuario(File fichU, File fichL, String idUsuario,
			HashMap<Libro, LocalDate> LibrosUsuarios) {
		String isbnLibro;
		Libro libro;
		new HashMap<Libro, LocalDate>();
		boolean encontrado = false;
		boolean finArchivo = false;
		System.out.println("Introduce el ISBN del libro a añadir:");
		isbnLibro = Utilidades.introducirCadena();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichL))) {
			while (!finArchivo || !encontrado) {
				try {
					libro = (Libro) ois.readObject();
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
		Usuario usuarios;
		boolean encontrado = false;
		boolean finArchivo = false;
		System.out.println("Introduce el Id del usuario:");
		idUsuario = Utilidades.introducirCadena();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichU))) {
			while (!finArchivo || !encontrado) {
				try {
					usuarios = (Usuario) ois.readObject();
					if (usuarios.getIdUsuario().equalsIgnoreCase(idUsuario)) {
						System.out.println(usuarios.toString());
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

	private static void añadirLibroUsuario(File fichU, File fichL, String idUsuario,
			HashMap<Libro, LocalDate> librosUsuarios) {

		boolean finArchivo = false;
		boolean encontrado = false;
		boolean ficheroValido = true;

		if (!fichU.exists()) {
			System.out.println("El fichero de usuarios no existe");
			ficheroValido = false;

		}

		if (ficheroValido) {
			System.out.println("Introduce el ID del usuario al que desea añadir un libro:");
			idUsuario = Utilidades.introducirCadena();
			File tempFile = new File("temp.dat");
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;

			try {
				ois = new ObjectInputStream(new FileInputStream(fichU));
				oos = new ObjectOutputStream(new FileOutputStream(tempFile));
				while (!finArchivo) {

					try {
						Usuario usuario = (Usuario) ois.readObject();
						if (!usuario.getIdUsuario().equalsIgnoreCase(idUsuario)) {
							oos.writeObject(usuario);
						} else {
							buscarLibroUsuario(fichU, fichL, idUsuario, librosUsuarios);
							usuario.setLibrosPrestados(librosUsuarios);
							oos.writeObject(usuario);
							encontrado = true;
						}

					} catch (EOFException e) {
						finArchivo = true;
					}
				}

				ois.close();
				oos.close();
				boolean borrado = fichU.delete();
				boolean renombrado = tempFile.renameTo(fichU);
				if (!borrado) {
					System.out.println("No se pudo borrar el archivo original");
				}
				if (!renombrado) {
					System.out.println("No se pudo renombrar el archivo temporal");
				}
				if (encontrado) {
					System.out.println("Libro añadido al usuario correctamente");
				} else {
					System.out.println("Usuario no encontrado");
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error procesando el fichero de usuarios");
			}
		}
	}

	public static void eliminarLibroDeUsuario(File fichU) {
		if (!fichU.exists()) {
			System.out.println("No hay usuarios.");
			return;
		}

		System.out.println("Introduce el ID del usuario:");
		String id = Utilidades.introducirCadena();
		File temp = new File("temp.dat");
		boolean encontradoUsuario = false;
		boolean libroEliminado = false;
		boolean finArchivo = false;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichU));
			oos = new ObjectOutputStream(new FileOutputStream(temp));
			while (!finArchivo) {
				try {
					Usuario u = (Usuario) ois.readObject();
					if (u.getIdUsuario().equalsIgnoreCase(id)) {
						encontradoUsuario = true;
						if (!u.getLibrosPrestados().isEmpty()) {
							System.out.println("Introduce ISBN del libro a eliminar:");
							String isbn = Utilidades.introducirCadena();
							Libro libroEliminar = null;
							boolean encontradoLibro = false;
							for (Libro l : u.getLibrosPrestados().keySet()) {
								if (!encontradoLibro && l.getIsbn().equals(isbn)) {
									libroEliminar = l;
									encontradoLibro = true;
								}
							}

							if (libroEliminar != null) {
								u.getLibrosPrestados().remove(libroEliminar);
								libroEliminado = true;
								System.out.println("Libro eliminado correctamente");
							} else {
								System.out.println("El usuario no tiene ese libro");
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

			if (!encontradoUsuario) {
				System.out.println("Usuario no encontrado");
			} else if (!libroEliminado) {
				System.out.println("No se eliminó ningún libro");
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error procesando usuarios");
		}
	}

	private static void borrarUsuario(File fichU) {
		boolean finArchivo = false;
		boolean encontrado = false;
		boolean ficheroValido = true;
		String idUsuario;
		if (!fichU.exists()) {
			System.out.println("El fichero no existe");
			ficheroValido = false;
		}
		if (ficheroValido) {
			File tempFile = new File("temp.dat");
			System.out.println("Introduce el ID del usuario a dar de baja:");
			idUsuario = Utilidades.introducirCadena();
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;

			try {
				ois = new ObjectInputStream(new FileInputStream(fichU));
				oos = new ObjectOutputStream(new FileOutputStream(tempFile));
				while (!finArchivo) {

					try {
						Usuario usuario = (Usuario) ois.readObject();
						if (!usuario.getIdUsuario().equalsIgnoreCase(idUsuario)) {
							oos.writeObject(usuario);
						} else {
							encontrado = true;
						}
					} catch (EOFException e) {
						finArchivo = true;
					}
				}

				ois.close();
				oos.close();
				boolean borrado = fichU.delete();
				boolean renombrado = tempFile.renameTo(fichU);
				if (!borrado) {
					System.out.println("No se pudo borrar el archivo original");
				}
				if (!renombrado) {
					System.out.println("No se pudo renombrar el archivo temporal");
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
						System.out.println(u.toString());
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

	private static void modificarDatosLibros(File fichL) {
		boolean continuar=true,encontrado = false;
		boolean finArchivo = false;
		boolean error = false;
		int opcion;
		String isbnBuscado;

		if (!fichL.exists()) {
			System.out.println("El fichero de libros no existe.");
			continuar = false;
		}
		if(continuar) {
			System.out.println("Introduce el ISBN del libro a modificar:");
			isbnBuscado = Utilidades.introducirCadena();
			File temp = new File("tempLibros.dat");
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichL));
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp))) {

				while(!finArchivo) {
					try {
						Libro libro = (Libro) ois.readObject();
						if (!encontrado && libro.getIsbn().equals(isbnBuscado)) {
							encontrado = true;

							do {
								System.out.println("Libro encontrado:");
								System.out.println(libro);

								opcion = submenuModificarLibro(libro);

								switch (opcion) {
								case 1:
									System.out.println("Nuevo título:");
									libro.setTitulo(Utilidades.introducirCadena());
									break;

								case 2:
									System.out.println("Nuevo autor:");
									libro.setAutor(Utilidades.introducirCadena());
									break;

								case 3:
									System.out.println("Nuevo género:");
									try {
										libro.setGenero(Genero.valueOf(Utilidades.introducirCadena().toUpperCase()));
									} catch (IllegalArgumentException e) {
										System.out.println("Género inválido.");
									}
									break;


								case 4:
									if (libro instanceof LFisico) {
										LFisico lf = (LFisico) libro;
										System.out.println("Nuevo número de disponibles:");
										lf.setDisponibles(Utilidades.leerInt(0, 9999));
									}
									break;

								case 5:
									if (libro instanceof LDigital) {
										LDigital ld = (LDigital) libro;
										System.out.println("Nuevo formato:");
										try {
											ld.setFormato(Formato.valueOf(Utilidades.introducirCadena().toUpperCase()));
										} catch (IllegalArgumentException e) {
											System.out.println("Formato inválido.");
										}
									}
									break;

								case 6:
									if (libro instanceof LDigital) {
										LDigital ld = (LDigital) libro;
										System.out.println("Nuevo tamaño en MB:");
										ld.setTamanoMB(Utilidades.leerDouble());
									}
									break;

								case 0:
									System.out.println("Guardando cambios...");
									break;
								}

							} while (opcion != 0);
						}

						oos.writeObject(libro);

					}catch (EOFException e) {
						finArchivo = true;

					}
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Error procesando el fichero de libros.");
				error = true;
			}

			if (!error) {
				boolean borrado = fichL.delete();
				boolean renombrado = temp.renameTo(fichL);

				if (!borrado) System.out.println("No se pudo borrar el fichero original.");
				if (!renombrado) System.out.println("No se pudo renombrar el fichero temporal.");

				if (encontrado)
					System.out.println("Libro modificado correctamente.");
				else
					System.out.println("No se encontró ningún libro con ese ISBN.");
			}
		}
	}

	private static int submenuModificarLibro(Libro libro) {
		System.out.println("--- MENÚ MODIFICAR LIBRO ---");
		System.out.println("1- Cambiar título");
		System.out.println("2- Cambiar autor");
		System.out.println("3- Cambiar género");

		if (libro instanceof LFisico) {
			System.out.println("4- Cambiar disponibles (solo en libro físico)");
		}

		if (libro instanceof LDigital) {
			System.out.println("5- Cambiar formato (solo en libro digital)");
			System.out.println("6- Cambiar tamaño MB (solo en libro digital)");
		}

		System.out.println("0- Guardar y salir");
		return Utilidades.leerInt(0, 6);
	}
}