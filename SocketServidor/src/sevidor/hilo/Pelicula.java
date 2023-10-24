package sevidor.hilo;

public class Pelicula {
	
	private String idPelicula;
	private String titulo;
	private String director;
	private int precio;
	
//CONSTRUCTOR POR DEFECTO:
	public Pelicula() {
		super();
	}

// CONSTRUCTOR CON TODOD:
public Pelicula(String idPelicula, String titulo, String director, int precio) {
	super();
	this.idPelicula = idPelicula;
	this.titulo = titulo;
	this.director = director;
	this.precio = precio;
}

//GETTER AND SETTERS::

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




	
	
	

	
}
