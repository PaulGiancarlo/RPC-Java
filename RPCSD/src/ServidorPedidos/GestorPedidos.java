package ServidorPedidos;

import GlobalPedidos.PedidoPaquete;
import GlobalPedidos.RespuestaPaquete;
import GlobalSeciones.Sesion;
import GlobalSeciones.SesionPaquete;
import GlobalSeciones.SesionPaqueteEscucha;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fabian
 */
public class GestorPedidos implements SesionPaqueteEscucha {
    private ThreadPoolExecutor ejecutor;

    private PedidoPaqueteEscucha pedidoPaqueteEscucha;
    private final Object pedidoPaqueteEscucha_bloqueo = new Object();

    
    public GestorPedidos(int tamaño) {
        ejecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(tamaño);
    }

    protected void finalize() throws Throwable {
        try {
            ejecutor.shutdown();

        } finally {
            super.finalize();
        }
    }

    public PedidoPaqueteEscucha getPedidoPaqueteEscucha() {
        synchronized (pedidoPaqueteEscucha_bloqueo) {
            return pedidoPaqueteEscucha;
        }
    }

    public void setPedidoPaqueteEscucha(PedidoPaqueteEscucha pedidoPaqueteEscucha) {
        synchronized (pedidoPaqueteEscucha_bloqueo) {
            this.pedidoPaqueteEscucha = pedidoPaqueteEscucha;
        }
    }

    // implements SesionPaqueteEscucha
    public void sesionPaqueteLLegados(SesionPaquete sesionPaquete, final Sesion sesion) {
        final PedidoPaquete pedidoPaquete = (PedidoPaquete)sesionPaquete.getPayload();

        if (pedidoPaquete == null)
            return;

        ejecutor.execute(new Runnable() {
            public void run() {
                try {
                    ContextoTrabajo.setContexto(sesion.getContext());
                    RespuestaPaquete respuestaPaquete = pedidoPaqueteEscucha.pedidoPaqueteEscucha(pedidoPaquete);
                    sesion.enviar(new SesionPaquete(respuestaPaquete));

                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    ContextoTrabajo.setContexto(null);
                }
            }
        });
    }
}
