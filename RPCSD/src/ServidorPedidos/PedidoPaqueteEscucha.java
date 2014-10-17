package ServidorPedidos;

import GlobalPedidos.PedidoPaquete;
import GlobalPedidos.RespuestaPaquete;

/**
 * @author fabian
 */
public interface PedidoPaqueteEscucha {
    RespuestaPaquete pedidoPaqueteEscucha(PedidoPaquete pedidoPaquete);
}
