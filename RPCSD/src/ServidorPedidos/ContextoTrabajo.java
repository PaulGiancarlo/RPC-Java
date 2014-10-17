package ServidorPedidos;

import java.util.Map;

/**
 * @author fabian
 */
public class ContextoTrabajo {
    private static ThreadLocal<Map<String, Object>> contextoTrabajo = new ThreadLocal<Map<String, Object>>();

    static void setContexto(Map<String, Object> contexto) {
        contextoTrabajo.set(contexto);
    }
    public static Map<String, Object> getContexto() {
        return contextoTrabajo.get();
    }
}
