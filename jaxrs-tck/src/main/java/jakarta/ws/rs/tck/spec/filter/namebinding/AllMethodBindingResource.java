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

package jakarta.ws.rs.tck.spec.filter.namebinding;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("resource/all")
@SingleNameBinding
public class AllMethodBindingResource {

  @GET
  @Path("hundred")
  public String getHundred() {
    return "100";
  }

  @GET
  @Path("thousand")
  @ComplementNameBinding
  public String getThousand() {
    return "1000";
  }
}
