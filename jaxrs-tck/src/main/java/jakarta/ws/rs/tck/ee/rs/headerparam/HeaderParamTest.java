/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.headerparam;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import jakarta.ws.rs.tck.ee.rs.ParamEntityThrowingExceptionGivenByName;
import jakarta.ws.rs.tck.ee.rs.ParamEntityThrowingWebApplicationException;
import jakarta.ws.rs.tck.ee.rs.ParamEntityWithConstructor;
import jakarta.ws.rs.tck.ee.rs.ParamEntityWithFromString;
import jakarta.ws.rs.tck.ee.rs.ParamEntityWithValueOf;
import jakarta.ws.rs.tck.ee.rs.ParamTest;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;

@Path(value = "/HeaderParamTest")
public class HeaderParamTest extends ParamTest {

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldParamEntityWithConstructor")
  ParamEntityWithConstructor fieldParamEntityWithConstructor;

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldParamEntityWithFromString")
  ParamEntityWithFromString fieldParamEntityWithFromString;

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldParamEntityWithValueOf")
  ParamEntityWithValueOf fieldParamEntityWithValueOf;

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldSetParamEntityWithFromString")
  Set<ParamEntityWithFromString> fieldSetParamEntityWithFromString;

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldSortedSetParamEntityWithFromString")
  SortedSet<ParamEntityWithFromString> fieldSortedSetParamEntityWithFromString;

  @DefaultValue("HeaderParamTest")
  @HeaderParam("FieldListParamEntityWithFromString")
  List<ParamEntityWithFromString> fieldListParamEntityWithFromString;

  @HeaderParam("FieldParamEntityThrowingWebApplicationException")
  public ParamEntityThrowingWebApplicationException fieldEntityThrowingWebApplicationException;

  @HeaderParam("FieldParamEntityThrowingExceptionGivenByName")
  public ParamEntityThrowingExceptionGivenByName fieldEntityThrowingExceptionGivenByName;

  @GET
  public String stringParamHandling(
      @HeaderParam("X-CTSTEST-HEADERTEST-STRINGTEST1") @DefaultValue("default") String stringheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-stringtest2") @DefaultValue("default") String stringheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-inttest1") int intheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-inttest2") int intheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-bytetest1") byte byteheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-bytetest2") byte byteheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-doubletest1") double doubleheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-doubletest2") double doubleheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-floattest1") float floatheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-floattest2") float floatheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-shorttest1") short shortheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-shorttest2") short shortheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-longtest1") long longheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-longtest2") long longheader2,
      @HeaderParam("X-CTSTEST-HEADERTEST-booleantest1") boolean booleanheader1,
      @HeaderParam("X-CTSTEST-HEADERTEST-booleantest2") boolean booleanheader2,
      @DefaultValue("HeaderParamTest") @HeaderParam("ParamEntityWithConstructor") ParamEntityWithConstructor paramEntityWithConstructor,
      @DefaultValue("HeaderParamTest") @HeaderParam("ParamEntityWithFromString") ParamEntityWithFromString paramEntityWithFromString,
      @DefaultValue("HeaderParamTest") @HeaderParam("ParamEntityWithValueOf") ParamEntityWithValueOf paramEntityWithValueOf,
      @DefaultValue("HeaderParamTest") @HeaderParam("SetParamEntityWithFromString") Set<ParamEntityWithFromString> setParamEntityWithFromString,
      @DefaultValue("HeaderParamTest") @HeaderParam("SortedSetParamEntityWithFromString") SortedSet<ParamEntityWithFromString> sortedSetParamEntityWithFromString,
      @DefaultValue("HeaderParamTest") @HeaderParam("ListParamEntityWithFromString") List<ParamEntityWithFromString> listParamEntityWithFromString,
      @HeaderParam("ParamEntityThrowingWebApplicationException") ParamEntityThrowingWebApplicationException paramEntityThrowingWebApplicationException,
      @HeaderParam("ParamEntityThrowingExceptionGivenByName") ParamEntityThrowingExceptionGivenByName paramEntityThrowingExceptionGivenByName) {

    noparam = true;
    sb = new StringBuilder();

    if (stringheader1 == "" || stringheader1 == null
        || !stringheader1.equals("default"))
      noparam = false;
    sb.append("stringtest1=").append(stringheader1);

    if (stringheader2 == "" || stringheader2 == null
        || !stringheader2.equals("default"))
      noparam = false;
    sb.append("stringtest2=").append(stringheader2);

    appendNonNullSetNoParam("inttest1", intheader1);
    appendNonNullSetNoParam("inttest2", intheader2);
    appendNonNullSetNoParam("doubletest1", doubleheader1);
    appendNonNullSetNoParam("doubletest2", doubleheader2);
    appendNonNullSetNoParam("floattest1", floatheader1);
    appendNonNullSetNoParam("floattest2", floatheader2);
    appendNonNullSetNoParam("longtest1", longheader1);
    appendNonNullSetNoParam("longtest2", longheader2);
    appendNonNullSetNoParam("shorttest1", shortheader1);
    appendNonNullSetNoParam("shorttest2", shortheader2);
    appendNonNullSetNoParam("bytetest1", byteheader1);
    appendNonNullSetNoParam("bytetest2", byteheader2);
    appendTrueSetNoParam("booleantest1", booleanheader1);
    appendTrueSetNoParam("booleantest2", booleanheader2);

    setReturnValues(paramEntityWithConstructor, paramEntityWithFromString,
        paramEntityWithValueOf, setParamEntityWithFromString,
        sortedSetParamEntityWithFromString, listParamEntityWithFromString, "");

    setReturnValues(fieldParamEntityWithConstructor,
        fieldParamEntityWithFromString, fieldParamEntityWithValueOf,
        fieldSetParamEntityWithFromString,
        fieldSortedSetParamEntityWithFromString,
        fieldListParamEntityWithFromString, FIELD);

    if (noparam)
      sb.append("No HeaderParam");
    return sb.toString();
  }

}
