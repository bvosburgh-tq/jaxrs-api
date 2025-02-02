/*
 * Copyright (c) 2012, 2020, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.resource.java2entity;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.tck.common.AbstractMessageBodyRW;

/**
 * Complement to CollectionWriter, writes error
 */
@Provider
public class IncorrectCollectionWriter extends AbstractMessageBodyRW
    implements MessageBodyWriter<Collection<?>> {

  public static final String ERROR = "ERROR ";

  @Override
  public boolean isWriteable(Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType) {
    return !new CollectionWriter().isWriteable(type, genericType, annotations,
        mediaType);
  }

  @Override
  public long getSize(Collection<?> t, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType) {
    String path = getPathValue(annotations);
    return ERROR.length() + path.length();
  }

  @Override
  public void writeTo(Collection<?> t, Class<?> type, Type genericType,
      Annotation[] annotations, MediaType mediaType,
      MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
      throws IOException, WebApplicationException {
    String path = getPathValue(annotations);
    entityStream.write(ERROR.getBytes());
    entityStream.write(path.getBytes());
  }
}