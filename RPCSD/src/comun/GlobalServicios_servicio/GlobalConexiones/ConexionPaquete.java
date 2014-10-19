package comun.GlobalServicios_servicio.GlobalConexiones;

import java.io.Serializable;

/**
 * @author fabian
 */
public class ConexionPaquete implements Serializable {
    private Serializable carga;

    public ConexionPaquete(Serializable carga) {
        this.carga = carga;
    }

    public Serializable getCarga() {
        return carga;
    }
}
