package GlobalPedidos;

import java.io.Serializable;

/**
 * This class represents the data request unit of the request layer.
 * @author enriquer
 */
public class PedidoPaquete implements Serializable {
    private String key;
    private Serializable payload;

    public PedidoPaquete(Serializable payload, String key) {
        this.payload = payload;
        this.key = key;
    }

    public Serializable getPayload() {
        return payload;
    }

    public String getKey() {
        return key;
    }
}
