package Servidor.ServidorServicios;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author fabian
 */
public class ClasesUtiles {
    private ClasesUtiles() {
    }

    private static List<Class> listaClases = null;
    public static List<Class> getClases(String classPath) throws IOException {
        if (listaClases != null)
            return listaClases;

        String pathSeparator = System.getProperty("path.separator");

        String[] paths = classPath.split(pathSeparator);

        List<Class> classes = new ArrayList<Class>();

        for (String path : paths) {
            if (path.toLowerCase().endsWith("jar")) {
                JarFile jarFile = new JarFile(path);

                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName().replace('/', '.');

                    if (!entryName.contains("$") && entryName.endsWith(".class")) {

                        String fqClassName = entryName.substring(0, entryName.lastIndexOf('.'));

                        try {
                            Class clazz = Class.forName(fqClassName);

                            if (!clazz.isPrimitive()) {
                                classes.add(clazz);
                            }
                        } catch (Throwable e) {}
                    }
                }
            }
        }

        Class[] a = classes.toArray(new Class[0]);
        Arrays.sort(a, new Comparator<Class>() {
            public int compare(Class c1, Class c2) {
                try {
                    return c1.getSimpleName().compareTo(c2.getSimpleName());

                } catch(Error e) {
                    String s1 = c1.getName().substring(c1.getName().lastIndexOf('.')+1);
                    String s2 = c2.getName().substring(c2.getName().lastIndexOf('.')+1);
                    return s1.compareTo(s2);
                }
            }
        });

        classes = new ArrayList<Class>();
        for (Object o : a) {
            classes.add((Class)o);
        }

        listaClases = classes;

        return listaClases;
    }

    public static List<Class> getClassesByScope(List<Class> classes,
                                                boolean scopePrivate, boolean scopeProtected,
                                                boolean scopePublic) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            int modifier = clazz.getModifiers();

            if ((scopePrivate && Modifier.isPrivate(modifier)) ||
                    scopeProtected && Modifier.isProtected(modifier) ||
                    scopePublic && Modifier.isPublic(modifier)) {
                l.add(clazz);
            }
        }

        return l;
    }

    public static List<Class> getClassesConcrete(List<Class> classes) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            int modifier = clazz.getModifiers();

            if (!Modifier.isAbstract(modifier) && !Modifier.isInterface(modifier)) {
                l.add(clazz);
            }
        }

        return l;
    }
    public static List<Class> getClassesInteface(List<Class> classes) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            int modifier = clazz.getModifiers();

            if (Modifier.isInterface(modifier)) {
                l.add(clazz);
            }
        }

        return l;
    }
    public static List<Class> getClassesAbstract(List<Class> classes) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            int modifier = clazz.getModifiers();

            if (Modifier.isAbstract(modifier)) {
                l.add(clazz);
            }
        }

        return l;
    }

    public static List<Class> getClassesInstanceOf(List<Class> classes, Class parentClass) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            if (parentClass.isAssignableFrom(clazz)) {
                l.add(clazz);
            }
        }

        return l;
    }

    public static List<Class> getClassesWithDefaultConstructor(List<Class> classes) {
        List<Class> l = new ArrayList<Class>();

        for (Class clazz : classes) {
            try {
                clazz.getConstructor();
                l.add(clazz);
            } catch(NoSuchMethodException e) {}
        }

        return l;
    }

    /**
     * Devuelve una lista de <code>Field<code> con los campos que declara la
     * clase <code>clazz</code> y todas sus superclases.
     * @param clazz Class Clase inicial.
     * @return List<Field> La lista de campos declaradoy y heredados.
     */
    public static List<Field> getDeclaredAndInheritedFields(Class clazz) {
        List<Field> fieldsList = new ArrayList<Field>();

        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();

            for (Field declaredField : declaredFields) {
                fieldsList.add(declaredField);
            }
            clazz = clazz.getSuperclass();
        }

        return fieldsList;
    }

    public static Method findMethodByName(Class clazz, String methodName) throws NoSuchMethodException {
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                return m;
            }
        }
        throw new NoSuchMethodException(clazz.getName() + "." + methodName);
    }
}
