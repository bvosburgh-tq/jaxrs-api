/*
 * Copyright (c) 2014, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.beanparam.form.plain;

import jakarta.ws.rs.tck.ee.rs.Constants;
import jakarta.ws.rs.tck.ee.rs.ParamTest;
import jakarta.ws.rs.tck.ee.rs.beanparam.form.bean.FormBeanParamEntity;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path(value = "resource")
public class Resource extends ParamTest {

  @BeanParam
  FormBeanParamEntity field;

  @POST
  @Path("Field")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String field() {
    sb = new StringBuilder();
    setReturnValues(field.paramEntityWithConstructor,
        field.paramEntityWithFromString, field.paramEntityWithValueOf,
        field.setParamEntityWithFromString,
        field.sortedSetParamEntityWithFromString,
        field.listParamEntityWithFromString, FIELD);
    setReturnValues(field.inner.paramEntityWithConstructor,
        field.inner.paramEntityWithFromString,
        field.inner.paramEntityWithValueOf,
        field.inner.setParamEntityWithFromString,
        field.inner.sortedSetParamEntityWithFromString,
        field.inner.listParamEntityWithFromString, Constants.INNER + FIELD);
    return sb.toString();
  }

  @POST
  @Path("Param")
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String stringParamHandling(@BeanParam FormBeanParamEntity bean) {
    sb = new StringBuilder();
    setReturnValues(bean.paramEntityWithConstructor,
        bean.paramEntityWithFromString, bean.paramEntityWithValueOf,
        bean.setParamEntityWithFromString,
        bean.sortedSetParamEntityWithFromString,
        bean.listParamEntityWithFromString, PARAM);
    setReturnValues(bean.inner.paramEntityWithConstructor,
        bean.inner.paramEntityWithFromString, bean.inner.paramEntityWithValueOf,
        bean.inner.setParamEntityWithFromString,
        bean.inner.sortedSetParamEntityWithFromString,
        bean.inner.listParamEntityWithFromString, Constants.INNER + PARAM);
    return sb.toString();
  }
}
