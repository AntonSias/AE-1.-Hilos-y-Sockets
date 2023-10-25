package sevidor.hilo;


public class PeliculaClass {
	private String idPelicula;
	private String titulo;
	private String director;
	private int precio;
	
	
	public PeliculaClass() {
		super();
	}


	public PeliculaClass(String idPelicula, String titulo, String director, int precio) {
		super();
		this.idPelicula = idPelicula;
		this.titulo = titulo;
		this.director = director;
		this.precio = precio;
	}


	public String getIdPelicula() {
		return idPelicula;
	}


	public void setIdPelicula(String idPelicula) {
		this.idPelicula = idPelicula;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDirector() {
		return director;
	}


	public void setDirector(String director) {
		this.director = director;
	}


	public int getPrecio() {
		return precio;
	}


	public void setPrecio(int precio) {
		this.precio = precio;
	}


	@Override
	public String toString() {
		return "PeliculaClass [idPelicula=" + idPelicula + ", titulo=" + titulo + ", director=" + director + ", precio="
				+ precio + "]";
	}
	
	
	
}