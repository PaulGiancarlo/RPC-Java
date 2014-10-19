package Servidor.ServidorServicios;

//import GlobalServicios.ExcepcionNoEjecutada;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author fabian
 */
public class DespacharEjecucion implements InterceptarEjecucion {
    private final ArrayList<InterceptarEjecucion> interceptores = new ArrayList<InterceptarEjecucion>();

    public DespacharEjecucion() {
    }

    public void agregarEjecucionInterceptor(InterceptarEjecucion interceptor) {
        synchronized(interceptores) {
            interceptores.add(interceptor);
        }
    }

    public void eliminarEjecucionInterceptor(InterceptarEjecucion interceptor) {
        synchronized(interceptores) {
            interceptores.remove(interceptor);
        }
    }

    // implements InterceptarEjecucion
    public void enPreEjecucion(String nombreServicio, String nombreMetodo, Object[] parametros) throws RuntimeException //ExcepcionNoEjecutada 
    {
        ArrayList copiaLista;
        synchronized(interceptores) {
            copiaLista  = (ArrayList)interceptores.clone();
        }

        for (int i = 0, n = copiaLista.size(); i < n; i++) {
            InterceptarEjecucion interceptor = (InterceptarEjecucion)copiaLista.get(i);
            try { 
                interceptor.enPreEjecucion(nombreServicio, nombreMetodo, parametros);
            } catch (Exception ex) {
                //Logger.getLogger(DespacharEjecucion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
