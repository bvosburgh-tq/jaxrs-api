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

package jakarta.ws.rs.tck.spec.provider.reader;

import java.io.InputStream;
import java.io.IOException;

import jakarta.ws.rs.tck.common.JAXRSCommonClient;
import jakarta.ws.rs.tck.lib.util.TestUtil;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 */

@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends JAXRSCommonClient {

  public JAXRSClientIT() {
    setup();
    setContextRoot("/jaxrs_spec_provider_reader_web/resource");
  }


  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException{
    InputStream inStream = JAXRSClientIT.class.getClassLoader().getResourceAsStream("jakarta/ws/rs/tck/spec/provider/reader/web.xml.template");
    String webXml = editWebXmlString(inStream);
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_spec_provider_reader_web.war");
    archive.addClasses(TSAppConfig.class, Resource.class, AbstractReader.class, AppJavaReader.class, AppOctetReader.class, EntityForReader.class, WildCardReader.class);
    archive.setWebXML(new StringAsset(webXml));
    return archive;
  }

  @BeforeEach
  void logStartTest(TestInfo testInfo) {
    TestUtil.logMsg("STARTING TEST : "+testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
    TestUtil.logMsg("FINISHED TEST : "+testInfo.getDisplayName());
  }


  /* Run test */
  /*
   * @testName: noEntityProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:31; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy: An implementation MUST NOT use an entity provider for a
   * media type that is not supported by that provider.
   */
  @Test
  public void noEntityProviderTest() throws Fault {
    enableAppJava(false);
    String ct = buildContentType(AbstractReader.NO_PROVIDER_MEDIATYPE);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.REQUEST_HEADERS, ct);
    setProperty(Property.CONTENT, "meadiaTypeContentTypeTest");
    setProperty(Property.STATUS_CODE,
        getStatusCode(Status.UNSUPPORTED_MEDIA_TYPE));
    invoke();
  }

  /*
   * @testName: meadiaTypeContentTypeTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.1;
   * 
   * @test_Strategy: Obtain the media type of the request. If the request does
   * not contain a Content-Type header then use application/octet-stream
   */
  @Test
  public void meadiaTypeContentTypeTest() throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_HTML_TYPE));
    setProperty(Property.CONTENT, "meadiaTypeContentTypeTest");
    setProperty(Property.SEARCH_STRING, "meadiaTypeContentTypeTest");
    setProperty(Property.SEARCH_STRING, MediaType.TEXT_HTML);
    invoke();
  }

  /*
   * @testName: meadiaTypeDefaultTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.1;
   * 
   * @test_Strategy: Obtain the media type of the request. If the request does
   * not contain a Content-Type header then use application/octet-stream
   */
  @Test
  public void meadiaTypeDefaultTest() throws Fault {
    enableAppJava(false);
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.UNORDERED_SEARCH_STRING, "meadiaTypeDefaultTest");
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppOctetReader.class.getSimpleName().toUpperCase());
    setProperty(Property.CONTENT, "meadiaTypeDefaultTest");
    invoke();
  }

  /*
   * @testName: iterateAllAppJavaReadersTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.2; JAXRS:SPEC:62.3;
   * JAXRS:SPEC:62.4; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy:
   */
  @Test
  public void iterateAllAppJavaReadersTest() throws Fault {
    enableAppJava(false);
    MediaType mt = new MediaType("application", "java");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.CONTENT, "some content");
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        WildCardReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        WildCardReader.class.getSimpleName().toUpperCase());
    invoke();
  }

  /*
   * @testName: iterateFirstAppJavaReaderTest
   * 
   * @assertion_ids: JAXRS:SPEC:62; JAXRS:SPEC:62.2; JAXRS:SPEC:62.3;
   * JAXRS:SPEC:62.4; JAXRS:SPEC:62.5;
   * 
   * @test_Strategy:
   */
  @Test
  public void iterateFirstAppJavaReaderTest() throws Fault {
    enableAppJava(true);
    MediaType mt = new MediaType("application", "java");
    setProperty(Property.REQUEST, buildRequest(Request.POST, "plain"));
    setProperty(Property.CONTENT, "some content");
    setProperty(Property.REQUEST_HEADERS, buildContentType(mt));
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName());
    setProperty(Property.UNORDERED_SEARCH_STRING,
        AppJavaReader.class.getSimpleName().toUpperCase());
    setProperty(Property.UNORDERED_SEARCH_STRING, mt.toString());
    invoke();
  }

  // /////////////////////////////////////////////////////////////////////////

  private void enableAppJava(boolean enable) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, "appjava"));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.TEXT_PLAIN_TYPE));
    setProperty(Property.CONTENT, String.valueOf(enable));
    invoke();
  }

}
