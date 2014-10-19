package Servidor.ServidorPedidos;

import Clientes.GlobalPedidos.PedidoPaquete;
import Clientes.GlobalPedidos.RespuestaPaquete;

/**
 * @author fabian
 */
public interface PedidoPaqueteEscucha {
    RespuestaPaquete pedidoPaqueteEscucha(PedidoPaquete pedidoPaquete);
}
