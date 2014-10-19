package ServidorServicios;

//import GlobalServicios.ExcepcionNoEjecutada;

public interface InterceptarEjecucion {
    void enPreEjecucion(String nombreServicio, String nombreMetodo, Object[] parametros) throws Exception;
}
