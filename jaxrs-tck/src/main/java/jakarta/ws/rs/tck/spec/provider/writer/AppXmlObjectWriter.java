/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.spec.provider.writer;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

@Provider
@Produces("application/xml")
public class AppXmlObjectWriter implements MessageBodyWriter<Object> {
  // Pass information from any Writer
  // Each resource method call is a new thread
  static StringBuilder writerSet = new StringBuilder();

  public static void resetSet() {
    writerSet = new StringBuilder();
  }

  public static void addClass(String string) {
    writerSet.append(string).append(";");
  }

  public static int getCount() {
    String[] split = writerSet.toString().split(";");
    return split.length;
  }

  // Do not inherit
  private static boolean isWritable = false;

  public static void setWritable(boolean bool) {
    isWritable = bool;
  }

  @Override
  public boolean isWriteable(Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType) {
    addClass(AppXmlObjectWriter.class.getSimpleName());
    return isWritable;
  }

  @Override
  public long getSize(Object t, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType) {
    addClass(getClass().getSimpleName().toUpperCase());
    return writerSet.length();
  }

  @Override
  public void writeTo(Object t, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    // in case getSize() is not used,
    // otherwise these additional characters are omitted.
    getSize(entityStream, type, genericType, annotations, mediaType);
    entityStream.write(writerSet.toString().getBytes());
  }

}
