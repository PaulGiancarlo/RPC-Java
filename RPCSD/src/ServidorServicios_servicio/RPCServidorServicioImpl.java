package ServidorServicios_servicio;

import GlobalServicios.ExcepcionServicioNoEncontrado;
import GlobalServicios_servicio.RPCServidorServicio;
import ServidorServicios.Servicio;

/**
 * @author enriquer
 */
public class RPCServidorServicioImpl extends Servicio implements RPCServidorServicio {
    public RPCServidorServicioImpl() {
    }

    public Class getInterfazServicio(String servicioId) throws ExcepcionServicioNoEncontrado {
        return gestorServicio.getInterfazServicio(servicioId);
    }
}
