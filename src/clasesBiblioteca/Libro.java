package clasesBiblioteca;
import java.io.Serializable;

public abstract class Libro implements Serializable {

	protected String titulo;
	protected int isbn;
	protected String autor;
	protected Genero genero;

	public Libro(String titulo, String isbn, String autor, Genero genero) {
		super();
		this.titulo = titulo;
		this.isbn = (int)Math.random() * 1000000000; //ISBN aleatorio
		this.autor = autor;
		this.genero = genero;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
}
