package ClientesPedidos;

import java.io.Serializable;

/**
 * @author fabian
 */
public class Futuro {
    private Serializable dato = null;

    public Futuro() {
    }

    public void setDato(Serializable d) {
        dato = d;
    }

    public Serializable getDato() {
        return dato;
    }
}
