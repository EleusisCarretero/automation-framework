package utilities;

public enum ApiMethods {
	GET("GET"), 
	POST("POST"),
	PUT("PUT"),
	DELETE("DELETE"),
	UPDATE("UPDATE");
	
	private final String descripcion;

	ApiMethods(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
