package GlobalSeciones;

import GlobalConexiones.Conexion;
import GlobalConexiones.ConexionPaquete;
import GlobalConexiones.ConexionPaqueteEscucha;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author enriquer
 */
public class Sesion implements ConexionPaqueteEscucha {
    private Conexion conexion = null;
    private final Object conexion_bloqueo = new Object();

    private SesionPaqueteEscucha sesionPaqueteEscucha = null;
    private final Object sesionPaqueteEscucha_lock = new Object();

    private Map<String, Object> contexto = new HashMap<String, Object>();

    //envio la conexion a la sesion, para que empieze a correr el hilo de conexion
    public Sesion(Conexion conexion) {
        this.conexion = conexion;
        conexion.setConnectionPacketListener(this);
    }

    public Map<String, Object> getContext() {
        return contexto;
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();

        } catch (Throwable ex) {
            throw ex;

        } finally {
            kill();
        }
    }

    public void kill() {
        synchronized (conexion_bloqueo) {
            if (contexto != null) {
                contexto.clear();
            }
            contexto = null;
            if (conexion != null) {
                conexion.apagar();
                conexion = null;
            }
        }
    }

    public void enviar(SesionPaquete paquete) throws IOException {
        synchronized (conexion_bloqueo) {
            if (conexion != null) {
                conexion.enviar(new ConexionPaquete(paquete));
            } else {
                throw new IOException("sesion no conecto");
            }
        }
    }

    public void setSesionPaqueteEscucha(SesionPaqueteEscucha sesionPaqueteEscucha) {
        synchronized (sesionPaqueteEscucha_lock) {
            this.sesionPaqueteEscucha = sesionPaqueteEscucha;
        }
    }
    public SesionPaqueteEscucha getSessionPacketListener() {
        synchronized (sesionPaqueteEscucha_lock) {
            return sesionPaqueteEscucha;
        }
    }

    public void setConexion(Conexion con) {
        synchronized (conexion_bloqueo) {
            conexion = con;
            conexion.setConnectionPacketListener(this);
        }
    }
    public Conexion getConexion() {
        synchronized (conexion_bloqueo) {
            return conexion;
        }
    }

    // implements ConexionPaqueteEscucha
    public void conexionPaqueteLLegado(ConexionPaquete packet) {
        synchronized (sesionPaqueteEscucha_lock) {
            if (sesionPaqueteEscucha != null) {
                sesionPaqueteEscucha.sesionPaqueteLLegados((SesionPaquete)packet.getCarga(), this);
            }
        }
    }
    public void conexionPerdida() {
        kill();
    }
}
