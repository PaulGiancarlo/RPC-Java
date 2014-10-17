package ServidorServicios;

public class ExcepcionServicioExistente extends Exception {

    public ExcepcionServicioExistente() {
        super();
    }

    public ExcepcionServicioExistente(String message) {
        super(message);
    }
}
