package comun.GlobalServicios_servicio;

import Clientes.ClientesServicios.Servicio;
//import GlobalServicios.ExcepcionServicioNoEncontrado;

/**
 * @author fabian
 */

public interface RPCServidorServicio extends Servicio {
    public final String NOMBRE = "__RPCServidorServicio";

    Class getInterfazServicio(String servicioId) throws Exception;//ExcepcionServicioNoEncontrado;
}
