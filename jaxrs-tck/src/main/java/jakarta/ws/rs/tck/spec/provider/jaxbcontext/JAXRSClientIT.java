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

package jakarta.ws.rs.tck.spec.provider.jaxbcontext;

import java.io.InputStream;
import java.io.IOException;

import jakarta.ws.rs.tck.common.JAXRSCommonClient;
import jakarta.ws.rs.tck.lib.util.TestUtil;

import jakarta.ws.rs.core.MediaType;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
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
    setContextRoot("/jaxrs_spec_provider_jaxbcontext_web/resource");
  }

  private void setPropertyAndInvoke(String resourceMethod) throws Fault {
    setProperty(Property.REQUEST, buildRequest(Request.POST, resourceMethod));
    setProperty(Property.REQUEST_HEADERS,
        buildContentType(MediaType.APPLICATION_XML_TYPE));
    setProperty(Property.SEARCH_STRING, SomeUnmarshaller.class.getSimpleName());
    setProperty(Property.SEARCH_STRING, SomeMarshaller.class.getSimpleName());
    setProperty(Property.CONTENT, "anything");
    invoke();
  }

 
  @Deployment(testable = false)
  public static WebArchive createDeployment() throws IOException{
    InputStream inStream = JAXRSClientIT.class.getClassLoader().getResourceAsStream("jakarta/ws/rs/tck/spec/provider/jaxbcontext/web.xml.template");
    String webXml = editWebXmlString(inStream);
    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_spec_provider_jaxbcontext_web.war");
    archive.addClasses(TSAppConfig.class, Resource.class, JaxbContextProvider.class, SomeJaxbContext.class, SomeMarshaller.class, SomeUnmarshaller.class, TckJaxbProvider.class);
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
   * @testName: readWriteProviderTest
   * 
   * @assertion_ids: JAXRS:SPEC:34
   * 
   * @test_Strategy: The implementation-supplied entity provider(s) for
   * jakarta.xml.bind.JAXBElement and application supplied JAXB classes MUST use
   * JAXBContext instances provided by application-supplied context resolvers,
   * see Section 4.3.
   */
  @Test
  @Tag("xml_binding")
  public void readWriteProviderTest() throws Fault {
    setPropertyAndInvoke("jaxb");
  }

}
