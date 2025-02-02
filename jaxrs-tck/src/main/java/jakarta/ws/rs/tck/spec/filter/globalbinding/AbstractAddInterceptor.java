/*
 * Copyright (c) 2013, 2021 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.spec.filter.globalbinding;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import jakarta.ws.rs.tck.common.util.JaxrsUtil;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.ext.ReaderInterceptor;
import jakarta.ws.rs.ext.ReaderInterceptorContext;
import jakarta.ws.rs.ext.WriterInterceptor;
import jakarta.ws.rs.ext.WriterInterceptorContext;

public abstract class AbstractAddInterceptor
    implements ReaderInterceptor, WriterInterceptor {

  private int amount;

  public AbstractAddInterceptor(int amount) {
    this.amount = amount;
  }

  @Override
  public void aroundWriteTo(WriterInterceptorContext context)
      throws IOException, WebApplicationException {
    String entity = (String) context.getEntity();
    Integer i = Integer.parseInt(entity);
    entity = String.valueOf(i + amount);
    context.setEntity(entity);
    context.proceed();
  }

  @Override
  public Object aroundReadFrom(ReaderInterceptorContext context)
      throws IOException, WebApplicationException {
    InputStream inputStream = context.getInputStream();
    String entity = JaxrsUtil.readFromStream(inputStream);
    Integer i = Integer.parseInt(entity);
    entity = String.valueOf(i + amount);
    context.setInputStream(new ByteArrayInputStream(entity.getBytes()));
    return context.proceed();
  }

}
