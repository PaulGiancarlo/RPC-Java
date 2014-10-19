package comun.GlobalServicios_servicio.GlobalServicios_servicio;

//import GlobalServicios.ExcepcionServicioNoEncontrado;
import comun.GlobalServicios_servicio.RPCServidorServicio;
import Servidor.ServidorServicios.Servicio;

/**
 * @author fabian
 */
public class RPCServidorServicioImpl extends Servicio implements RPCServidorServicio {
    public RPCServidorServicioImpl() {
    }

    public Class getInterfazServicio(String servicioId) throws Exception //ExcepcionServicioNoEncontrado 
    {
        return gestorServicio.getInterfazServicio(servicioId);
    }
}
