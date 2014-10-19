package comun.GlobalServicios_servicio.GlobalConexiones;

/**
 * @author fabian
 */
public interface ConexionPaqueteEscucha {

    //Este metodo se llama cuando a llegado un packete de conexion
    void conexionPaqueteLLegado(ConexionPaquete paquete);

    //Este metodo se llama cuando es ha perdido la conexion
    void conexionPerdida();
}
