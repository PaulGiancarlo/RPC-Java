package ClientesServicios;

import ClientesPedidos.GestorPedidos;
import GlobalServicios.ExcepcionServicioNoEncontrado;
import GlobalServicios_servicio.RPCServidorServicio;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fabian
 */
public class HacerServiciosProxy {
    private GestorPedidos gestorPedidos;
    private InvocadorServicios invocardor;
    private RPCServidorServicio rpcServidorServicio;
    private Map<String, Servicio> servicios = new HashMap<String, Servicio>();

    public HacerServiciosProxy(GestorPedidos gestorPedidos) {
        this.gestorPedidos = gestorPedidos;
        this.invocardor = new InvocadorServicios(gestorPedidos, RPCServidorServicio.NOMBRE);
    }

    public Servicio crearProxy(String nombreServicio) throws ExcepcionServicioNoEncontrado {
        Servicio servicio = servicios.get(nombreServicio);
        if (servicio == null) {
            Class servicioInterface = getServiceInterface(nombreServicio);

            servicio = (Servicio)Proxy.newProxyInstance(servicioInterface.getClassLoader(),
                                       new Class[] {servicioInterface},
                                       new InvocadorServicios(gestorPedidos, nombreServicio));
            servicios.put(nombreServicio, servicio);
        }

        return servicio;
    }

    private Class getServiceInterface(String servicioId) throws ExcepcionServicioNoEncontrado {
        if (rpcServidorServicio == null) {
            rpcServidorServicio = (RPCServidorServicio)Proxy.newProxyInstance(RPCServidorServicio.class.getClassLoader(),
                                                                        new Class[] {RPCServidorServicio.class},
                                                                        invocardor);
        }
        return rpcServidorServicio.getInterfazServicio(servicioId);
    }
}
