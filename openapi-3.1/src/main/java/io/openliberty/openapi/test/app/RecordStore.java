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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RecordStore {
    
    private Map<Integer, ExampleRecord> records;
    
    @PostConstruct
    private void setup() {
        records = Collections.synchronizedMap(new HashMap<>());
    }
    
    public void store(ExampleRecord record) {
        records.put(record.id, record);
    }
    
    public void delete(int id) {
        records.remove(id);
    }
    
    public ExampleRecord get(int id) {
        return records.get(id);
    }
    
    public List<ExampleRecord> getAll() {
        synchronized (records) {
            return new ArrayList<>(records.values());
        }
    }
}
