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

package jakarta.ws.rs.tck.common.provider;

import jakarta.ws.rs.ext.ParamConverter;

public class StringBeanParamConverter implements ParamConverter<StringBean> {

  public static final String VALUE = "Converted value: ";

  @Override
  public StringBean fromString(String value) throws IllegalArgumentException {
    return new StringBean(VALUE + value);
  }

  @Override
  public String toString(StringBean value) throws IllegalArgumentException {
    return VALUE + value.get();
  }

}