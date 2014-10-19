package Clientes.ClientesServicios;

import Clientes.ClientesPedidos.GestorPedidos;
import Clientes.GlobalPedidos.PedidoPaquete;
import Clientes.GlobalPedidos.RespuestaPaquete;
//import GlobalServicios.ExcepcionServicio;
import comun.GlobalServicios_servicio.GlobalServicios.ServicioPaquete;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author fabian
 */
public class InvocadorServicios implements InvocationHandler {
    private String nombreServicio = null;
    private GestorPedidos gestorPedidos = null;

    public InvocadorServicios(GestorPedidos gestorPedidos, String nombreServicio) {
        this.nombreServicio = nombreServicio;
        this.gestorPedidos = gestorPedidos;
    }

    // implementacion de  InvocationHandler
    // Tambien aqui se envia la consulta al servidor 
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        PedidoPaquete req = new PedidoPaquete(new ServicioPaquete(nombreServicio, m.getName(), args), getUnicoKey());

        RespuestaPaquete resultado = gestorPedidos.enviar(req);
        /*if (resultado.getData() instanceof ExcepcionServicio) {
            ExcepcionServicio e = (ExcepcionServicio)resultado.getData();
            throw e.getCause();
        }
*/
        return resultado.getDato();
    }

    /**
     * Un Identificador universalmente único (universally unique identifier o UUID) 
     * es un identificador estándar usado en el desarrollo de software, estandarizado
     * por la Open Software Foundation (OSF) como parte del entorno de computación 
     * distribuida (Distributed Computing Environment o DCE).
     * 
     * El UUID es un número de 16-byte (128-bit). El número teórico de posibles UUID es entonces de 
     * unos 3 × 10^38. En su forma canónica, un UUID consiste de 32 dígitos hexadecimales, mostrados
     * en cinco grupos separados por guiones, de la forma 8-4-4-4-12 para un total de 36 caracteres
     * (32 dígitos y 4 guiones). Por ejemplo:
     * 
     *          550e8400-e29b-41d4-a716-446655440000 
     * 
     * Su implementacion es un hilo seguro y rapido.
     * 
     * 
     * Refencencias : 
     *  http://johannburkard.de/software/uuid/
     *  https://es.wikipedia.org/wiki/Universally_unique_identifier
     */
    
    
    private String getUnicoKey() {
         return Thread.currentThread().getName() + "_" + UUID.randomUUID();
    }
}
