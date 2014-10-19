package comun.GlobalServicios_servicio.GlobalServicios;

import java.io.Serializable;

/**
 * @author fabian
 */
public class ServicioPaquete implements Serializable {
    private final String nombreServicio;
    private final String nombreMetodo;
    private final Object parametros[];

    public ServicioPaquete(String nombreServicio, String nombreMetodo, Object parametros[]) {
        this.nombreServicio = nombreServicio;
        this.nombreMetodo = nombreMetodo;
        this.parametros = parametros;
    }

    public String getnombreServicio() {
        return nombreServicio;
    }

    public String getnombreMetodo() {
        return nombreMetodo;
    }

    public Object[] getParametros() {
        return parametros;
    }
}
