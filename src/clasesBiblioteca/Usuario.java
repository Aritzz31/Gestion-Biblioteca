package clasesBiblioteca;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.io.Serializable;

public class Usuario implements Serializable {
	private String nombre;
	private String idUsuario; //El id sera Automatico al crear el usuario
	private String contraseña;
	private HashMap<Libro, LocalDate> librosPrestados;
	private LocalDate fechaNacimiento;

	public Usuario(String nombre, String idUsuario, String contraseña, LocalDate fechaNacimiento, HashMap<Libro, LocalDate> librosPrestados) {
		super();
		this.nombre = nombre;
		this.idUsuario = idUsuario;
		this.contraseña = contraseña;
		this.librosPrestados = librosPrestados;
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public Usuario(String nombre, String contraseña, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.fechaNacimiento = fechaNacimiento;
		this.idUsuario = IdAutomatico();
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public HashMap<Libro, LocalDate> getLibrosPrestados() {
		return librosPrestados;
	}

	public void setLibrosPrestados(HashMap<Libro, LocalDate> librosPrestados) {
		this.librosPrestados = librosPrestados;
	} 
	
	public String IdAutomatico() {
		return this.idUsuario = this.nombre.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 10000);
	}
	@Override
	public String toString() {
		return  "Usuario --> " +nombre +" \nID: "+ idUsuario +" | Contraseña: "+contraseña+" | Fecha de Nacimiento: " + fechaNacimiento +" | \nLibros Prestados: " + librosPrestados + " <--- Esta es su fecha limite de devolucion \n";
	}
}
