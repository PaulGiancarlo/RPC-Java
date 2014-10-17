package GlobalConexiones;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author fabian
 */
public class Conexion extends Thread {
    private Socket socket = null;
    private final Object bloqueo_socket = new Object();

    private ObjectInputStream is = null;
    private final Object is_bloqueo = new Object();

    private ObjectOutputStream os = null;
    private final Object os_bloqueo = new Object();

    private ConexionPaqueteEscucha conexionPaqueteEscucha = null;
    private final Object conexionPaqueteEscucha_bloqueo = new Object();

    public Conexion(String host, int puerto) throws IOException, SecurityException {
        this(new Socket(host, puerto));
        setName("Conexion RPC Cliente");
    }

    public Conexion(Socket socket) throws IOException, SecurityException {
        setName("Conexion RPC Server-" + hashCode());

        this.socket = socket;
        setDaemon(true);

        System.out.println(""+this.getName());
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            limpiar();
            throw e;
        }
    }

    public void run() {
        try {
            for (;;) {
                ConexionPaquete dato;
                synchronized (is_bloqueo) {
                    dato = (ConexionPaquete)is.readObject();
                }
                synchronized (conexionPaqueteEscucha_bloqueo) {
                    if (conexionPaqueteEscucha != null) {
                        conexionPaqueteEscucha.conexionPaqueteLLegado(dato);
                    }
                }
            }

        } catch (SocketException e) {

        } catch (EOFException e) {

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        synchronized (conexionPaqueteEscucha_bloqueo) {
            if (conexionPaqueteEscucha != null) {
                try {
                    conexionPaqueteEscucha.conexionPerdida();
                } catch(Exception e) {}
            }
        }

        limpiar();
    }

    //Esto podria quitar
    protected void finalize() throws Throwable {
        try {
            super.finalize();

        } catch(Throwable e) {
            throw e;

        } finally {
            limpiar();
        }
    }

    public void setConnectionPacketListener(ConexionPaqueteEscucha conexionPaqueteEscucha) {
        synchronized (conexionPaqueteEscucha_bloqueo) {
            this.conexionPaqueteEscucha = conexionPaqueteEscucha;
        }
        if (!isAlive()) {
            start();
        }
    }

    public ConexionPaqueteEscucha getConnectionPacketListener() {
        synchronized (conexionPaqueteEscucha_bloqueo) {
            return conexionPaqueteEscucha;
        }
    }

    public void apagar() {
        interrupt();
    }

    public void enviar(ConexionPaquete paquete) throws IOException {
        synchronized (os_bloqueo) {
            os.reset();
            os.writeObject(paquete);
            os.flush();
        }
    }

    private void limpiar() {
        try {
            if (is != null) {
                is.close();
                is = null;
            }
        } catch(Exception e) {}

        try{
            synchronized (os_bloqueo) {
                if (os != null) {
                    os.close();
                    os = null;
                }
            }
        } catch(Exception e) {}

        try {
            synchronized (bloqueo_socket) {
                if (socket != null) {
                    socket.close();
                    socket = null;
                }
            }
        } catch(Exception e) {}
    }
}
