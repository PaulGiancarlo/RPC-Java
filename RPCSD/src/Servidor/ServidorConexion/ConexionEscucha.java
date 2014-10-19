package Servidor.ServidorConexion;

import comun.GlobalServicios_servicio.GlobalConexiones.Conexion;

/**
 * @author fabian
 */
public interface ConexionEscucha {


     // Este método se invoca cuando un cliente se conecta al servidor.
     
    void nuevaConexion(Conexion conexion);
}
