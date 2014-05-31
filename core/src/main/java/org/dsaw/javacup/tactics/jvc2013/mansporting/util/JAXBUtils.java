/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dsaw.javacup.tactics.jvc2013.mansporting.util;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author MaN
 */
public class JAXBUtils {

    /**
     * Contextos.
     */
    private final static Map<String, JAXBContext> CONTEXT = new HashMap<String, JAXBContext>();

    /**ç
     * Recupera el contexto de l paquete.
     * @param packageName paquete,
     * @return contexto,
     * @throws JAXBException en caso de error.
     */
    private static JAXBContext getContext(String packageName) throws JAXBException {
        JAXBContext context;
        synchronized (CONTEXT) {
            context = CONTEXT.get(packageName);
            if (context == null) {
                context = JAXBContext.newInstance(packageName);
                CONTEXT.put(packageName, context);
            }
        }
        return context;
    }

    /**
     * Realiza el marshal del objeto.
     * @param object objeto.
     * @param escritor
     */
    public static void marshal(Object object, Writer writer) throws JAXBException {
        if (object == null) {
            throw new IllegalArgumentException("object no puede ser <null>");
        }

        String packageName = object.getClass().getPackage().getName();
        JAXBContext context = getContext(packageName);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.marshal(object, writer);
    }

    /**
     * Realiza el unmarshal del objeto.
     * @retun objeto.
     * @param lector
     */
    public static <E> E ummarshal(Class<E> clazz, Reader reader) throws JAXBException {
        if (clazz == null) {
            throw new IllegalArgumentException("object no puede ser <null>");
        }

        String packageName = clazz.getPackage().getName();
        JAXBContext context = getContext(packageName);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object result = unmarshaller.unmarshal(reader);
        if (!clazz.isAssignableFrom(result.getClass())) {
            throw new IllegalArgumentException("El contenido del fichero no es del tipo " + clazz);
        }
        return (E) result;
    }
}
