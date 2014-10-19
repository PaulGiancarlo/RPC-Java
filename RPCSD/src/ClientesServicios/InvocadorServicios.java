package ClientesServicios;

import ClientesPedidos.GestorPedidos;
import GlobalPedidos.PedidoPaquete;
import GlobalPedidos.RespuestaPaquete;
//import GlobalServicios.ExcepcionServicio;
import GlobalServicios.ServicioPaquete;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author fabian
 */
public class InvocadorServicios implements InvocationHandler {
    private String nombreServicio = null;
    private GestorPedidos gestorPedidos = null;

    public InvocadorServicios(GestorPedidos gestorPedidos, String nombreServicio) {
        this.nombreServicio = nombreServicio;
        this.gestorPedidos = gestorPedidos;
    }

    // implementacion de  InvocationHandler
    // Tambien aqui se envia la consulta al servidor 
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        PedidoPaquete req = new PedidoPaquete(new ServicioPaquete(nombreServicio, m.getName(), args), getUnicoKey());

        RespuestaPaquete resultado = gestorPedidos.enviar(req);
        /*if (resultado.getData() instanceof ExcepcionServicio) {
            ExcepcionServicio e = (ExcepcionServicio)resultado.getData();
            throw e.getCause();
        }
*/
        return resultado.getDato();
    }

    private String getUnicoKey() {
         return Thread.currentThread().getName() + "_" + UUID.randomUUID();
    }
}
