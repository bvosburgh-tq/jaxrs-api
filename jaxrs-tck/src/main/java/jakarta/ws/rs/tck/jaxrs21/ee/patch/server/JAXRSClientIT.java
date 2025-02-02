/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.ws.rs.tck.jaxrs21.ee.patch.server;

import jakarta.ws.rs.tck.common.webclient.http.HttpRequest;
import jakarta.ws.rs.tck.common.JAXRSCommonClient;
import java.io.InputStream;
import java.io.IOException;

import jakarta.ws.rs.tck.lib.util.TestUtil;

import jakarta.ws.rs.core.MediaType;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/*
 * @class.setup_props: webServerHost;
 *                     webServerPort;
 *                     ts_home;
 */
/**
 * @since 2.1
 */
@ExtendWith(ArquillianExtension.class)
public class JAXRSClientIT extends JAXRSCommonClient {

  private static final long serialVersionUID = 21L;

  public JAXRSClientIT() {
    setup();
    setContextRoot("/jaxrs_jaxrs21_ee_patch_server_web/resource");
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

    WebArchive archive = ShrinkWrap.create(WebArchive.class, "jaxrs_jaxrs21_ee_patch_server_web.war");
    archive.addClasses(TSAppConfig.class, jakarta.ws.rs.tck.jaxrs21.ee.patch.Resource.class);
    return archive;

  }


  /*
   * @testName: patchTest
   * 
   * @assertion_ids: JAXRS:SPEC:124;
   * 
   */
  @Test
  public void patchTest() throws Fault {
    AdaptiveMethodFactory.getMethodMap().put("PATCH", PatchMethod.class);
    setProperty(Property.REQUEST, buildRequest("PATCH", "patch"));
    setProperty(Property.CONTENT, "patch");
    setProperty(Property.SEARCH_STRING, "patch");
    setProperty(Property.REQUEST_HEADERS,
        buildAccept(MediaType.TEXT_PLAIN_TYPE));
    invoke();
  }

  @Override
  protected HttpRequest createHttpRequest(String requestLine, String host,
      int port) {
    return new AdaptiveHttpRequest(requestLine, host, port);
  };
}
