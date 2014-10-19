package Servidor.ServidorServicios;

import Clientes.GlobalPedidos.PedidoPaquete;
import Clientes.GlobalPedidos.RespuestaPaquete;
//import GlobalServicios.ExcepcionNoEjecutada;
//import GlobalServicios.ExcepcionServicio;
//import GlobalServicios.ExcepcionServicioNoEncontrado;
import comun.GlobalServicios_servicio.GlobalServicios.ServicioPaquete;
import Servidor.ServidorPedidos.PedidoPaqueteEscucha;
import comun.GlobalServicios_servicio.GlobalServicios_servicio.RPCServidorServicioImpl;


//import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GestorServicio implements PedidoPaqueteEscucha {
    private final Map<String, Servicio> servicios = new HashMap<String, Servicio>();
    private Map<String, Class<? extends Clientes.ClientesServicios.Servicio>> serviciosIfaces =
            new HashMap<String, Class<? extends Clientes.ClientesServicios.Servicio>>();

    private DespacharEjecucion despachadorEjecuciones = new DespacharEjecucion();

    public GestorServicio() {
    }

    public void addService(String nombreServicio,
                           Class<? extends Clientes.ClientesServicios.Servicio> servicioIfaceClass,
                           Class<? extends Servidor.ServidorServicios.Servicio> servicioImplClass,
                           Map<String, String> parametros)throws Exception  {/*throws ExcepcionServicioExistente, ExcepcionServicioInvalida*/
        synchronized (servicios) {
            if (servicios.get(nombreServicio) != null) {
                throw new Exception("Servicio [" + nombreServicio + "] ya existe");
            }
//ExcepcionServicioExistente
            List<String> errors = ValidarServicio.validate(servicioIfaceClass, servicioImplClass);
            if (errors.size() > 0) {
                throw new Exception("Servicio [" + nombreServicio + "] tuvo un error");
            }
//ExcepcionServicioInvalida
            Servicio servicio;
            try {
                servicio = servicioImplClass.newInstance();
                if (parametros != null) {
                    for (String key : parametros.keySet()) {
                        String value = parametros.get(key);
                        servicio.getParametros().put(key, value);
                    }
                }
                servicio.setGestorServicio(this);
                servicio.init();
                servicios.put(nombreServicio, servicio);
                serviciosIfaces.put(nombreServicio, servicioIfaceClass);

            } catch (InstantiationException e) {
                errors = new ArrayList<String>();
                errors.add("InstantiationException: " + e.getMessage());
                throw new Exception("Service [" + nombreServicio + "] has errors");//ExcepcionServicioInvalida

            } catch (IllegalAccessException e) {
                errors = new ArrayList<String>();
                errors.add("IllegalAccessException: " + e.getMessage());
                throw new Exception("Service [" + nombreServicio + "] has errors");

            } catch (Exception e) {
                errors = new ArrayList<String>();
                errors.add("Exception: " + e.getMessage());
                throw new Exception("Service [" + nombreServicio + "] has errors");
            }
        }
    }

    
    public void eliminarServicio(String nombreServicio) {
        synchronized (servicios) {
            servicios.remove(nombreServicio);
        }
        Servicio servicio = getServicio(nombreServicio);
        servicio.destroy();
    }

    public Servicio getServicio(String nombreServicio) {
        synchronized (servicios) {
            return servicios.get(nombreServicio);
        }
    }

    public List<Servicio> getServicios() {
        List<Servicio> l = new ArrayList<Servicio>();
        synchronized (servicios) {
            for (Map.Entry<String, Servicio> entry : servicios.entrySet()) {
                l.add(entry.getValue());
            }
        }
        return l;
    }

    public List<String> getNombreServicios() {
        List<String> l = new ArrayList<String>();
        synchronized (servicios) {
            for (String nombreServicio : servicios.keySet()) {
                l.add(nombreServicio);
            }
        }
        return l;
    }

    
    public Class getInterfazServicio(String nombreServicio) throws Exception//ExcepcionServicioNoEncontrado 
    {
        synchronized(servicios) {
            Class servicioIface = serviciosIfaces.get(nombreServicio);

            //if (servicioIface == null)
             //   throw new ExcepcionServicioNoEncontrado("Servicio [" + nombreServicio + "] no encontrado.");

            return servicioIface;
        }
    }

    
    public void addExecutionInterceptor(InterceptarEjecucion interceptor) {
        despachadorEjecuciones.agregarEjecucionInterceptor(interceptor);
    }

    public void removeExecutionInterceptor(InterceptarEjecucion interceptor) {
        despachadorEjecuciones.eliminarEjecucionInterceptor(interceptor);
    }

    // implements PedidoPaqueteEscucha
    public RespuestaPaquete pedidoPaqueteEscucha(PedidoPaquete pedidoPaquete) {
        if (pedidoPaquete == null)
            return null;

        ServicioPaquete servicePacket = (ServicioPaquete)pedidoPaquete.getCarga();

        String nombreServicio = servicePacket.getnombreServicio();
        Servicio servicio = getServicio(nombreServicio);
        String nombreMetodo = servicePacket.getnombreMetodo();
        Object[] parametros = servicePacket.getParametros();

        try {
            Class servicioClass = servicio.getClass();

            if (!servicioClass.equals(RPCServidorServicioImpl.class)) {
                despachadorEjecuciones.enPreEjecucion(nombreServicio, nombreMetodo, parametros); // may throw ExcepcionNoEjecutada
            }

            Method metodo = ClasesUtiles.findMethodByName(servicioClass, nombreMetodo);
            Object dato = metodo.invoke(servicio, parametros);

            return new RespuestaPaquete(dato, pedidoPaquete.getKey());
        }catch(Exception e){}
            /*
        } catch (ExcepcionNoEjecutada ex) {
            return new RespuestaPaquete(new ExcepcionServicio(ex), pedidoPaquete.getKey());

        } catch (InvocationTargetException ex) {
            Throwable t = ex.getCause();
            return new RespuestaPaquete(new ExcepcionServicio(t), pedidoPaquete.getKey());

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }*/
        return null;
    }
}
