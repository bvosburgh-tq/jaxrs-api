/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package jakarta.ws.rs.tck.ee.rs.core.responsebuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DateContainerReaderWriter
    implements MessageBodyReader<Date>, MessageBodyWriter<Date> {

  public static final int ANNOTATION_NONE = 0;

  public static final int ANNOTATION_CONSUMES = 1 << 2;

  public static final int ANNOTATION_PROVIDER = 1 << 3;

  public static final int ANNOTATION_UNKNOWN = 1 << 7;

  public static final String SPLITTER = " ANNOTATION_VALUE ";

  @Override
  public long getSize(Date arg0, Class<?> arg1, Type arg2, Annotation[] arg3,
      MediaType arg4) {
    Annotation[] annotations = AnnotatedClass.class.getAnnotations();
    return String.valueOf(Long.MAX_VALUE).length() + SPLITTER.length()
        + annotations[0].annotationType().getName().length()
        + annotations[1].annotationType().getName().length();
  }

  @Override
  public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
      MediaType arg3) {
    return arg0 == Date.class;
  }

  @Override
  public void writeTo(Date date, Class<?> arg1, Type arg2, Annotation[] arg3,
      MediaType arg4, MultivaluedMap<String, Object> arg5, OutputStream stream)
      throws IOException, WebApplicationException {
    String annotation = parseAnnotations(arg3);
    stream.write(dateToString(date).getBytes());
    stream.write(SPLITTER.getBytes());
    stream.write(annotation.getBytes());
  }

  @Override
  public boolean isReadable(Class<?> arg0, Type arg1, Annotation[] arg2,
      MediaType arg3) {
    return isWriteable(arg0, arg1, arg2, arg3);
  }

  @Override
  public Date readFrom(Class<Date> arg0, Type arg1, Annotation[] arg2,
      MediaType arg3, MultivaluedMap<String, String> arg4, InputStream arg5)
      throws IOException, WebApplicationException {
    InputStreamReader reader = new InputStreamReader(arg5);
    BufferedReader br = new BufferedReader(reader);
    long date = Long.parseLong(br.readLine());
    return new Date(date);
  }

  protected String parseAnnotations(Annotation[] annotations) {
    StringBuilder value = new StringBuilder();
    if (annotations != null)
      for (Annotation annotation : annotations)
        value.append(annotation.annotationType().getName()).append(", ");
    return value.toString();
  }

  public static final String dateToString(Date date) {
    return String.valueOf(date.getTime());
  }
}
