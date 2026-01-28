package clasesBiblioteca;

import java.time.LocalDate;

public class LDigital extends Libro {
	private Formato formato;
	private double tamanoMB;
	
	public LDigital(String titulo, String isbn, String autor, Genero genero, LocalDate fechaReserva,
			LocalDate fechaDevolucion, String formato, double tamanoMB) {
		super(titulo, isbn, autor, genero);
		this.formato = Formato.valueOf(formato);
		this.tamanoMB = tamanoMB;
		
	}
	
	public LDigital(String titulo, String isbn, String autor, Genero genero, Formato formato, double tamanoMB) {
		super(titulo, isbn, autor, genero);
		this.formato = formato;
		this.tamanoMB = tamanoMB;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public double getTamanoMB() {
		return tamanoMB;
	}

	public void setTamanoMB(double tamanoMB) {
		this.tamanoMB = tamanoMB;
	}

	@Override
	public String toString() {
		return "LDigital [formato=" + formato + ", tamanoMB=" + tamanoMB + "]";
	}
}
