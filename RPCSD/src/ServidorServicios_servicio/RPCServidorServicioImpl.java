package ServidorServicios_servicio;

//import GlobalServicios.ExcepcionServicioNoEncontrado;
import GlobalServicios_servicio.RPCServidorServicio;
import ServidorServicios.Servicio;

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
