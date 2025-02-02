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

package jakarta.ws.rs.tck.spec.provider.overridestandard;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.Source;

import jakarta.activation.DataSource;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.xml.bind.JAXBElement;

@Path("resource")
public class Resource {
  @Path("bytearray")
  @POST
  public byte[] bytearray(byte[] bytes) {
    return bytes;
  }

  @Path("string")
  @POST
  public String string(String string) {
    return string;
  }

  @Path("inputstream")
  @POST
  public InputStream inputstream(InputStream inputstream) {
    return inputstream;
  }

  @Path("reader")
  @POST
  public Reader reader(Reader reader) {
    return reader;
  }

  @Path("file")
  @POST
  public File file(File file) {
    return file;
  }

  @Path("datasource")
  @POST
  public DataSource datasource(DataSource datasource) {
    return datasource;
  }

  @Path("source")
  @POST
  public Source source(Source source) {
    return source;
  }

  @Path("jaxb")
  @POST
  public JAXBElement<String> jaxb(JAXBElement<String> jaxb) {
    return jaxb;
  }

  @Path("map")
  @POST
  public MultivaluedMap<String, String> map(
      MultivaluedMap<String, String> map) {
    return map;
  }

  @Path("streamingoutput")
  @POST
  public StreamingOutput streamingoutput(StreamingOutput streamingoutput) {
    return streamingoutput;
  }

  @Path("character")
  @POST
  public Character character(Character character) {
    if (character != 'b')
      throw new WebApplicationException(Status.NOT_ACCEPTABLE);
    return character;
  }

  @Path("boolean")
  @POST
  public Boolean bool(Boolean bool) {
    if (!bool)
      throw new WebApplicationException(Status.NOT_ACCEPTABLE);
    return false;
  }

  @Path("number")
  @POST
  public Integer number(Integer i) {
    if (i != 1)
      throw new WebApplicationException(Status.NOT_ACCEPTABLE);
    return i;
  }

}
