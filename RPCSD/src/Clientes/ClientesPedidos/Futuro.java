package Clientes.ClientesPedidos;

import java.io.Serializable;

/**
 * @author fabian
 * @author Paul
 * 
 * Fururo es un semaforo con el cual vamos a esperar la respuesta del servidor,
 * despues de enviar un pedido al servidor.
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
