/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.ee.rs.ext.interceptor.containerreader.readerinterceptorcontext;

import java.io.InputStream;
import java.io.IOException;
import jakarta.ws.rs.tck.api.rs.ext.interceptor.TemplateInterceptorBody;
import jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.ContextOperation;
import jakarta.ws.rs.tck.common.client.TextCaser;
import jakarta.ws.rs.tck.lib.util.TestUtil;
import jakarta.ws.rs.tck.ee.rs.ext.interceptor.containerreader.ReaderClient;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 */
@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends ReaderClient<ContextOperation> {

  private static final long serialVersionUID = 3006391868445878375L;

  public JAXRSClientIT() {
    setup();
    setContextRoot(
        "/jaxrs_ee_rs_ext_interceptor_containerreader_readerinterceptorcontext_web/resource");
  }

  @BeforeEach
  void logStartTest(TestInfo testInfo) {
    TestUtil.logMsg("STARTING TEST : "+testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
    TestUtil.logMsg("FINISHED TEST : "+testInfo.getDisplayName());
  }

  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException{

    InputStream inStream = JAXRSClientIT.class.getClassLoader().getResourceAsStream("jakarta/ws/rs/tck/ee/rs/ext/interceptor/containerreader/readerinterceptorcontext/web.xml.template");
    String webXml = editWebXmlString(inStream);

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_ee_rs_ext_interceptor_containerreader_readerinterceptorcontext_web.war");
    archive.addClasses(TSAppConfig.class, Resource.class,
      jakarta.ws.rs.tck.common.util.JaxrsUtil.class,
      jakarta.ws.rs.tck.common.provider.StringBean.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.ContextOperation.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.InputStreamReaderProvider.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.InterceptorBodyOne.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.InterceptorBodyTwo.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.InterceptorCallbackMethods.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.TemplateInterceptorBody.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.ReaderClient.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.TemplateReaderInterceptor.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBean.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.ExceptionThrowingStringBeanEntityProvider.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorOne.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.ReaderInterceptorTwo.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorOneBody.class,
      jakarta.ws.rs.tck.api.rs.ext.interceptor.reader.readerinterceptorcontext.InterceptorTwoBody.class
    );
    archive.setWebXML(new StringAsset(webXml));
    return archive;

  }


  /* Run test */

  /*
   * @testName: getHeadersOperationOnlyTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void getHeadersOperationOnlyTest() throws Fault {
    setOperationAndEntity(ContextOperation.GETHEADERS);
    setProperty(Property.SEARCH_STRING_IGNORE_CASE,
        TemplateInterceptorBody.OPERATION);
    setPrintEntity(true);
    invoke();
  }

  /*
   * @testName: getHeadersHeadersSetTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void getHeadersHeadersSetTest() throws Fault {
    Property p = Property.UNORDERED_SEARCH_STRING;
    setOperationAndEntity(ContextOperation.GETHEADERS);
    setProperty(p, TemplateInterceptorBody.OPERATION);
    setTextCaser(TextCaser.LOWER);
    for (int i = 0; i != 5; i++) {
      addHeader(TemplateInterceptorBody.PROPERTY + i, "any");
      setProperty(p, TemplateInterceptorBody.PROPERTY + i);
    }
    invoke();
  }

  /* Run test */
  /*
   * @testName: getHeadersIsMutableTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:923; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Get mutable map of HTTP headers.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void getHeadersIsMutableTest() throws Fault {
    setOperationAndEntity(ContextOperation.GETHEADERSISMUTABLE);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.PROPERTY);
    invoke();
  }

  /*
   * @testName: getInputStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:924; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Get the input stream of the object to be read.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void getInputStreamTest() throws Fault {
    String entity = "getInputStreamEntity";
    setOperationAndEntity(ContextOperation.GETINPUTSTREAM);
    setRequestContentEntity(entity);
    setProperty(Property.SEARCH_STRING, entity);
    invoke();
  }

  /*
   * @testName: proceedThrowsIOExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:925; JAXRS:JAVADOC:926; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Throws: IOException - if an IO error arises
   * 
   * proceed is actually called in every
   * containerreader.readerinterceptorcontext test
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void proceedThrowsIOExceptionTest() throws Fault {
    setOperationAndEntity(ContextOperation.PROCEEDTHROWSIOEXCEPTION);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.IOE);
    invoke();
  }

  /*
   * @testName: proceedThrowsWebApplicationExceptionTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:925; JAXRS:JAVADOC:1008; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Throws: WebApplicationException - thrown by the wrapped
   * {@code MessageBodyReader.readFrom} method.
   * 
   * Proceed is tested in any of the interceptor tests.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void proceedThrowsWebApplicationExceptionTest() throws Fault {
    setOperationAndEntity(ContextOperation.PROCEEDTHROWSWEBAPPEXCEPTION);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.WAE);
    invoke("errorbean");
  }

  /*
   * @testName: setInputStreamTest
   * 
   * @assertion_ids: JAXRS:JAVADOC:927; JAXRS:JAVADOC:920;
   * 
   * @test_Strategy: Update the input stream of the object to be read.
   * 
   * ReaderInterceptor.aroundReadFrom
   */
  @Test
  public void setInputStreamTest() throws Fault {
    setOperationAndEntity(ContextOperation.SETINPUTSTREAM);
    setProperty(Property.SEARCH_STRING, TemplateInterceptorBody.ENTITY2);
    invoke();
  }

  // =====================

}
