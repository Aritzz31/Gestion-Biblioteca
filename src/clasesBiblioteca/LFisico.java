package clasesBiblioteca;

import java.time.LocalDate;

public class LFisico extends Libro {
	private boolean tapaDura;
	private int disponibles;
	
	public LFisico(String titulo, String isbn, String autor, Genero genero, LocalDate fechaReserva,
			LocalDate fechaDevolucion, boolean tapaDura, int disponibles) {
		super(titulo, isbn, autor, genero);
		this.tapaDura = tapaDura;
		this.disponibles = disponibles;
	}
	
	public LFisico(String titulo, String isbn, String autor, Genero genero, boolean tapaDura, int disponibles) {
		super(titulo, isbn, autor, genero);
		this.tapaDura = tapaDura;
		this.disponibles = disponibles;
	}

	public boolean isTapaDura() {
		return tapaDura;
	}


	public void setTapaDura(boolean tapaDura) {
		this.tapaDura = tapaDura;
	}


	public int getDisponibles() {
		return disponibles;
	}


	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}


	@Override
	public String toString() {
		return "LFisico [tapaDura=" + tapaDura + ", disponibles=" + disponibles + "]";
	}
}
