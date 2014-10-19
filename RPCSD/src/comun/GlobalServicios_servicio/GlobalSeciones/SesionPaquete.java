package comun.GlobalServicios_servicio.GlobalSeciones;

import java.io.Serializable;

/**
 * 
 * @author fabian
 */
public class SesionPaquete implements Serializable {
    private Serializable carga;

    public SesionPaquete(Serializable carga) {
        this.carga = carga;
    }

    public Serializable getPayload() {
        return carga;
    }
}
