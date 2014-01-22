/**
 * 
 */
package jp.co.city.mag.ping.web.rest;

import jabara.general.Empty;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * @author jabaraster
 */
@SuppressWarnings("unused")
public class EnumMessageBodyWriter implements MessageBodyWriter<Object> {

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType)
     */
    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#getSize(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType)
     */
    @Override
    public long getSize(final Object pT, final Class<?> pType, final Type pGenericType, final Annotation[] pAnnotations, final MediaType pMediaType) {
        return -1;
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#isWriteable(java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType)
     */
    @Override
    public boolean isWriteable(final Class<?> pType, final Type pGenericType, final Annotation[] pAnnotations, final MediaType pMediaType) {
        return MediaType.TEXT_PLAIN_TYPE.isCompatible(pMediaType);
    }

    /**
     * @see javax.ws.rs.ext.MessageBodyWriter#writeTo(java.lang.Object, java.lang.Class, java.lang.reflect.Type, java.lang.annotation.Annotation[],
     *      javax.ws.rs.core.MediaType, javax.ws.rs.core.MultivaluedMap, java.io.OutputStream)
     */
    @Override
    public void writeTo(final Object pT, final Class<?> pType, final Type pGenericType, final Annotation[] pAnnotations, final MediaType pMediaType,
            final MultivaluedMap<String, Object> pHttpHeaders, final OutputStream pEntityStream) throws IOException {
        pEntityStream.write((pT == null ? Empty.STRING : String.valueOf(pT)).getBytes(Charset.forName("utf-8"))); //$NON-NLS-1$
    }

}
