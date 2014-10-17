package ServidorServicios;

import java.util.List;
import java.util.ArrayList;

/**
 * @author fabian
 */
public class ExcepcionServicioInvalida extends Exception {
    private List<String> errores;

    public ExcepcionServicioInvalida(String mensaje) {
        this(mensaje, new ArrayList<String>());
    }

    public ExcepcionServicioInvalida(String mensaje, List<String> errores) {
        super(mensaje);
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }
    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
}
