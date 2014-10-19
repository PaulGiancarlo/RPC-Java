package Servidor;

import GlobalServicios_servicio.RPCServidorServicio;
import ServidorConexion.ConexionServidor;
import ServidorPedidos.GestorPedidos;
import ServidorServicios.GestorServicio;
import ServidorServicios_servicio.RPCServidorServicioImpl;
import ServidorSesiones.GestorSesiones;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fabian
 */
public class RPCServidor {
    private GestorServicio gestorServicio;
    private int puerto = -1;

    public RPCServidor(int puerto) throws IOException {
        this(puerto, 10);
    }

    public RPCServidor(int puerto, int rqPoolSize) throws IOException {
        ConexionServidor serverConnection = new ConexionServidor(puerto);
        GestorSesiones gestorSesion = new GestorSesiones();
        GestorPedidos gestorPedido = new GestorPedidos(rqPoolSize);
        gestorServicio = new GestorServicio();

        serverConnection.setConnectionListener(gestorSesion);
        gestorSesion.setSesionPaqueteEscucha(gestorPedido);
        gestorPedido.setPedidoPaqueteEscucha(gestorServicio);

        try {
            gestorServicio.addService(RPCServidorServicio.NOMBRE, RPCServidorServicio.class, RPCServidorServicioImpl.class, null);

        } catch (Exception ex) {
            
        }
    }

   
    public int getPuerto() {
        return puerto;
    }

    
    public GestorServicio getGestorServicio() {
        return gestorServicio;
    }
    
    
    public static void main(String []args) throws IOException
    {
    	RPCServidor rpcServer = new RPCServidor(12345);
    	 
    	       Class iface = ClientesServicios.Calculadora.class;
    	       Class impl = ServidorServicios.CalculadoraImpl.class;
    	       Map parametros = new HashMap(); 
               try{
    	       rpcServer.getGestorServicio().addService("servicio_Calculadora", iface, impl, parametros);
               }catch(Exception e){}
    }
}
