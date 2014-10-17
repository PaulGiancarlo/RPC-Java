package GlobalPedidos;

import java.io.Serializable;

/**
 * This class represents the data response unit of the request layer.
 * @author enriquer
 */
public class RespuestaPaquete implements Serializable {
    private String key;
    private Object data;

    public RespuestaPaquete(Object data, String key) {
        this.data = data;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Object getData() {
        return data;
    }
}
