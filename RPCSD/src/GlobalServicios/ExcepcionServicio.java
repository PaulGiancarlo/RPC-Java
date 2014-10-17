package GlobalServicios;

/**
 * @author fabian
 */
public class ExcepcionServicio extends Exception {
    public ExcepcionServicio() {
        super();
    }

    public ExcepcionServicio(String mensaje) {
        super(mensaje);
    }

    public ExcepcionServicio(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public ExcepcionServicio(Throwable causa) {
        super(causa);
    }
}
