package Clientes;

import ClientesPedidos.GestorPedidos;
import ClientesServicios.Calculadora;
import ClientesServicios.Servicio;
import ClientesServicios.HacerServiciosProxy;
import GlobalConexiones.Conexion;
//import GlobalServicios.ExcepcionServicioNoEncontrado;
import GlobalSeciones.Sesion;
//subio bien
import java.io.IOException;

public class RPCCliente {
    private GestorPedidos gestorPedidos;
    private HacerServiciosProxy servicios_proxy;

    private String host;
    private int puerto;

    //IOException por si no logra conectar con el servidor
    public RPCCliente(String host, int puerto) throws IOException {
        this.host = host;
        this.puerto = puerto;

        Conexion conexion = new Conexion(host, puerto);
        Sesion sesion = new Sesion(conexion);
        gestorPedidos = new GestorPedidos(sesion);

        servicios_proxy = new HacerServiciosProxy(gestorPedidos);

        sesion.setSesionPaqueteEscucha(gestorPedidos);
    }

    
    public void desconectardeServidor() {
        gestorPedidos.getSession().apagar();
        gestorPedidos = null;
    }

    public String getServidorHost() {
        return host;
    }

    public int getServerPuerto() {
        return puerto;
    }

    
      //Encontramos un servicio con el "nombre_servicio" y retornamos un proxy de este
      
      //@throws ExcepcionServicioNoEncontrado si idService is not installed in the server
    
    public Servicio getServicio(String nombreServicio)// throws ExcepcionServicioNoEncontrado 
    {
        try{
        return servicios_proxy.crearProxy(nombreServicio);
        }catch(Exception e){}
        return null;
    }
    
    public static void main(String []args) throws IOException//, ExcepcionServicioNoEncontrado
    {
    	RPCCliente cliente = new RPCCliente("localhost", 12345);
    	Calculadora calculadora = (Calculadora)cliente.getServicio("servicio_Calculadora");
    	double pi_8 = calculadora.getNumeroPI(8);
    	System.out.println(pi_8);
    }
    
}


