/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * SPDX-License-Identifier: EPL-1.0
 *******************************************************************************/
package io.openliberty.openapi.test.app;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@XmlRootElement(name = "record")
@Schema()
public class ExampleRecord {
    
    public ExampleRecord() {
    }
    
    public ExampleRecord(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    @Schema(description = "A unique numerical id", example = "42")
    public int id;
    @Schema(description = "A descriptive name", example = "Fuzzy Bumblebee")
    public String name;
}
