package ServidorServicios;

import ServidorPedidos.ContextoTrabajo;

import java.util.HashMap;
import java.util.Map;

 // @author fabian
 
public class Servicio {
    private Map<String, String> parametros = new HashMap<String, String>();

    protected GestorServicio gestorServicio;

    public Servicio() {
    }

    public void init() throws Exception {
    }

    public Map<String, String> getParametros() {
        return parametros;
    }

    public Map<String, Object> getSesionContexto() {
        return ContextoTrabajo.getContexto();
    }

    public void destroy() {
    }

    void setGestorServicio(GestorServicio gestorServicio) {
        this.gestorServicio = gestorServicio;
    }
}
