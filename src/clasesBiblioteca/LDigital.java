package clasesBiblioteca;

import java.time.LocalDate;

public class LDigital extends Libro {
	private String formato;
	private double tamanoMB;
	
	public LDigital(String titulo, String isbn, String autor, Genero genero, LocalDate fechaReserva,
			LocalDate fechaDevolucion, String formato, double tamanoMB) {
		super(titulo, isbn, autor, genero);
		this.formato = formato;
		this.tamanoMB = tamanoMB;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
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
