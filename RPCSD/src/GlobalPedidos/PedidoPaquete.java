package GlobalPedidos;

import java.io.Serializable;

/**
 * 
 * @author fabian
 */
public class PedidoPaquete implements Serializable {
    private String key;
    private Serializable carga;

    public PedidoPaquete(Serializable carga, String key) {
        this.carga = carga;
        this.key = key;
    }

    public Serializable getCarga() {
        return carga;
    }

    public String getKey() {
        return key;
    }
}
