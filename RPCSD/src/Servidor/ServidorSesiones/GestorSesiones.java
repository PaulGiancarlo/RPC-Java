package Servidor.ServidorSesiones;

import comun.GlobalServicios_servicio.GlobalConexiones.Conexion;
import comun.GlobalServicios_servicio.GlobalSeciones.Sesion;
import comun.GlobalServicios_servicio.GlobalSeciones.SesionPaqueteEscucha;
import Servidor.ServidorConexion.ConexionEscucha;

/**
 * @author 
 */

public class GestorSesiones implements ConexionEscucha {
    private SesionPaqueteEscucha sesionPaqueteEscucha = null;
    private final Object sesionPaqueteEscucha_bloqueo = new Object();

    public GestorSesiones() {
    }

    public void setSesionPaqueteEscucha(SesionPaqueteEscucha escuchador){
        synchronized (sesionPaqueteEscucha_bloqueo) {
            sesionPaqueteEscucha = escuchador;
        }
    }

    // implements ConexionEscucha
    public void nuevaConexion(Conexion conexion) {
        Sesion session = new Sesion(conexion);
        synchronized (sesionPaqueteEscucha_bloqueo) {
            session.setSesionPaqueteEscucha(sesionPaqueteEscucha);
        }
    }
}
