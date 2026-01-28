package clasesBiblioteca;
import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.io.Serializable;

public class Usuario implements Serializable {
	private String nombre;
	private String idUsuario; //El id sera Automatico al crear el usuario
	private String contraseña;
	private boolean Suspendido; //no puedes reservar mas libros hasta que devuelvas los libros que hayan terminado el plazo 
	private HashMap<Libro, LocalDate> librosPrestados;
	private LocalDate fechaNacimiento;
	private boolean mayorEdad;

	public Usuario(String nombre, String idUsuario, String contraseña, boolean suspendido,
			HashMap<Libro, LocalDate> librosPrestados, LocalDate fechaNacimiento, boolean mayorEdad) {
		super();
		this.nombre = nombre;
		this.idUsuario = idUsuario;
		this.contraseña = contraseña;
		Suspendido = suspendido;
		this.librosPrestados = librosPrestados;
		this.fechaNacimiento = fechaNacimiento;
		this.mayorEdad = ComprobarMayorEdad();
	}
	
	public Usuario(String nombre, String contraseña, LocalDate fechaNacimiento) {
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.fechaNacimiento = fechaNacimiento;
		this.idUsuario = IdAutomatico();
	}
	
	public boolean isMayorEdad() {
		return mayorEdad;
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

	public boolean isSuspendido() {
		return Suspendido;
	}

	public void setSuspendido(boolean suspendido) {
		Suspendido = suspendido;
	}

	public HashMap<Libro, LocalDate> getLibrosPrestados() {
		return librosPrestados;
	}

	public void setLibrosPrestados(HashMap<Libro, LocalDate> librosPrestados) {
		this.librosPrestados = librosPrestados;
	}
	
	
	
	public boolean ComprobarMayorEdad(){
	    LocalDate hoy = LocalDate.now();
	    int edad = hoy.getYear() - fechaNacimiento.getYear();
	    if (hoy.getMonthValue() < fechaNacimiento.getMonthValue() || 
	        (hoy.getMonthValue() == fechaNacimiento.getMonthValue() && hoy.getDayOfMonth() < fechaNacimiento.getDayOfMonth())) {
	        edad--;
	    }
	    return edad >= 18;
	}
	
	public String IdAutomatico() {
		return this.idUsuario = this.nombre.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 10000);
	}
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", idUsuario=" + idUsuario + ", contraseña=" + contraseña + ", Suspendido="
				+ Suspendido + ", librosPrestados=" + librosPrestados + ", fechaNacimiento=" + fechaNacimiento + "]";
	}
}
