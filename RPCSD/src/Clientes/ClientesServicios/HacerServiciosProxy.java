package Clientes.ClientesServicios;

import Clientes.ClientesPedidos.GestorPedidos;
//import GlobalServicios.ExcepcionServicioNoEncontrado;
import comun.GlobalServicios_servicio.RPCServidorServicio;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
//import java.util.logging.Level;
//import java.util.logging.Logger;

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

    public Servicio crearProxy(String nombreServicio)// throws ExcepcionServicioNoEncontrado 
    {
        Servicio servicio = servicios.get(nombreServicio);
        try{
        
        if (servicio == null) {
            Class servicioInterface = getServiceInterface(nombreServicio);

            servicio = (Servicio)Proxy.newProxyInstance(servicioInterface.getClassLoader(),
                                       new Class[] {servicioInterface},
                                       new InvocadorServicios(gestorPedidos, nombreServicio));
            servicios.put(nombreServicio, servicio);
        }
        }catch(Exception e){}
        return servicio;
    }

    private Class getServiceInterface(String servicioId)// throws ExcepcionServicioNoEncontrado 
    {
        if (rpcServidorServicio == null) {
            rpcServidorServicio = (RPCServidorServicio)Proxy.newProxyInstance(RPCServidorServicio.class.getClassLoader(),
                                                                        new Class[] {RPCServidorServicio.class},
                                                                        invocardor);
        }
        try {
            return rpcServidorServicio.getInterfazServicio(servicioId);
        } catch (Exception ex) {
        }
        return null;
    }
}
