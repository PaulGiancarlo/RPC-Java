package GlobalServicios;

/**
 * @author fabian
 */
public class ExcepcionServicioNoEncontrado extends Exception {
    public ExcepcionServicioNoEncontrado() {
    }

    public ExcepcionServicioNoEncontrado(String mensaje) {
        super(mensaje);
    }
}
