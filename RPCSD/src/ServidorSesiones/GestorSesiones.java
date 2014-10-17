package ServidorSesiones;

import GlobalConexiones.Conexion;
import GlobalSeciones.Sesion;
import GlobalSeciones.SesionPaqueteEscucha;
import ServidorConexion.ConexionEscucha;

/**
 * @author enriquer
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
