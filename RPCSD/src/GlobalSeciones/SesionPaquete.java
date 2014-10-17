package GlobalSeciones;

import java.io.Serializable;

/**
 * This class represents the data unit of the Session layer.
 * @author enriquer
 */
public class SesionPaquete implements Serializable {
    private Serializable payload;

    public SesionPaquete(Serializable payload) {
        this.payload = payload;
    }

    public Serializable getPayload() {
        return payload;
    }
}
