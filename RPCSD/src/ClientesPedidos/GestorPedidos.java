package ClientesPedidos;

import GlobalPedidos.PedidoPaquete;
import GlobalPedidos.RespuestaPaquete;
import GlobalSeciones.Sesion;
import GlobalSeciones.SesionPaquete;
import GlobalSeciones.SesionPaqueteEscucha;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fabian
 */
public class GestorPedidos implements SesionPaqueteEscucha {
    private final Map<String, Futuro> futuros = new HashMap<String, Futuro>();
    private Sesion sesion;

    public GestorPedidos(Sesion sesion) {
        this.sesion = sesion;
    }

    public Sesion getSession() {
        return sesion;
    }
    public void setSession(Sesion sesion) {
        this.sesion = sesion;
    }

    public RespuestaPaquete enviar(PedidoPaquete r) throws InterruptedException, IOException {
        Futuro futuro;
        synchronized (futuros) {
            futuro = futuros.get(r.getKey());
            if (futuro == null) {
                futuro = new Futuro();
                futuros.put(r.getKey(), futuro);
            }
        }
        synchronized (futuro) {
            sesion.enviar(new SesionPaquete(r));
            futuro.wait();
        }

        RespuestaPaquete rp = (RespuestaPaquete)futuro.getDato();
        futuro.setDato(null);

        return rp;
    }

    // implements SesionPaqueteEscucha
    public void sesionPaqueteLLegados(SesionPaquete sessionPacket, Sesion session) {
        RespuestaPaquete respuestaPaquete = (RespuestaPaquete)sessionPacket.getPayload();
        Futuro future;
        synchronized (futuros) {
            future = futuros.get(respuestaPaquete.getKey());
        }
        synchronized (future) {
            future.setDato(respuestaPaquete);
            future.notify();
        }
    }
}
