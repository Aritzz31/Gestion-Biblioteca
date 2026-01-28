package mainGestionBiblioteca;

import utilidades.Utilidades;

public class GestionarBiblioteca {

	public static void main(String[] args) {

		int opcion;
		do {
			opcion = mostrarMenu();
			System.out.println("---MENU PRINCIPAL DE LA GESTION DE LA BIBLIOTECA---");
			switch (opcion) {
			case 1:
			
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
				break;
			}
		} while (opcion != 8);
	}
	
	public static int mostrarMenu() {
		System.out.println("----- Gestion de Biblioteca -----");
		System.out.println("1. Crear Usuario");
		System.out.println("2. Añadir un nuevo Libro");
		System.out.println("3. Borrar usuario");
		System.out.println("4. Eliminar libro prestado de usuario");
		System.out.println("5.Listar usuarios con sus respectivos libros");
		System.out.println("6. Listar catalogo de libros");
		System.out.println("7. Buscar usuario por ID");
		System.out.println("8.Modificar datos de libros");
		
		return Utilidades.leerInt(1, 8);
	}
	
	public void menu () {
		
	}

}
