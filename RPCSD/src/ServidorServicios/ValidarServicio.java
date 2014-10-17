package ServidorServicios;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fabian
 */
public class ValidarServicio {
    private final static Class[] EMPTY_CLASS_ARRAY = new Class[]{};

    private final static String DEFAULT_CONSTRUCTOR_NOT_FOUND = "Default constructor not found";
    private final static String SERVICE_IMPL_NOT_IMPLEMENTS_IFACE = "Service class does not implements service interface";
    private final static String DUPLICATE_METHOD_NAME = "Method overloading not supported";
    private final static String METHOD_RET_NOT_SERIALIZABLE = "Method return type not java.io.Serializable";
    private final static String METHOD_PARAM_NOT_SERIALIZABLE = "Method param type not java.io.Serializable";

    private ValidarServicio() {
    }

    public static List<String> validate(Class serviceIfaceClass, Class serviceImplClass) {
        List<String> errors = new LinkedList<String>();

        try {
            serviceImplClass.getConstructor(EMPTY_CLASS_ARRAY);
        } catch (NoSuchMethodException e) {
            errors.add(DEFAULT_CONSTRUCTOR_NOT_FOUND);
        }

        if (!serviceIfaceClass.isAssignableFrom(serviceImplClass)) {
            errors.add(SERVICE_IMPL_NOT_IMPLEMENTS_IFACE);
        }

        Method[] serviceIfaceMethods = serviceIfaceClass.getMethods();
        for (int i = 0; i < serviceIfaceMethods.length; i++) {
            String m1 = serviceIfaceMethods[i].getName();
            for (int j = i+1; j < serviceIfaceMethods.length; j++) {
                String m2 = serviceIfaceMethods[j].getName();
                if (m1.equals(m2)) {
                    errors.add(DUPLICATE_METHOD_NAME + ": " + m1 + "() in " + serviceIfaceClass.getName());
                }
            }
        }

        for (Method m : serviceIfaceMethods) {
            if (!m.getReturnType().isPrimitive() && m.getReturnType() != null &&
                    !m.getReturnType().isInterface() && !Serializable.class.isAssignableFrom(m.getReturnType())) {
                errors.add(METHOD_RET_NOT_SERIALIZABLE + ": " + m.getName() + "()");
            }

            for (int j = 0; j < m.getParameterTypes().length; j++) {
                if (!m.getParameterTypes()[j].isPrimitive() && !m.getParameterTypes()[j].isInterface() &&
                        !Serializable.class.isAssignableFrom(m.getParameterTypes()[j])) {
                    errors.add(METHOD_PARAM_NOT_SERIALIZABLE + ": " + m.getName() + "()");
                }
            }
        }

        return errors;
    }
}
