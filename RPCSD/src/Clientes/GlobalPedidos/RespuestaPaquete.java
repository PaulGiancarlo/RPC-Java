package Clientes.GlobalPedidos;

import java.io.Serializable;

/**
 * 
 * @author fabian
 */
public class RespuestaPaquete implements Serializable {
    private String key;
    private Object dato;

    public RespuestaPaquete(Object dato, String key) {
        this.dato = dato;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Object getDato() {
        return dato;
    }
}
