package Servidor.ServidorConexion;

import comun.GlobalServicios_servicio.GlobalConexiones.Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Esta clase administra todas las conexiones de los clientes.
 * @author fabian
 */
public class ConexionServidor extends Thread {

    private ConexionEscucha conexionEscucha = null;
    private ServerSocket servidorSocket;

    public ConexionServidor(int puerto) throws IOException {
        super("Conexion Servidor");

        try {
            servidorSocket = new ServerSocket(puerto);

        } catch (IOException e) {
            limpiar();
            throw e;
        }
    }

    public void run() {
        while (servidorSocket != null) {
            try {
                Socket socket = servidorSocket.accept();
                Conexion nuevaConexion = new Conexion(socket);

                if (conexionEscucha != null) {
                    conexionEscucha.nuevaConexion(nuevaConexion);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void setConnectionListener(ConexionEscucha listener) {
        conexionEscucha = listener;
        if (!isAlive()) {
            start();
        }
    }

    private void limpiar(){
        try {
            servidorSocket.close();
        } catch(Exception e) {}

        servidorSocket = null;
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();

        } catch(Throwable e) {
            throw e;

        } finally {
            limpiar();
        }
    }
}
